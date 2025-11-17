/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerPackage;

import Models.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author josed
 */
public class Servidor {
    private final int PORT = 1509;
    ServerSocket server;
    private final int maxConections = 4;
    Socket clientesSocket; //Arreglo de clientes.
    ObjectOutputStream writer;
    ObjectInputStream listener;
    private ArrayList<ThreadServidor> connectedClients; // arreglo de hilos por cada jugador conectado
    private ThreadConnections connectionsThread;
    
    public Servidor() {
        connectedClients = new ArrayList<>();
    }
    
    //Levantar el servidor.
    public void conectarServidor(){
        try {
            server = new ServerSocket(PORT);
            connectionsThread = new ThreadConnections(this);
            connectionsThread.start();
        } catch (IOException ex){
            
        }
    }
    

    public void broadcast(String msg){      
        // manda mensajes en los txa de bitacora y status a txaataque del client
        for (ThreadServidor client : connectedClients) {
            try {
                client.sender.writeObject(msg);  
                client.sender.flush();
            } catch (IOException ex) {
                System.err.println("Error enviando mensaje a cliente: " + ex.getMessage());
            }
        }
    }
    
    
    public void broadcastCommand(Command cmd){
        for (ThreadServidor client : connectedClients) {
            try {
                client.sender.writeObject(cmd);
                client.sender.flush();
            } catch (IOException ex) {
                System.err.println("Error enviando comando a cliente: " + ex.getMessage());
            }
        }
    }
    
    public int elegirObjetivo(int id) {
        int objetivo = 0;
        if (id !=3) 
            objetivo = id++;
        else
            objetivo = 0;
        return objetivo;
    }
    
    public int getMaxConections() {
        return maxConections;
    }

    public ArrayList<ThreadServidor> getConnectedClients() {
        return connectedClients;
    }

    public ServerSocket getServer() {
        return server;
    }

    public Socket getClientesSocket() {
        return clientesSocket;
    }
    
    public void writeMessage(String msg){
        System.out.println(msg);
    }


    
    
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.conectarServidor();
    }
    
    
}
