 package Proyecto.Promocion;
 public class Promocion {
     private int id;
     private String descripcion;
     private double descuento;

     public Promocion(int id, String descripcion, double descuento) {
         this.id = id;
         this.descripcion = descripcion;
         this.descuento = descuento;
     }

     public int getId() {
         return id;
     }

     public String getDescripcion() {
         return descripcion;
     }

     public double getDescuento() {
         return descuento;
     }
 }