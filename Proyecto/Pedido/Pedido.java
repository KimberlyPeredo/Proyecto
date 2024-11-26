package Proyecto.Pedido;
import Proyecto.Cliente.Cliente;
import Proyecto.Promocion.Promocion;
import Proyecto.Sabor.Sabor;
import Proyecto.Tamano.Tamano;
import Proyecto.Empleado.Empleado;

public class Pedido {
    private Cliente cliente;
    private Empleado empleado;
    private Sabor sabor;
    private Tamano tamano;
    private Promocion promocion;

    public Pedido(Cliente cliente, Empleado empleado, Sabor sabor, Tamano tamano, Promocion promocion) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.sabor = sabor;
        this.tamano = tamano;
        this.promocion = promocion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public double calcularPrecioTotal() {
        double precio_total = sabor.getPrecio() + tamano.getPrecio();
        precio_total -= promocion.getDescuento();
        return precio_total < 0 ? 0 : precio_total;
    }
}
