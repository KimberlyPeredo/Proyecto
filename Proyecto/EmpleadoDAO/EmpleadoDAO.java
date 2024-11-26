package Proyecto.EmpleadoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Proyecto.Empleado.Empleado;


public class EmpleadoDAO {

    private Connection connection;

    public EmpleadoDAO(Connection connection) {
        this.connection = connection;
    }
    public List<Empleado> obtenerEmpleados() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM empleados"; // Asegúrate de que tu tabla se llama "empleados"
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                empleados.add(new Empleado(id, nombre)); // Crear un objeto Empleado
            }
        }
        return empleados;
    }
    public boolean insertarEmpleado(String nombre) {
        String query = "INSERT INTO empleados (nombre) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombre);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Devuelve true si el registro fue exitoso
        } catch (SQLException e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }
}


