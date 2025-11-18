/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerPackage;

import Jugador.Civilizacion;
import Models.AssignTargetCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Models.*;
/**
 *
 * @author josed
 */
public class ThreadServidor extends Thread{
    private Servidor server;
    private Socket socket;

    public ObjectInputStream listener;
    public ObjectOutputStream sender;
    private int idJugador;
    private int idObjetivo;
    private boolean isRunning = true;
    private boolean esMiTurno = false;
    private final Object turnoLock = new Object();
    
     // Información del jugador
    private String civilizacion;
    private int vida = 100;
    private int ataquesHechos = 0;
    
    public ThreadServidor(Servidor server, Socket socket, int id) {
        this.server = server;
        this.socket = socket;
        this.idJugador = id;
        try {
            sender = new ObjectOutputStream(socket.getOutputStream());
            sender.flush(); 
            listener = new ObjectInputStream(socket.getInputStream());
            
        } catch (IOException ex) {

        }
    }
    
    
    @Override
    public void run() {
        try {
            // PRIMER MENSAJE OBLIGATORIO DEL CLIENTE = su civilización
            Object first = listener.readObject();
            
            if (first instanceof OwnInfo own) {
                own.processForServer(this);
                server.writeMessage("Civilizacion del jugador " + idJugador + " recibida.");
            }
            while (isRunning) {
                try {
                    
                    // Esperar hasta que sea mi turno
                    esperarMiTurno();
                    
                    // Si el juego no ha iniciado o no estoy corriendo, salir
                    if (!isRunning) {
                        Thread.sleep(100);
                        continue;
                    }
                    
                    // Es mi turno, esperar comando del cliente
                    server.writeMessage("Esperando acción de J" + idJugador + "...");
                    
                    Object obj = listener.readObject();
                    
                    if (obj instanceof Command) {
                        Command cmd = (Command) obj;
                        
                        // Procesar el comando
                        cmd.processForServer(this);
                        
                        // Avanzar al siguiente turno
                        server.siguienteTurno();
                        
                    } else if (obj instanceof String) {
                        String receivedMessage = (String) obj;
                        server.writeMessage("Mensaje de J" + idJugador + ": " + receivedMessage);
                        server.broadcast("J" + idJugador + ": " + receivedMessage);
                    }
                    
                    // Resetear flag de turno
                    synchronized (turnoLock) {
                        esMiTurno = false;
                    }
                    
                } catch (IOException ex) {
                    server.writeMessage("Jugador J" + idJugador + " desconectado");
                    isRunning = false;
                    server.getConnectedClients().remove(this);
                } catch (ClassNotFoundException ex) {
                    server.writeMessage("Error: Clase no encontrada");
                    
                } catch (InterruptedException ex) {
                    server.writeMessage("Thread J" + idJugador + " interrumpido");
                    break;
                }
            }
            
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ex) {
                System.err.println("Error cerrando socket: " + ex.getMessage());
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.getLogger(ThreadServidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void esperarMiTurno() throws InterruptedException {
        synchronized (turnoLock) {
            while (!esMiTurno && isRunning) {
                turnoLock.wait(100); // Esperar con timeout
            }
        }
    }
    
    public void notificarTurno() {
        synchronized (turnoLock) {
            esMiTurno = true;
            turnoLock.notifyAll();
        }
    }
    
    public void asignarNuevoObjetivo() {
        idObjetivo = server.elegirObjetivo(this.idJugador);
        String[] params = { 
            Integer.toString(idJugador), 
            Integer.toString(idObjetivo),
            server.getConnection(idObjetivo).getCivilizacion()
        };
        AssignTargetCommand cmd = new AssignTargetCommand(params);
        try {
            sender.writeObject(cmd);
            sender.flush();
        } catch (IOException ex) {
            server.writeMessage("Error enviando nuevo objetivo a jugador " + idJugador);
        } 
    }
    public void setCivilizacion(String civ) {
        this.civilizacion = civ;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void addAtaqueHecho() {
        this.ataquesHechos++;
    }
    
    public String getCivilizacion() { return civilizacion; }
    public int getVida() { return vida; }
    public int getAtaquesHechos() { return ataquesHechos; }
    public int getIdObjetivo() { return idObjetivo; }
    
    public ObjectOutputStream getSender() {
        return sender;
    }
    
    public void nuevaRonda() {
        asignarNuevoObjetivo();
        
    }
    
    
    public void stopThread() {
        isRunning = false;
    }

    public Servidor getServer() {
        return server;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getIdJugador() {
        return idJugador;
    }
    
    public void recibirAtaque(int x, int y, int atacante, int tipoAtaque){
        String[] params = { 
            Integer.toString(x), 
            Integer.toString(y),
            Integer.toString(atacante),
            Integer.toString(tipoAtaque),
            this.getServer().getConnection(atacante).getCivilizacion()
        };
        ReceiveAttackCommand cmd = new ReceiveAttackCommand(CommandType.RECEIVEATTACK, params);
        try {
            sender.writeObject(cmd);
            sender.flush();
        } catch (IOException ex) {
            server.writeMessage("Error recibiendo ataque");
        } 
    }
    
    
}
