package Proyecto.Sabor;
public class Sabor {
    private int id;
    private String nombre;
    private double precio;

    public Sabor(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
