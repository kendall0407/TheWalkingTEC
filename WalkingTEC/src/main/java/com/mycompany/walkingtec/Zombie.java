/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import java.awt.Image;
import java.awt.Point;
import static java.lang.Thread.sleep;
import javax.swing.*;

/**
 *
 * @author josed
 */
public abstract class Zombie extends Thread {
    private JLabel refLabel;
    private boolean isRunning = true;
    private boolean isPause = false;
    private CampoBatalla refPantalla;
    
    private int vida;
    private int dano;
    private int velocidad;
    private int posX;
    private int posY;
    private String direccion;
    private String nombre;
    public int rango;
    
    private int espacio;  //cuanto espacio ocupan
    private int nivel;  //nivel, conforme pasan los niveles se aumenta la vida y el dano

    public Zombie(JLabel refLabel, CampoBatalla refPantalla, int vida, int dano, int velocidad, int posX, int posY, String nombre, String dirrecion) {
        this.refLabel = refLabel;
        this.refPantalla = refPantalla;
        this.vida = vida;
        this.dano = dano;
        this.velocidad = velocidad;
        this.posX = posX;
        this.posY = posY;
        this.nivel = refPantalla.getNivel();
    }
    
    
    public void run(){
        while (isRunning){
            try {
                              
                int rango = this.rango;

                for (int dx = -rango; dx <= rango; dx++) {
                    for (int dy = -rango; dy <= rango; dy++) {
                        if (dx == 0 && dy == 0) continue; // Ignorar la celda del mismo zombie

                        int nx = posX + dx;
                        int ny = posY + dy;

                        // Validar límites del tablero
                        if (nx >= 0 && nx < 25 && ny >= 0 && ny < 25) {

                            Celda celda = refPantalla.getCelda(nx, ny); 

                            if (celda != null && celda.estaOcupadaEstructura()!= null) {
                                // Aquí hay una tropa
                                System.out.println("Ataco lo que esté en " + nx + "," + ny);
                                Estructura estructura = celda.estaOcupadaEstructura();
                                atacarEstructura(estructura); 
                                return;                                 
                            } else {
                                //1. esperar velicidad milisegundos
                                sleep(velocidad);

                                //2. mover el label aleatoriamente: determinar la posicion: dónde está el objetivo para determinar a dónde debo ir
                                Point puntoObjetivo = refPantalla.getReliquiaLocation();
                                int posXAntigua = posX;
                                int posYAntigua = posY;
                                //desplaza a ladercha
                                if (posX < puntoObjetivo.x)
                                    posX += 1;
                                //hacia la izquierda
                                else if (posX > puntoObjetivo.x)
                                    posX -= 1;
                                //desplaza a abajo
                                if (posY < puntoObjetivo.y)
                                    posY += 1;
                                //le qiuto para que vaya arriba
                                else if (posY > puntoObjetivo.y)
                                    posY -= 1;
                                //3. pinta el movimiento del label con el método de pantalla

                                refPantalla.moverZombie(refLabel, posX, posY, posXAntigua, posYAntigua);
                                //4. atacar TODO: ataquen por proximidad, también que reciban ataque por proximindad
                            }
                        }
                    }
                }
                
                
                while (isPause){
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(Soldado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (InterruptedException ex) {
                //Logger.getLogger(Soldado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void morir() {   
        if (vida <= 0){
            this.setStop();
            vida = 0;
            ImageIcon icono = new ImageIcon(getClass().getResource("/images/Calavera.png"));
            
            refLabel.setIcon(icono);
        }
    }
    
    public void setStop(){
        this.isPause = false;
        this.isRunning = false;
    }
    
    public abstract void atacar(Reliquia objetivo);
    public abstract void atacarEstructura(Estructura objetivo);
    
    public int recibirDano(int cantidadDano, Estructura atacante){
        vida -= cantidadDano;
        if (vida <= 0){
            morir();
        }
        return vida;
    }

    public int getVida() {
        return vida;
    }

    public int getDano() {
        return dano;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getRango() {
        return rango;
    }
    
    
    
}
