/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author kendall-sanabria
 */
public class ThreadConnections extends Thread {

    private final Servidor server;

    public ThreadConnections(Servidor server) {
        this.server = server;
    }

    @Override
    public void run() {
        Socket newSocket;

        // Esperar únicamente hasta que el admin envie el msj de iniciar
        // despues de eso, se establecera un maximo de conexiones, las que esten
        // ya conectadas
        while (this.server.getMaxConnections() == 0) {

            server.writeMessage("Esperando jugadores...");
            try {
                newSocket = server.getServer().accept();

                ThreadServidor newServerThread =
                        new ThreadServidor(server, newSocket, server.getConnectedClients().size());
                
                server.getConnectedClients().add(newServerThread);
                newServerThread.start();

                server.writeMessage("Un jugador ha entrado a la sala (Jugadores: " +
                        server.getConnectedClients().size() + ")");

            } catch (IOException ex) {
                server.writeMessage("Error: " + ex.getMessage());
            }
        }
    }
    
    
}
