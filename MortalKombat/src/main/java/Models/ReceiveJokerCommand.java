/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import Servidor.ThreadServidor;
import java.io.Serializable;

/**
 *
 * @author kendall-sanabria
 */
public class ReceiveJokerCommand extends Command implements Serializable {
    
    public ReceiveJokerCommand(String[] parameters) {
        super(CommandType.RECEIVECOMODIN, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
       
    }

    @Override
    public void processInClient(Client client) {
        String[] params = getParameters();
        client.getClientModel().recibirComodin(params);
    }
    
}
