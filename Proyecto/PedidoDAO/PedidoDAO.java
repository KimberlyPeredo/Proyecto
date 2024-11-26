package Proyecto.PedidoDAO;
import java.sql.*;
import Proyecto.Pedido.Pedido;
import Proyecto.DatabaseConnection.DatabaseConnection;
import java.sql.*;
public class PedidoDAO {
    public PedidoDAO(Connection connection) {
    }

    public void guardarPedido(Pedido pedido) {
        String queryCliente = "INSERT INTO clientes (nombre, contacto) VALUES (?, ?)";
        String queryEmpleado = "INSERT INTO empleados (nombre) VALUES (?)"; // Inserta un empleado si no existe
        String queryPedido = "INSERT INTO pedidos (cliente_id, empleado_id, sabor_id, tamano_id, promocion_id, precio_total) VALUES ( ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                // Primero, agregar un empleado si no se ha seleccionado o no existe
                int empleadoId = agregarEmpleadoSiNoExiste(conn);

                // Insertar cliente
                try (PreparedStatement stmtCliente = conn.prepareStatement(queryCliente, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmtCliente.setString(1, pedido.getCliente().getNombre());
                    stmtCliente.setString(2, pedido.getCliente().getContacto());
                    stmtCliente.executeUpdate();

                    ResultSet rsCliente = stmtCliente.getGeneratedKeys();
                    int clienteId = rsCliente.next() ? rsCliente.getInt(1) : -1;

                    // Insertar pedido
                    try (PreparedStatement stmtPedido = conn.prepareStatement(queryPedido)) {
                        stmtPedido.setInt(1, clienteId);
                        System.out.println("->"+empleadoId);
                        stmtPedido.setInt(2, empleadoId);  // Usamos el ID del empleado que acabamos de insertar o seleccionar
                        stmtPedido.setInt(3, pedido.getSabor().getId());
                        stmtPedido.setInt(4, pedido.getTamano().getId());
                        stmtPedido.setInt(5, pedido.getPromocion().getId());
                        stmtPedido.setDouble(6, pedido.calcularPrecioTotal());
                        stmtPedido.executeUpdate();
                    }
                }
                System.out.println("Pedido guardado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error en guardarPedido:");
            e.printStackTrace();
        }
    }

    // Método para agregar un empleado si no existe
    private int agregarEmpleadoSiNoExiste(Connection conn) throws SQLException {
        String queryEmpleado = "SELECT * FROM empleados LIMIT 1";  // Verifica si ya hay empleados
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(queryEmpleado);
            if (!rs.next()) {
                String queryInsert = "INSERT INTO empleados (nombre) VALUES (?)";
                try (PreparedStatement stmtInsert = conn.prepareStatement(queryInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmtInsert.setString(1, "Empleado Predeterminado");
                    stmtInsert.executeUpdate();

                    ResultSet rsInsert = stmtInsert.getGeneratedKeys();
                    if (rsInsert.next()) {
                        return rsInsert.getInt(1);  // Retorna el ID del empleado recién insertado
                    }
                }
            } else {
                // Si ya hay empleados, seleccionamos el primero (o puedes agregar lógica para seleccionar uno específico)
                return rs.getInt("id");
            }
        }
        return -1; // En caso de que no se pueda obtener el empleado
    }
}