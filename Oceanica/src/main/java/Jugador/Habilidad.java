/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

/**
 *
 * @author kendall-sanabria
 */
public abstract class Habilidad {    
    private String nombre;

    public Habilidad(String nombre) {
        this.nombre = nombre;
    }
    
    //metodos de ataque que todos tienen
    public abstract void ataqueBase();
    public abstract void ataqueSecundario();
    public abstract void ataqueEspecial();

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return getNombre(); // o el campo que represente el nombre de la habilidad
    }

}
