/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidor;

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
    private ServerSocket server;
    private int conectados = 0;
    private ArrayList<ThreadServidor> connectedClients;
    private ThreadConnections connectionsThread;
    private int maxConnections = 0;
    
    // Sistema de turnos y rondas
    private int turnoActual = 0;  
    private int rondaActual = 0;
    private final Object turnoLock = new Object();

    // Base de datos de usuarios local
    private final UserDatabase userDatabase;
    
    public Servidor() {
        connectedClients = new ArrayList<>();
        userDatabase = new UserDatabase();
        // cargar DB al iniciar
        userDatabase.load();
    }
    
    public UserDatabase getUserDatabase() {
        return userDatabase;
    }
    
    public void conectarServidor(){
        try {
            server = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en puerto " + PORT);
            connectionsThread = new ThreadConnections(this);
            connectionsThread.start();
        } catch (IOException ex){
            System.err.println("Error iniciando servidor: " + ex.getMessage());
        }
    }
    
    public void broadcast(String msg){      
        for (ThreadServidor client : connectedClients) {
            try {
                client.getSender().writeObject(msg);  
                client.getSender().flush();
            } catch (IOException ex) {
                System.err.println("Error enviando mensaje a cliente: " + ex.getMessage());
            }
        }
    }
    
    //Iniciar nueva ronda
    public void iniciarNuevaRonda() {
        synchronized (turnoLock) {
            rondaActual++;
            turnoActual = 0;
            writeMessage("=== RONDA " + rondaActual + " ===");
            broadcast("=== RONDA " + rondaActual + " ===");
            for (ThreadServidor client : connectedClients) {
                client.asignarNuevoObjetivo();
            }
            notificarTurno();
        }
    }
    
    //Notificar al jugador actual que es su turno
    public void notificarTurno() {
        synchronized (turnoLock) {
            writeMessage("Turno de J" + turnoActual);
            broadcast("Turno de J" + turnoActual);
            for (ThreadServidor client : connectedClients) {
                if (client.getIdJugador() == turnoActual) {
                    client.notificarTurno();
                    break;
                }
            }
        }
    }
    
    //Avanzar al siguiente turno
    public void siguienteTurno() {
        synchronized (turnoLock) {
            turnoActual++;
            if (turnoActual == conectados) {
                writeMessage("Ronda " + rondaActual + " completada.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
                iniciarNuevaRonda();
            } else {
                notificarTurno();
            }
        }
    }
    
    //elegir objetivo para un jugador
    public int elegirObjetivo(int id) {
        if (id != conectados) {
            return id + 1;
        } else {
            return 0;
        }
    }
    
    // Validar si es el turno de un jugador
    public boolean esTurnoDeJugador(int idJugador) {
        synchronized (turnoLock) {
            return turnoActual == idJugador;
        }
    }
    
    public int getConections() {
        return conectados;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    
    public ArrayList<ThreadServidor> getConnectedClients() {
        return connectedClients;
    }
    
    public ServerSocket getServer() {
        return server;
    }
    
    public void writeMessage(String msg){
        System.out.println(msg);
    }
    
    public int getTurnoActual() {
        synchronized (turnoLock) {
            return turnoActual;
        }
    }
    
    public int getRondaActual() {
        return rondaActual;
    }
    
    public ThreadServidor getConnection(int i) {
        return this.connectedClients.get(i);
    }
    
    // Método para lanzar la GUI de autenticación y obtener el usuario autenticado.
    // Este método se puede llamar desde otro hilo/desde un cliente remoto que solicite
    // que se muestre la ventana en la máquina servidor.
    public User showAuthGUIAndGetUser() {
        return AuthGUI.promptForUser(this.userDatabase);
    }
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        // mostrar ventana auth para testing local (opcional)
        User u = s.showAuthGUIAndGetUser();
        if (u != null) {
            System.out.println("Usuario autenticado: " + u.getUsername());
            u.loadStats(); // función actualmente inútil
        }
        s.conectarServidor();
    }
}