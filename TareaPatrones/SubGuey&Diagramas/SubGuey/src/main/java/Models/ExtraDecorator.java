/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public abstract class ExtraDecorator implements Sandwich {
    protected final Sandwich sandwich;

    public ExtraDecorator(Sandwich sandwich) {
        this.sandwich = sandwich;
    }
    
    @Override
    public abstract String getDescripcion();
    
    @Override
    public abstract double getPrecio();
    
}
