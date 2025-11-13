/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.io.IOException;

/**
 *
 * @author kendall-sanabria
 */
public class ThreadClient extends Thread{
    private Client client;
    
    private boolean isRunning = true;

    public ThreadClient(Client client) {
        this.client = client;

    }
    
    public void run (){
        //TODO: pasar a object
        String receivedMessage = "";
        while (isRunning){
            try {
                receivedMessage = client.getListener().readUTF(); //espera hasta recibir un String desde el cliente que tiene su socket
                client.getRefFrame().writeMessage("Cliente recibió: " + receivedMessage);
                //TODO
                //leer el mensaje, procesarlo ... decidir qué hacer
            } catch (IOException ex) {
                
            }

        }
        
    }
  
}
