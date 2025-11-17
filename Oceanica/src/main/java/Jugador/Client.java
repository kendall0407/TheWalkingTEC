/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;


import Models.CommandType;
import Models.OwnInfo;
import Models.StatusCommand;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
    private int ataque;
    public Client(Civilizacion civilizacion) {
        this.civilizacion = civilizacion;
        
        //poner los luchadores en la interfaz
        refFrame = new FrameClient(this);
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
            
            String params = this.getCivilizacion().getNombreCivilizacion();
            OwnInfo cmd = new OwnInfo(CommandType.CIVILIZATION, params);
            
            try {
                sender.writeObject(cmd);
                sender.flush();
            } catch (IOException ex) {
                System.out.println("Error enviando STATUS");
            }
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
                    refFrame.agregarEstado("El estado enemigo es: ");
                    String[] params = {}; // no ocupa parámetros
                    StatusCommand cmd = new StatusCommand(CommandType.STATUS, params);
                    try {
                        sender.writeObject(cmd);
                        sender.flush();
                    } catch (IOException ex) {
                        System.out.println("Error enviando STATUS");
                    }
                }
                break;

            case SELECTING_FIGHTER:
                int idx = Integer.parseInt(msg);
                luchadorSeleccionado = idx;
                Habilidad hab = civilizacion.getLuchador(idx).getHabilidad();

                refFrame.agregarInstrucciones(
                    "Ataques disponibles:\n" +
                    "1. " + hab.getAtaqueBase() + "\n" +
                    "2. " + hab.getAtaqueSecundaria() + "\n" +
                    "3. " + hab.getAtaqueEspecial() + "\n" +
                    "4. Poder" + "\n" + 
                    "5. Resistencia" + "\n" +
                    "6. Sanidad"
                );

                currentState = ClientState.SELECTING_ATTACK;
                break;

            case SELECTING_ATTACK:
                switch (msg) {
                                // parameters[0] = nombre del atacante
                // parameters[1] = nombre del objetivo
                // parameters[2] = tipo de ataque
                // parameters[3] = daño
    //                ataque = Integer.parseInt(msg);
    //                String[] params = { 
    //                    ataque;
    //                };
    //                AttackCommand cmd = new AttackCommand(params);
    //                try {
    //                    sender.writeObject(cmd);
    //                    sender.flush();
    //                } catch (IOException ex) {
    //
    //                } 
                    case "4":
                        {
                            ArrayList<Celda> celdas = civilizacion.getLuchador(luchadorSeleccionado).getCeldas();
                            for (int i = 0; i < celdas.size(); i++){
                                celdas.get(i).aplicarPoder(civilizacion.getLuchador(luchadorSeleccionado).getPoder());
                            }   break;
                        }
                    case "5":
                        {
                            ArrayList<Celda> celdas = civilizacion.getLuchador(luchadorSeleccionado).getCeldas();
                            for (int i = 0; i < celdas.size(); i++){
                                celdas.get(i).aplicarResistencia(civilizacion.getLuchador(luchadorSeleccionado).getResistencia());
                            }   break;
                        }
                    case "6":
                        {
                            ArrayList<Celda> celdas = civilizacion.getLuchador(luchadorSeleccionado).getCeldas();
                            for (int i = 0; i < celdas.size(); i++){
                                celdas.get(i).aplicarSanidad(civilizacion.getLuchador(luchadorSeleccionado).getSanidad());
                            }   break;
                        }
                    default:
                        break;
                }

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
        refFrame.agregarBitacora("Eres el jugador J" + id);
    }
}
