/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author kendall-sanabria
 */
public class ThreadConnections extends Thread {

    private final Servidor server;
    private int jugadoresContador = 0;
    private String[] lista;

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
                        new ThreadServidor(server, newSocket, jugadoresContador);

                server.getConnectedClients().add(newServerThread);
                newServerThread.start();

                server.writeMessage("Un jugador ha entrado a la sala (Jugadores: " +
                        (jugadoresContador + 1) + ")");

                jugadoresContador++;

            } catch (IOException ex) {
                server.writeMessage("Error: " + ex.getMessage());
            }
        }

        
        
    }

    public String[] getLista() {
        return lista;
    }
    
    
}
