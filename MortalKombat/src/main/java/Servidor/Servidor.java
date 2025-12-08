/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

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
    public ArrayList<Integer> objetivosOcupados = new ArrayList<>();
    public ArrayList<ThreadServidor> saltosTurno = new ArrayList<>();
    private UserDatabase db;
    private ArrayList<Integer> desconectados = new ArrayList<>();
    
    // Sistema de turnos y rondas
    private int turnoActual = 0;  
    private int rondaActual = 0;
    public final Object turnoLock = new Object();
    public boolean gameStarted = false;
    
    public Servidor() {
        connectedClients = new ArrayList<>();
    }
    
    public void conectarServidor(){
//        db = new UserDatabase();
//        db.load();
//        AuthGUI a = new AuthGUI();
//        AuthGUI.promptForUser(db);
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
                client.sender.writeObject(msg);  
                client.sender.flush();
            } catch (IOException ex) {
                System.err.println("Error enviando mensaje a cliente: " + ex.getMessage());
            }
        }
    }
    
    public void msg(String msg, ThreadServidor client) {
        try {
            client.sender.writeObject(msg);  
            client.sender.flush();
        } catch (IOException ex) {
            System.err.println("Error enviando mensaje a cliente: " + ex.getMessage());
        }
    }
    //Iniciar nueva ronda
    public void iniciarNuevaRonda() {
        synchronized (turnoLock) {
            rondaActual++;
            if(!objetivosOcupados.isEmpty())
                objetivosOcupados.clear();
            turnoActual = 0;
            
            writeMessage("=== RONDA " + rondaActual + " ===");
            broadcast("=== RONDA " + rondaActual + " ===");
            
            // Asignar objetivos a todos
            for (ThreadServidor client : connectedClients) {
                client.asignarNuevoObjetivo();
            }
            
            //Notificar al primer jugador
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
            
            //revisar primero saltoTurnos
            while (saltosTurno.contains(turnoActual) || desconectados.contains(turnoActual)) {
                turnoActual++;
            }
            this.conectados = this.getConnectedClients().size();
            if (turnoActual >= conectados) {
                writeMessage("Ronda " + rondaActual + " completada.");
                if(!saltosTurno.isEmpty())
                    saltosTurno.clear();    
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
    public int elegirObjetivo(ThreadServidor t) {
        this.conectados = this.getConnectedClients().size();
        if (conectados <= 1) {
            partidaFinalizada("No hay mas jugadores  gano J" + t.getIdJugador());
            return -1;
        }
        Random r = new Random();
        int n = r.nextInt(conectados);
        if(t.getObjetivos().size() >= this.conectados - 1) {
            t.getObjetivos().clear();
            siguienteTurno();
            return -1;
        }
        while(n == t.getIdJugador() || t.getObjetivos().contains(n) || objetivosOcupados.contains(n)) {
            n = r.nextInt(conectados);
        }
        objetivosOcupados.add(n);
        return n;
    }
    public void partidaFinalizada(String msg) { //razon de terminarla ya sea por ganar o error por falta de
        //jugadores o incluso o otros
        broadcast("====PARTIDA FINALIZADA====== \t " + msg);
        desconectarServidor();
        
    }
    
    public int getConections() {
        this.conectados = this.getConnectedClients().size();
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
    
    
    public void desconectarServidor() {
        try {
            if (server != null && !server.isClosed()) {
                server.close();
                System.out.println("Servidor detenido.");
            }
        } catch (IOException ex) {
            System.err.println("Error al detener servidor: " + ex.getMessage());
        }

        if (connectionsThread != null && connectionsThread.isAlive()) {
            connectionsThread.interrupt();
        }
    }
    
    public void saltarTurno(ThreadServidor t) {
        saltosTurno.add(t);
    }

    public ArrayList<Integer> getDesconectados() {
        return desconectados;
    }
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.conectarServidor();
    }
}