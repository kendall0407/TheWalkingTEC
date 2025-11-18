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

        // Esperar únicamente hasta que lleguen los 4 jugadores
        while (server.getConnectedClients().size() < server.getMaxConections()) {

            server.writeMessage("Esperando jugadores...");
            try {
                newSocket = server.getServer().accept();

                ThreadServidor newServerThread =
                        new ThreadServidor(server, newSocket, jugadoresContador);

                server.getConnectedClients().add(newServerThread);
                newServerThread.start();

                server.writeMessage("Un jugador ha entrado a la sala (" +
                        (jugadoresContador + 1) + "/4)");

                jugadoresContador++;

            } catch (IOException ex) {
                server.writeMessage("Error: " + ex.getMessage());
            }
        }

        // Cuando está lleno, comienza la partida
        server.writeMessage("Sala llena, comenzando partida...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.getLogger(ThreadConnections.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        server.iniciarNuevaRonda(); 
    }

    public String[] getLista() {
        return lista;
    }
    
    
}
