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
public class SelectPlayerResponseCommand extends Command implements Serializable{
    public SelectPlayerResponseCommand(String[] parameters) {
        super(CommandType.CONSULTARESPONSE, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }

    @Override
    public void processInClient(Client client) {
       String[] params = getParameters();
       String msg = params[0];
       
       client.actualizarContrincante(msg);
    }
    
    
    
}
