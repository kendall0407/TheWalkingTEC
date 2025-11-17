/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Models.Command;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author kendall-sanabria
 */
public class Client {
    /* t
    TODO: dar las diferentes opciones de atacar, dejar que el mae haga acciones
    */
    
    private Civilizacion civilizacion;
    private final int PORT = 1509;
    private final String IP_ADDRESS = "localhost";
    private Socket socket;
    private FrameClient refFrame;
    private ObjectOutputStream sender;
    private ObjectInputStream listener;
    private ThreadClient threadClient;
    private int idObjetivo;
    private int ID;
    private ClientState currentState = ClientState.WAITING_ACTION;
    private int luchadorSeleccionado = -1;

    public Client(Civilizacion civilizacion) {
        this.civilizacion = civilizacion;
        
        //poner los luchadores en la interfaz
        refFrame = new FrameClient();
        refFrame.setVisible(true);

        for (int i = 1; i <= 3; i++) {
            Luchador l = civilizacion.getLuchador(i);

            int y = switch(i) {
                case 1 -> 20;
                case 2 -> 320;
                case 3 -> 620;
                default -> 20;
            };

            refFrame.colocarLuchador(
                l,
                l.getDireccion(),
                20,
                y,
                l.getRepresentacion(),
                l.getPoder(),
                l.getSanidad(),
                l.getResistencia(),
                l.getNombre(),
                l.getHabilidad().toString()
            );
        }

        refFrame.colocarInfoCasillas(civilizacion.getLuchadores());
    }
    
    public void connect (){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            sender = new ObjectOutputStream(socket.getOutputStream());    
            listener = new ObjectInputStream(socket.getInputStream()); 
            
            threadClient =  new ThreadClient(this);
            threadClient.start();
            
            
        } catch (IOException ex) {
            
        }
    }
    
    
    public void procesarEntradaUsuario(String msg) {

        switch (currentState) {

            case WAITING_ACTION:
                if (msg.equals("1")) {
                    refFrame.agregarEstado(
                        "Escoge luchador:\n" +
                        "1. " + civilizacion.getLuchador(1).getNombre() + "\n" +
                        "2. " + civilizacion.getLuchador(2).getNombre() + "\n" +
                        "3. " + civilizacion.getLuchador(3).getNombre()
                    );
                    currentState = ClientState.SELECTING_FIGHTER;
                } else if (msg.equals("2")){
                    refFrame.agregarEstado("Poder aplicado correctamente");
                    //TODO: aplicar poder
                } else if (msg.equals("3")){
                    refFrame.agregarEstado("Resistencia aplicado correctamente");
                    //TODO: aplicar resistencia
                } else if (msg.equals("4")){
                    refFrame.agregarEstado("Sanidad aplicado correctamente");
                    //TODO: aplicar sanidad
                } else if (msg.equals("5")){
                    refFrame.agregarEstado("El estado enemigo es: ");
                    //TODO: estado enemigo
                }
                break;

            case SELECTING_FIGHTER:
                int idx = Integer.parseInt(msg);
                luchadorSeleccionado = idx;
                Habilidad hab = civilizacion.getLuchador(idx).getHabilidad();

                refFrame.agregarEstado(
                    "Ataques disponibles:\n" +
                    "1. " + hab.getAtaqueBase() + "\n" +
                    "2. " + hab.getAtaqueSecundaria() + "\n" +
                    "3. " + hab.getAtaqueEspecial()
                );

                currentState = ClientState.SELECTING_ATTACK;
                break;

            case SELECTING_ATTACK:
                int ataque = Integer.parseInt(msg);
                //enviarAttackCommand(luchadorSeleccionado, ataque);

                refFrame.agregarEstado("Ataque enviado exitosamente!");

                currentState = ClientState.WAITING_ACTION;
                break;
        }
    }
    
    
    public void setIdObjetivo(String id) {
        this.idObjetivo = Integer.parseInt(id);
    }
    public void agregarMensajeEstado(String msg) {
        this.refFrame.agregarEstado(msg);
    }
    public void agregarMensajeBitacora(String msg) {
        this.refFrame.agregarBitacora(msg);
    }
    public ObjectOutputStream getSender() {
        return sender;
    }

    public ObjectInputStream getListener() {
        return listener;
    }

    public FrameClient getRefFrame() {
        return refFrame;
    }

    public Civilizacion getCivilizacion() {
        return civilizacion;
    }
    
    public void recibirDano(int dano) {
        
    }
    
    public void setID(String id) {
        this.ID = Integer.parseInt(id);
    }
}
