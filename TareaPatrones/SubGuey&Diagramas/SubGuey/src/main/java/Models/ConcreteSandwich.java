/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class ConcreteSandwich implements Sandwich{
    
   private final String nombre;
   private final int tamanno;
   private final double precioBase;

    public ConcreteSandwich(String nombre, int tamanno, double precioBase) {
        this.nombre = nombre;
        this.tamanno = tamanno;
        this.precioBase = precioBase;
    }
   
   @Override
    public String getDescripcion(){
       return String.format("%s %dcm",nombre, tamanno);
   }
   
   @Override
    public double getPrecio(){
       return precioBase;
   }
    
}
