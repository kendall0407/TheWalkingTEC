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
    public abstract void ataqueBase(int x, int y, Celda[][] celdas);
    public abstract void ataqueSecundario(int x, int y, Celda[][] celdas);
    public abstract void ataqueEspecial(int x, int y, Celda[][] celdas);
    public abstract void desplegarMenu(Client client);

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
