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
    public String ataqueBase = "";
    public String ataqueSecundaria = "";
    public String ataqueEspecial = "";
    
    public Habilidad() {
        
    }

    //metodos de ataque que todos tienen
    public abstract void ataqueBase();
    public abstract void ataqueSecundario();
    public abstract void ataqueEspecial();

    public String getNombre() {
        return nombre;
    }

    public String getAtaqueBase() {
        return ataqueBase;
    }

    public String getAtaqueSecundaria() {
        return ataqueSecundaria;
    }

    public String getAtaqueEspecial() {
        return ataqueEspecial;
    }
    
}
