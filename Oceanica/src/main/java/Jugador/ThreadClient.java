/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.io.IOException;
import Models.Command;
import Models.StatusResponseCommand;
/**
 *
 * @author kendall-sanabria
 */
public class ThreadClient extends Thread{
    //TODO: debe constantemente ver si recibe ataques, enviar atauqes y actualizar info de celdas
    private Client client;
    
    private boolean isRunning = true;

    public ThreadClient(Client client) {
        this.client = client;

    }
    
    @Override
    public void run (){
        client.agregarMensajeBitacora("Conectado al servidor\n");
        while (isRunning){
            try {
                Object obj = client.getListener().readObject();
                if (obj instanceof Command) { //permite identificar si es de tipo Command, por eso es un Objeto
                    Command cmd = (Command) obj;
                    cmd.processInClient(client);
                }

                else {
                    client.agregarMensajeBitacora((String) obj);
                }
            } catch (IOException ex) {
                
            } catch (ClassNotFoundException ex) {
                System.getLogger(ThreadClient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        }
        
    }
    
    
  
}
