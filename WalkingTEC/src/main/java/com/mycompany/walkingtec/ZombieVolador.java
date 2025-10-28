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
public class ZombieVolador extends Zombie {
    
    public ZombieVolador(JLabel refLabel, PantallaJuego refPantalla, int vida, int dano, int velocidad, int posX, int posY, String nombre, String dirrecion) {
        super(refLabel, refPantalla, vida, dano, velocidad, posX, posY, nombre, dirrecion);
    }

    @Override
    public void atacar(Reliquia objetivo) {
        objetivo.recibirDano(getDano(), this);
    }
    
}
