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
public abstract class Zombie extends Thread {
    private JLabel refLabel;
    private boolean isRunning = true;
    private boolean isPause = true;
    private Pantalla refPantalla;
    private int vida;
    private int dano;
    private int velocidad;
    private int posX;
    private int posY;
    private String direccion;

    public Zombie(JLabel refLabel, Pantalla refPantalla, int vida, int dano, int velocidad, int posX, int posY, String nombre, String dirrecion) {
        this.refLabel = refLabel;
        this.refPantalla = refPantalla;
        this.vida = vida;
        this.dano = dano;
        this.velocidad = velocidad;
        this.posX = posX;
        this.posY = posY;
        
    }
    
    public void run(){
        
        while(isRunning){
            
            try {
                sleep(velocidad);
                
                while(isPause){
                    try{
                        sleep(500);
                    }catch (InterruptedException ex){}
                }
            } catch (InterruptedException ex) {}
        }
    }
    
    public void morir(){   
    }
    public void atacar(){
        
    }
    
    public int recibirDano(int cantidadDano, Zombie atacante){
        vida -= cantidadDano;
        if (vida <= 0){
            morir();
        }
        return vida;
    }
    
    
}
