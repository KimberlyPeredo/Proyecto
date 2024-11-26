package Proyecto.Cliente;

public class Cliente {
    private int id;
    private String nombre;
    private String contacto;

    public Cliente(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setId(int id) {
        this.id = id;
    }
}
