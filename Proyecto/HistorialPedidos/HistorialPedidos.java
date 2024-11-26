package Proyecto.HistorialPedidos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Proyecto.Pedido.Pedido;
import Proyecto.Cliente.Cliente;
import Proyecto.Sabor.Sabor;
import Proyecto.Tamano.Tamano;
import Proyecto.Promocion.Promocion;
import Proyecto.Empleado.Empleado;

public class HistorialPedidos {
    private Connection connection;

    public HistorialPedidos(Connection connection) {
        this.connection = connection;
    }

    public void agregarPedido(Pedido pedido) throws SQLException {
        String query = "INSERT INTO pedidos (cliente_id, empleado_id, sabor_id, tamano_id, promocion_id, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setInt(2, pedido.getEmpleado().getId());
            stmt.setInt(3, pedido.getSabor().getId());
            stmt.setInt(4, pedido.getTamano().getId());
            stmt.setInt(5, pedido.getPromocion().getId());
            stmt.setDouble(6, pedido.calcularPrecioTotal());
            stmt.executeUpdate();
        }
    }

    public List<Pedido> obtenerHistorialCliente(int clienteId) throws SQLException {
        List<Pedido> historial = new ArrayList<>();
        String query = "SELECT * FROM pedidos WHERE cliente_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Construir objetos cliente, empleado, sabor, tamaño y promoción
                Cliente cliente = new Cliente(rs.getString("cliente_nombre"), rs.getString("cliente_contacto"));
                cliente.setId(rs.getInt("cliente_id"));

                Empleado empleado = new Empleado(rs.getInt("empleado_id"), "Empleado");
                Sabor sabor = new Sabor(rs.getInt("sabor_id"), "Sabor", rs.getDouble("sabor_precio"));
                Tamano tamano = new Tamano(rs.getInt("tamano_id"), "Tamano", rs.getDouble("tamano_precio"));
                Promocion promocion = new Promocion(rs.getInt("promocion_id"), "Promocion", rs.getDouble("descuento"));

                Pedido pedido = new Pedido(cliente, empleado, sabor, tamano, promocion);
                historial.add(pedido);
            }
        }
        return historial;
    }
}
