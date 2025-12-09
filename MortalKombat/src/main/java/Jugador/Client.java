/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
    private ClientModel clientModel;
    
    public Client() {
        this.clientModel = new ClientModel(this);
        this.refFrame = new FrameClient(this);
        this.refFrame.setVisible(true);
        
        this.connect();
        
        
    }
    
    private void connect (){
        try {
            socket = new Socket(IP_ADDRESS , PORT);
            this.sender = new ObjectOutputStream(socket.getOutputStream());    
            this.listener = new ObjectInputStream(socket.getInputStream()); 
            
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
    
    
    public void disconnect() {
        try {

            if (sender != null) sender.close();
            if (listener != null) listener.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

            System.out.println("Cliente desconectado.");

        } catch (IOException e) {
            System.out.println("Error al desconectar: " + e.getMessage());
        }
    }
    public void escribirMensajeConsola(String msg) {
        if (consola == null)
            consola = new ConsoleController(this);
        
        if(msg.contains("Turno de J")) {
            
        }
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
        //clientModel.getUltimoPeleador()
    }
    
    public void actualizarStatus(String msg) {
        this.refFrame.getTxaStatus().append(msg + "\n");
    }
    
    public void actualizarContrincante(String msg) {
        this.refFrame.getTxaContrincante().setText(msg + "\n");
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

    public int getID() {
        return ID;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }
    
    public int crearLuchador(String[] parametros) {
        if (parametros == null || parametros.length != 8) {
            this.consola.escribirConsola("\n > [ERROR13]: Se requieren exactamente 8 parámetros.");
            return -1;  // código de error
        } else if (clientModel.getPeleadores().size() >= 4) {
            this.consola.escribirConsola("\n > [ERROR15]: No puedes crear mas luchadores.");
            return -1;
        }

        this.clientModel.crearLuchador(parametros);
        
        return 0;      // OK
    }
    
    public void enviarServer(Object command) {
        try {
            this.getSender().writeObject(command);
            this.getSender().flush();
        } catch (IOException ex) {
            System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void enviarMsgServer(String msg) {
        try {
            this.getSender().writeObject(msg);
            this.getSender().flush();
        } catch (IOException ex) {
            System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void actualizarLuchadorEquipo(int index, String nombre, String porcentaje) {
        JPanel teamRow = refFrame.getTeamRow();
        if (teamRow == null) 
            return;
        if (index < 0 || index >= teamRow.getComponentCount()) 
            return;
        
        // Obtener la tarjeta existente
        JPanel card = (JPanel) teamRow.getComponent(index);

        // Recuperar labels guardados
        JLabel lblNombre = (JLabel) card.getClientProperty("lblNombre");
        JLabel lblPorcentaje = (JLabel) card.getClientProperty("lblPorcentaje");

        if (lblNombre != null) 
            lblNombre.setText(nombre);
        if (lblPorcentaje != null) 
            lblPorcentaje.setText(porcentaje);

        // Refrescar interfaz
        teamRow.revalidate();
        teamRow.repaint();
    }
    
    
    
    public static void main(String[] args) {
        Client cl = new Client();
    }
}
