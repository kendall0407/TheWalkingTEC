/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class ExtraItem extends ExtraDecorator{
    private final String nombreExtra;
    private final double precioExtra;

    public ExtraItem(String nombreExtra, double precioExtra, Sandwich sandwich) {
        super(sandwich);
        this.nombreExtra = nombreExtra;
        this.precioExtra = precioExtra;
    }
    
    @Override
    public String getDescripcion() {
        return sandwich.getDescripcion() + String.format(", +%s", nombreExtra);
    }

    @Override
    public double getPrecio() {
        return sandwich.getPrecio() + precioExtra;
    }
    
}
