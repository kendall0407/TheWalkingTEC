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
import Servidor.ThreadServidor;
import java.io.Serializable;

public class AssignTargetCommand extends Command implements Serializable {
    public AssignTargetCommand(String[] parameters) {
        super(CommandType.ASSIGNTARGET, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }
    
    @Override
    public void processInClient(Client client) {
        String idCliente = getParameters()[0];
        String idObjetivo = getParameters()[1];

        
        
        // Mostrar mensaje
        String mensaje = "Tu objetivo es el jugador: "  +idObjetivo;
        client.actualizarContrincante(mensaje);
        client.actualizarStatus("Eres el jugador: " + idCliente);
        client.setID(Integer.parseInt(idCliente));
    }
    
    
}