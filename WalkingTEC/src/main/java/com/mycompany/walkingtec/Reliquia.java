/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import javax.swing.*;

/**
 *
 * @author josed
 */
public class Reliquia {
    private CampoBatalla refPantalla;
    private JLabel refLabel;
    private int vida = 1000;
    private int x;
    private int y;
    
    public Reliquia(JLabel refLabel, CampoBatalla refPantalla) {
        this.refLabel = refLabel;
        this.refPantalla = refPantalla;
    }
    

    public void morir(){
        
    }

    public JLabel getRefLabel() {
        return refLabel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int recibirDano(int cantidadDano, Zombie atacante){
        if (vida <= 0) return -1;

        vida -= cantidadDano;
        this.refLabel.setText(vida+"");
        
        if (vida <= 0){
            refPantalla.detenerJuego();
            morir();
        }
            
        return vida;
    
    }
}
