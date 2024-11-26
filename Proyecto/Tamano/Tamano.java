package Proyecto.Tamano;

public class Tamano {
    private int id;
    private String descripcion;
    private double precio;

    public Tamano(int id, String descripcion, double precio) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }
}

