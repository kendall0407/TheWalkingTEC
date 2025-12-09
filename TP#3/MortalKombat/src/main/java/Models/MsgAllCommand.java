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
public class MsgAllCommand extends Command implements Serializable{

    public MsgAllCommand(String[] parameters) {
        super(CommandType.ALL, parameters);
    }



    @Override
    public void processForServer(ThreadServidor threadServidor) {

        threadServidor.getServer().writeMessage("Jugador J" + parameters[0] + " grito " + parameters[1]);

        threadServidor.getServer().broadcast("J"+parameters[0]+" grita a todos: " + parameters[1]);
    }

    @Override
    public void processInClient(Client client) {
        
    }    
    
}
