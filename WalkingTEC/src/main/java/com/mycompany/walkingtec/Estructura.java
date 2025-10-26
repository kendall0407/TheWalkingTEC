/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import javax.swing.JLabel;

/**
 *
 * @author josed
 */
public abstract class Estructura {
    private String nombre;
    private int vida;
    private int dano;
    private int espacio;
    private int nivelAparicion; //nivel de aparicion
    private String direccion; //apariencia
    private int nivel; //nivel, entrre mas nivel mas vida y ataque
    private JLabel refLabel;
    private Pantalla refPantalla;

    public Estructura() {
    }

    
    

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getDano() {
        return dano;
    }

    public int getEspacio() {
        return espacio;
    }


    public void setVida(int vida) {
        this.vida = vida;
    }


    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }
    
    public abstract void morir();
    public abstract int recibirDano(int cantidadDano, Zombie atacante);
    
    
}

