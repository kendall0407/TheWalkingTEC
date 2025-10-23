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
public class Zombie extends Estructura {
    private JLabel refLabel;


    public Zombie(JLabel refLabel) {
        this.refLabel = refLabel;
    }
    
    
    
    @Override
    public void morir(){
        
    }
    
    
    @Override
    public int recibirDano(int cantidadDano, Zombie atacante){
        int vida = this.getVida();
        if (vida <= 0) return -1;

        vida -= cantidadDano;
        this.refLabel.setText(vida+"");
        
        if (vida <= 0){
            //refPantalla.deternerJuego();
            morir();
        }
            
        return vida;
    
    }
    
    
}
