/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import javax.swing.ImageIcon;
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
    private PantallaJuego refPantalla;

    public Estructura(String nombre, int vida, int dano, int espacio, int nivelAparicion, String direccion, int nivel, JLabel refLabel, PantallaJuego refPantalla) {
        this.nombre = nombre;
        this.vida = vida;
        this.dano = dano;
        this.espacio = espacio;
        this.nivelAparicion = nivelAparicion;
        this.direccion = direccion;
        this.nivel = nivel;
        this.refLabel = refLabel;
        this.refPantalla = refPantalla;
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
    
    public void morir() {   
        if (vida <= 0){
            //this.setStop();
            vida = 0;
            ImageIcon icono = new ImageIcon(getClass().getResource("/images/Calavera.png"));
            
            refLabel.setIcon(icono);
        }
    }
    public abstract int recibirDano(int cantidadDano, Zombie atacante);
    
    
}

