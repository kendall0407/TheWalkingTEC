/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import java.awt.Point;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author josed
 */
public abstract class Estructura extends Thread {
    private boolean isRunning = true;
    private boolean isPause = false;
    
    public int posX;
    public int posY;
    private String nombre;
    private int vida;
    private int dano;
    private int espacio;
    private int nivelAparicion; //nivel de aparicion
    private int rango;
    private String direccion; //apariencia
    private int nivel; //nivel, entrre mas nivel mas vida y ataque
    private JLabel refLabel;
    private CampoBatalla refPantalla;
    private int velocidadAtaque;
    
    public Estructura(String nombre, int vida, int dano, int espacio, int nivelAparicion, int velocidadAtaque,String direccion, int nivel, JLabel refLabel, CampoBatalla refPantalla) {
        this.nombre = nombre;
        this.vida = vida;
        this.dano = dano;
        this.espacio = espacio;
        this.nivelAparicion = nivelAparicion;
        this.direccion = direccion;
        this.nivel = nivel;
        this.refLabel = refLabel;
        this.refPantalla = refPantalla;
        this.velocidadAtaque = velocidadAtaque;
        
    } 
    

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }
    
    public void setVida(int vida) {
        this.vida = vida;
    }
    
    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }
    
    public int getEspacio() {
        return espacio;
    }
    
    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }
    
    
    public void run(){
        while (isRunning){
            try {
                sleep(velocidadAtaque);
                int rango = this.rango;

                for (int dx = -rango; dx <= rango; dx++) {
                    for (int dy = -rango; dy <= rango; dy++) {
                        if (dx == 0 && dy == 0) continue; // Ignorar la celda del mismo zombie

                        int nx = posX + dx;
                        int ny = posY + dy;

                        // Validar límites del tablero
                        if (nx >= 0 && nx < 25 && ny >= 0 && ny < 25) {

                            Celda celda = refPantalla.getCelda(nx, ny); 

                            if (celda != null && celda.estaOcupada()) {
                                // Aquí hay una tropa
                                System.out.println("Ataco lo que esté en " + nx + "," + ny);
                                Zombie zombie = celda.estaOcupadaZombie();
                                atacar(zombie); 
                                return; 
                            }
                        }
                    }
                }
                
            } catch (InterruptedException ex) {
                //Logger
            }
        }
    }
    
    public abstract void atacar(Zombie objetivo);
    
    public void morir() {   
        if (vida <= 0){
            //this.setStop();
            vida = 0;
            ImageIcon icono = new ImageIcon(getClass().getResource("/images/Calavera.png"));
            
            refLabel.setIcon(icono);
        }
    }
    
    public int recibirDano(int cantidadDano, Zombie atacante) {
        if (vida <= 0) return -1;

        vida -= cantidadDano;
        this.refLabel.setText(vida+"");
        
        if (vida <= 0){
            refPantalla.detenerJuego();
            morir();
        }
            
        return vida;
    }
    
    public void setStop(){
        this.isPause = false;
        this.isRunning = false;
    }
    
}

