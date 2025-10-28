/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author kendall-sanabria
 */
public class Cannon extends Estructura {

    public Cannon(String nombre, int vida, int dano, int espacio, int nivelAparicion, String direccion, int nivel, JLabel refLabel, PantallaJuego refPantalla) {
        super(nombre, vida, dano, espacio, nivelAparicion, direccion, nivel, refLabel, refPantalla);
    }
    

    @Override
    public int recibirDano(int cantidadDano, Zombie atacante) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
