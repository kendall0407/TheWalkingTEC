/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerPackage;

import Models.AssignTargetCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Models.Command;
/**
 *
 * @author josed
 */
public class ThreadServidor extends Thread{
    private Servidor server;
    private Socket socket;
    //TODO OBJECT
    public ObjectInputStream listener;
    public ObjectOutputStream sender;
    private int idJugador;
    private int idObjetivo;
    private boolean isRunning = true;
    private int turno = 0;
    
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
        while (isRunning) {
            try {
                asignarNuevoObjetivo();
                if (this.turno == this.idJugador) {
                    //mandarle el mensaje a la bitacora de que ya puede atacar
                    Object obj = listener.readObject();
                    Command cmd = (Command) obj;
                    cmd.processForServer(this);
                }
                
                
            } catch (IOException ex) {
                server.writeMessage("Jugador desconectado");
                isRunning = false;
                server.getConnectedClients().remove(this);
            } catch (ClassNotFoundException ex) {
                server.writeMessage("Error: Clase no encontrada");
                
            }
        }
        
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Error cerrando socket: " + ex.getMessage());
        }
    }
    
    public ObjectOutputStream getSender() {
        return sender;
    }
    
    public void nuevaRonda() {
        asignarNuevoObjetivo();
        
    }
    
    
    public void asignarNuevoObjetivo() {
        idObjetivo = server.elegirObjetivo(this.idJugador);
        String[] params = { 
            Integer.toString(idJugador), 
            Integer.toString(idObjetivo) 
        };
        AssignTargetCommand cmd = new AssignTargetCommand(params);
        try {
            sender.writeObject(cmd);
            sender.flush();
        } catch (IOException ex) {
            server.writeMessage("Error enviando nuevo objetivo a jugador " + idJugador);
        } 
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
    
    
}
