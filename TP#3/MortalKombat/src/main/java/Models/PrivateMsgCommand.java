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
public class PrivateMsgCommand extends Command implements Serializable {

    public PrivateMsgCommand(String[] parameters) {
        super(CommandType.DM, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        ThreadServidor t = threadServidor.getServer().getConnection(Integer.parseInt(parameters[2]));
        String msg = "J" + parameters[0] + " te susurra: " + parameters[1];
        threadServidor.getServer().msg(msg, t);
    }

    @Override
    public void processInClient(Client client) {
        
    }
    
}
