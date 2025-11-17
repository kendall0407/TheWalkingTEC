/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerPackage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author kendall-sanabria
 */
public class ThreadConnections extends Thread{
    private Servidor server;
    private int jugadoresContador = 0;
    public ThreadConnections(Servidor server) {
        this.server = server;
    }
    
    
    //Recibir unicamente conexiones y crear los threads del server
    @Override
    public void run() {
        Socket newSocket = null;
        while (  server.getConnectedClients().size() <= server.getMaxConections() ){
            if (server.getConnectedClients().size() == server.getMaxConections()) {
                server.writeMessage("Sala llena, comenzando la partida...");
                
                return;
            }
            server.writeMessage("Esperando jugadores...");
            try {
                
                newSocket = server.getServer().accept();
                ThreadServidor newServerThread = new ThreadServidor(server, newSocket, jugadoresContador); //jugadoresContador = id
                server.getConnectedClients().add(newServerThread);
                newServerThread.start();
                server.writeMessage("Un jugador ha entrado a la sala ("+ ++jugadoresContador+"/4)");
                server.writeMessage("cliente conectado");
            } catch (IOException ex) {
                server.writeMessage("Error: " +  ex.getMessage());
            }
        }
            

        
    }
}


