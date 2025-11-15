/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author kendall-sanabria
 */
public class Client {
    
    
    private Civilizacion civilizacion;
    private final int PORT = 1509;
    private final String IP_ADDRESS = "localhost";
    private Socket socket;
    private FrameClient refFrame;
    private DataOutputStream sender;
    private DataInputStream listener;
    private ThreadClient threadClient;
    
    public Client(Civilizacion civilizacion) {
        this.civilizacion = civilizacion;
        
        //poner los luchadores en la interfaz
        FrameClient frame = new FrameClient();
        frame.setVisible(true);

        frame.colocarLuchador(civilizacion.getLuchador1(), civilizacion.getLuchador1().getDireccion(), 20, 20, civilizacion.getLuchador1().getRepresentacion(), 
                civilizacion.getLuchador1().getPoder(), civilizacion.getLuchador1().getSanidad(), civilizacion.getLuchador1().getResistencia(), 
                civilizacion.getLuchador1().getNombre(), civilizacion.getLuchador1().getHabilidad().toString());
        frame.colocarLuchador(civilizacion.getLuchador2(), civilizacion.getLuchador2().getDireccion(), 20, 320, civilizacion.getLuchador2().getRepresentacion(), 
                civilizacion.getLuchador2().getPoder(), civilizacion.getLuchador2().getSanidad(), civilizacion.getLuchador2().getResistencia(),
                civilizacion.getLuchador2().getNombre(), civilizacion.getLuchador2().getHabilidad().toString());
        frame.colocarLuchador(civilizacion.getLuchador3(), civilizacion.getLuchador3().getDireccion(), 20, 620, civilizacion.getLuchador3().getRepresentacion(), 
                civilizacion.getLuchador3().getPoder(), civilizacion.getLuchador3().getSanidad(), civilizacion.getLuchador3().getResistencia(),
                civilizacion.getLuchador3().getNombre(), civilizacion.getLuchador3().getHabilidad().toString());
        
        frame.colocarInfoCasillas(civilizacion.getLuchadores());
    }
    
    public void connect (){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            sender = new DataOutputStream(socket.getOutputStream());    
            listener = new DataInputStream(socket.getInputStream()); 
            
            threadClient =  new ThreadClient(this);
            threadClient.start();
            
            
        } catch (IOException ex) {
            
        }
    }
    
    public void sendString (String msg){
        try {
            sender.writeUTF(msg);
        } catch (IOException ex) {
            
        }
    }  

    public DataOutputStream getSender() {
        return sender;
    }

    public DataInputStream getListener() {
        return listener;
    }

    public FrameClient getRefFrame() {
        return refFrame;
    }
    
    
}
