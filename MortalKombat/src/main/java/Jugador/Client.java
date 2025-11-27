/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author kendall-sanabria
 */
public class Client {
    private final int PORT = 1509;
    private final String IP_ADDRESS = "localhost";
    private Socket socket;
    private FrameClient refFrame;
    private ObjectOutputStream sender;
    private ObjectInputStream listener;
    private ThreadClient threadClient;
    private int idObjetivo;
    private int ID;
    private ConsoleController consola;
    public Client() {
        this.refFrame = new FrameClient(this);
        this.refFrame.setVisible(true);
        
        this.connect();
        
        
    }
    
    private void connect (){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            this.sender = new ObjectOutputStream(socket.getOutputStream());    
            this.listener = new ObjectInputStream(socket.getInputStream()); 
            System.out.println(listener.toString());
            
            threadClient =  new ThreadClient(this);
            threadClient.start();
            
            
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this.refFrame,
                "No se pudo conectar al servidor,\nintenta abrirlo!",
                "Error [270]",
                JOptionPane.WARNING_MESSAGE
            );

            this.refFrame.dispose();
        }
    }
    
    public void escribirMensajeConsola(String msg) {
        if (consola == null)
            consola = new ConsoleController(this);
        
        this.consola.escribirConsola(msg);
    }
    
    
    public void actualizarRanking(String msg) {
        this.getRefFrame().getTxaRanking().append(msg + "\n");
    }
    
    public void actualizarAtaquesHechos(String msg) {
        this.getRefFrame().getTxaAttacksDone().append(msg + "\n");
    }
    
    public void actualizarReceivedAttacks(String msg) {
        this.getRefFrame().getTxaReceivedAttacks().append(msg + "\n");
    }
    
    public void actualizarStatus(String msg) {
        this.refFrame.getTxaStatus().append(msg + "\n");
    }
    
    public FrameClient getRefFrame() {
        return refFrame;
    }
    
    public ObjectOutputStream getSender() {
        return sender;
    }

    public ObjectInputStream getListener() {
        return listener;
    }
    
    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void enviarServer(Object command) {
        try {
            this.getSender().writeObject(command);
            this.getSender().flush();
        } catch (IOException ex) {
            System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public static void main(String[] args) {
        Client cl = new Client();
    }
}
