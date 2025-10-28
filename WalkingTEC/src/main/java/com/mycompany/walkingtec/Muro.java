/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import javax.swing.JLabel;

/**
 *
 * @author kendall-sanabria
 */
public class Muro extends Estructura {

    public Muro(String nombre, int vida, int dano, int espacio, int nivelAparicion, int velocidadAtaque, String direccion, int nivel, JLabel refLabel, CampoBatalla refPantalla) {
        super(nombre, vida, dano, espacio, nivelAparicion, velocidadAtaque, direccion, nivel, refLabel, refPantalla);
    }

  

    @Override
    public void atacar(Zombie objetivo) {
        
    }
    
}
