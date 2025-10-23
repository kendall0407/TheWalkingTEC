/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

/**
 *
 * @author josed
 */
public class WalkingThread extends Thread {
    private boolean isRunning = true;
    private boolean isPause = false;
    Pantalla refPantalla;
    
    public WalkingThread(Pantalla refPantalla){
        this.refPantalla = refPantalla;
    }
    
    @Override
    public void run(){
        try {
            while (isRunning){
               sleep(1000); 
            }
            
        } catch (InterruptedException ex){}
        
    }
    public void stopThread(){
        this.isRunning = false;
    
    }
}
