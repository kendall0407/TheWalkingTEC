/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Models.GiveJokerCommand;
import Models.NotificarRanking;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
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
    public ArrayList<Integer> saltosTurno = new ArrayList<>();
    private UserDatabase db;
    private ArrayList<Integer> desconectados = new ArrayList<>();
    
    // Sistema de turnos y rondas
    private int turnoActual = 0;  
    private int turnoActualCopia;
    private int rondaActual = 0;
    public final Object turnoLock = new Object();
    public boolean gameStarted = false;
    
    public Servidor() {
        connectedClients = new ArrayList<>();
        

    }
    
    public void conectarServidor(){
        db = new UserDatabase();
        db.load();
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
            int jugadoresConectados = getConections();

            // Si no hay jugadores, terminar
            if (jugadoresConectados == 0) {
                partidaFinalizada("Todos los jugadores se desconectaron");
                return;
            }

            // Encontrar próximo jugador válido
            int intentos = 0;
            do {
                turnoActual = (turnoActual + 1) % jugadoresConectados;
                intentos++;

                // Prevenir bucle infinito
                if (intentos > jugadoresConectados * 2) {
                    writeMessage("Error: No se puede encontrar jugador para turno");
                    break;
                }

            } while (saltosTurno.contains(turnoActual) || 
                     desconectados.contains(turnoActual) ||
                     !jugadorEstaConectado(turnoActual));

            if (intentos <= jugadoresConectados * 2) {
                notificarTurno();
            }
        }
    }

    private boolean jugadorEstaConectado(int idJugador) {
        for (ThreadServidor ts : connectedClients) {
            if (ts.getIdJugador() == idJugador) {
                return true;
            }
        }
        return false;
    }
    
    //elegir objetivo para un jugador
    public int elegirObjetivo(ThreadServidor t) {
        this.conectados = this.getConnectedClients().size();

        if (conectados <= 1) {
            partidaFinalizada("No hay más jugadores. Ganó J" + t.getIdJugador());
            return -1;
        }

        // Si ya atacó a todos, limpiar historial
        if (t.getObjetivos().size() >= this.conectados - 1) {
            t.getObjetivos().clear();
            writeMessage("J" + t.getIdJugador() + " ha atacado a todos, reiniciando objetivos...");
        }

        Random r = new Random();
        int intentos = 0;
        int maxIntentos = conectados * 3; // Límite de intentos

        while (intentos < maxIntentos) {
            int n = r.nextInt(conectados);

            // Verificar si es un objetivo válido
            if (n != t.getIdJugador() && 
                !t.getObjetivos().contains(n) && 
                !objetivosOcupados.contains(n)) {

                objetivosOcupados.add(n);
                t.getObjetivos().add(n);
                return n;
            }
            intentos++;
        }

        // Si no se encontró objetivo después de muchos intentos
        writeMessage("No se pudo encontrar objetivo para J" + t.getIdJugador() + ", saltando turno.");
        t.getObjetivos().clear(); // Limpiar para la próxima vez
        return -1;
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
    
    public void saltarTurno(int t) {
        saltosTurno.add(t);
    }

    public ArrayList<Integer> getDesconectados() {
        return desconectados;
    }

    public void setTurnoActualExterno(int nuevoTurno) {
        synchronized (turnoLock) {
            this.turnoActual = nuevoTurno;

           
            for (ThreadServidor client : connectedClients) {
                if (client.getIdJugador() == nuevoTurno) {
                    client.notificarTurno();
                    break;
                }
            }
        }
    }


    public UserDatabase getDb() {
        return db;
    }
     
    public void actualizarRanking() {
       ArrayList<ThreadServidor> ordenados = new ArrayList<>(connectedClients);
       Collections.sort(ordenados, (a, b) -> Integer.compare(b.ataquesBuenos, a.ataquesBuenos));

       StringBuilder rankingBuilder = new StringBuilder();
       rankingBuilder.append("=== RANKING ===\n");

       for (int i = 0; i < ordenados.size(); i++) {
           ThreadServidor t = ordenados.get(i);
           rankingBuilder.append(String.format("%d. J%d - %s [Ataques exitosos: %d | Vida: %d]\n", 
               i + 1, 
               t.getIdJugador(),
               t.getUsuario() != null ? t.getUsuario() : "Jugador" + t.getIdJugador(),
               t.ataquesBuenos,
               t.getVida()
           ));
       }

       String[] params = {rankingBuilder.toString()};
       NotificarRanking cmd = new NotificarRanking(params);

       for (ThreadServidor client : connectedClients) {
           try {
               client.sender.writeObject(cmd);  
               client.sender.flush();
           } catch (IOException ex) {
               System.err.println("Error enviando ranking a J" + client.getIdJugador() + ": " + ex.getMessage());
           }
       }
   }
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.conectarServidor();
        
    }
}