package Proyecto.Main;
import Proyecto.EmpleadoDAO.EmpleadoDAO;
import Proyecto.PedidoDAO.PedidoDAO;
import Proyecto.Pedido.Pedido;
import Proyecto.Promocion.Promocion;
import Proyecto.Tamano.Tamano;
import Proyecto.Cliente.Cliente;
import Proyecto.Empleado.Empleado;
import Proyecto.Sabor.Sabor;
import Proyecto.HistorialPedidos.HistorialPedidos;
import Proyecto.DatabaseConnection.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.List;
import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Establecer la conexión con la base de datos
        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            System.out.println("No se pudo establecer la conexión a la base de datos. Saliendo del programa.");
            return;
        }

        // Instancia los objetos DAO
        EmpleadoDAO empleadoDAO = new EmpleadoDAO(connection);
        PedidoDAO pedidoDAO = new PedidoDAO(connection);
        HistorialPedidos historialPedidos = new HistorialPedidos(connection);

        // Escáner para leer entradas del usuario
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Menú principal
            System.out.println("\n--- Menú de Heladería ---");
            System.out.println("1. Registrar nuevo pedido");
            System.out.println("2. Ver historial de pedidos de un cliente");
            System.out.println("3. Registrar nuevo empleado");
            System.out.println("4. Ver empleados registrados");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarNuevoPedido(scanner, pedidoDAO, empleadoDAO);
                    break;
                case 2:
                    System.out.print("Ingrese el ID del cliente: ");
                    int clienteId = scanner.nextInt();
                    verHistorialCliente(clienteId, historialPedidos);
                    break;
                case 3:
                    registrarNuevoEmpleado(scanner, empleadoDAO);
                    break;
                case 4:
                    verEmpleados(empleadoDAO);
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema.");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    // Función para registrar un nuevo pedido
    private static void registrarNuevoPedido(Scanner scanner, PedidoDAO pedidoDAO, EmpleadoDAO empleadoDAO) {
        try {
            // Obtener datos del cliente
            System.out.print("Ingrese el nombre del cliente: ");
            String nombreCliente = scanner.nextLine();
            System.out.print("Ingrese el contacto del cliente: ");
            String contactoCliente = scanner.nextLine();
            Cliente cliente = new Cliente(nombreCliente, contactoCliente);

            // Seleccionar sabor, tamaño y promoción
            Sabor sabor = seleccionarSabor(scanner);
            Tamano tamano = seleccionarTamano(scanner);
            Promocion promocion = seleccionarPromocion(scanner);

            // Seleccionar empleado
            Empleado empleado = seleccionarEmpleado(scanner, empleadoDAO);
            if (empleado == null) {
                System.out.println("No se pudo seleccionar un empleado válido. Abortando el registro del pedido.");
                return;
            }

            // Crear el pedido
            Pedido pedido = new Pedido(cliente, empleado, sabor, tamano, promocion);

            // Guardar el pedido
            pedidoDAO.guardarPedido(pedido);

            System.out.println("Pedido registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar el pedido: " + e.getMessage());
        }
    }

    // Función para seleccionar un sabor
    private static Sabor seleccionarSabor(Scanner scanner) {
        System.out.println("Sabores disponibles:");
        System.out.println("1. Vainilla - $2.5");
        System.out.println("2. Chocolate - $3.0");
        System.out.println("3. Fresa - $2.8");
        System.out.println("4. Mango - $3.2");
        System.out.println("5. Limón - $2.7");
        System.out.print("Seleccione un sabor (1-5): ");
        int saborSeleccionado = scanner.nextInt();

        // Verificación de opción válida
        if (saborSeleccionado < 1 || saborSeleccionado > 5) {
            System.out.println("Opción no válida. Seleccionando Vainilla por defecto.");
            saborSeleccionado = 1; // Por defecto, selecciona Vainilla
        }

        switch (saborSeleccionado) {
            case 1:
                return new Sabor(1, "Vainilla", 2.5);
            case 2:
                return new Sabor(2, "Chocolate", 3.0);
            case 3:
                return new Sabor(3, "Fresa", 2.8);
            case 4:
                return new Sabor(4, "Mango", 3.2);
            case 5:
                return new Sabor(5, "Limón", 2.7);
            default:
                System.out.println("Opción no válida. Seleccionando Vainilla por defecto.");
                return new Sabor(1, "Vainilla", 2.5); // En caso de error, selecciona Vainilla
        }
    }

    // Función para seleccionar el tamaño
    private static Tamano seleccionarTamano(Scanner scanner) {
        System.out.println("Tamaños disponibles:");
        System.out.println("1. Pequeño - $1.0");
        System.out.println("2. Mediano - $1.5");
        System.out.println("3. Grande - $2.0");
        System.out.print("Seleccione un tamaño (1-3): ");
        int tamanoSeleccionado = scanner.nextInt();

        switch (tamanoSeleccionado) {
            case 1:
                return new Tamano(1, "Pequeño", 1.0);
            case 2:
                return new Tamano(2, "Mediano", 1.5);
            case 3:
                return new Tamano(3, "Grande", 2.0);
            default:
                System.out.println("Opción no válida. Seleccionando Pequeño por defecto.");
                return new Tamano(1, "Pequeño", 1.0);
        }
    }

    // Función para seleccionar una promoción
    private static Promocion seleccionarPromocion(Scanner scanner) {
        System.out.println("Promociones disponibles:");
        System.out.println("1. Sin Promoción - $0.0");
        System.out.println("2. Descuento 10% - $0.5");
        System.out.println("3. Descuento 20% - $1.0");
        System.out.print("Seleccione una promoción (1-3): ");
        int promocionSeleccionada = scanner.nextInt();

        switch (promocionSeleccionada) {
            case 1:
                return new Promocion(1, "Sin promoción", 0.0);
            case 2:
                return new Promocion(2, "Descuento 10%", 0.5);
            case 3:
                return new Promocion(3, "Descuento 20%", 1.0);
            default:
                System.out.println("Opción no válida. Seleccionando Sin Promoción por defecto.");
                return new Promocion(1, "Sin promoción", 0.0);
        }
    }

    // Función para seleccionar un empleado
    private static Empleado seleccionarEmpleado(Scanner scanner, EmpleadoDAO empleadoDAO) {
        try {
            List<Empleado> empleados = empleadoDAO.obtenerEmpleados();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados disponibles.");
                return null; // No hay empleados
            }

            // Mostrar empleados disponibles
            System.out.println("Seleccione un empleado:");
            for (int i = 0; i < empleados.size(); i++) {
                System.out.println((i + 1) + ". " + empleados.get(i).getNombre());
            }

            // Seleccionar empleado
            System.out.print("Seleccione el número de empleado: ");
            int empleadoSeleccionado = scanner.nextInt();
            if (empleadoSeleccionado < 1 || empleadoSeleccionado > empleados.size()) {
                System.out.println("Empleado no válido.");
                return null;
            }

            return empleados.get(empleadoSeleccionado - 1);
        } catch (Exception e) {
            System.out.println("Error al obtener empleados: " + e.getMessage());
            return null;
        }
    }

    // Función para ver el historial de pedidos de un cliente
    private static void verHistorialCliente(int clienteId, HistorialPedidos historialPedidos) {
        try {
            List<Pedido> historial = historialPedidos.obtenerHistorialCliente(clienteId);
            if (historial.isEmpty()) {
                System.out.println("No se encontraron pedidos para este cliente.");
            } else {
                System.out.println("Historial de pedidos:");
                for (Pedido pedido : historial) {
                    System.out.println("- Sabor: " + pedido.getSabor().getNombre() +
                            ", Tamaño: " + pedido.getTamano().getDescripcion() +
                            ", Total: $" + pedido.calcularPrecioTotal());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el historial de pedidos: " + e.getMessage());
        }
    }

    // Función para registrar un nuevo empleado
    private static void registrarNuevoEmpleado(Scanner scanner, EmpleadoDAO empleadoDAO) {
        System.out.print("Ingrese el nombre del nuevo empleado: ");
        String nombreEmpleado = scanner.nextLine();

        boolean exito = empleadoDAO.insertarEmpleado(nombreEmpleado);
        if (exito) {
            System.out.println("Empleado registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el empleado.");
        }
    }

    // Función para ver los empleados registrados
    private static void verEmpleados(EmpleadoDAO empleadoDAO) {
        try {
            List<Empleado> empleados = empleadoDAO.obtenerEmpleados();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados registrados.");
            } else {
                System.out.println("Empleados registrados:");
                for (Empleado empleado : empleados) {
                    System.out.println("ID: " + empleado.getId() + " - Nombre: " + empleado.getNombre());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener empleados: " + e.getMessage());
        }
    }
}