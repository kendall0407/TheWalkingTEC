/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author kendall-sanabria
 */
import Jugador.Client;
import ServerPackage.ThreadServidor;
import java.io.Serializable;

public class AssignTargetCommand extends Command implements Serializable {
    
    // parameters[0] = ID del jugador objetivo
    private int targetId;
    public AssignTargetCommand(String[] parameters) {
        super(CommandType.ASSIGN_TARGET, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }
    
    @Override
    public void processInClient(Client client) {
        String idCliente = getParameters()[0];
        String idObjetivo = getParameters()[1];
        client.setIdObjetivo(idObjetivo);
        client.setID(idCliente);
        
        
        // Mostrar mensaje
        String mensaje = "\n";
        mensaje += "Tu objetivo es la civilizacion: " + idObjetivo + "\n";
        
        client.agregarMensajeBitacora(mensaje);
    }
    
    public String[] getParameters() {
        return parameters;
    }
    
    public int getTargetId() { return targetId; }
}