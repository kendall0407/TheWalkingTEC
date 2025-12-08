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
public class GiveupCommand extends Command implements Serializable{
    
    public GiveupCommand(String[] parameters) {
        super(CommandType.RENDIRSE, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        threadServidor.getServer().broadcast("Jugador J" + threadServidor.getIdJugador() + " acepto su derrota");
        threadServidor.stopThread();
        threadServidor.getServer().getDesconectados().add(threadServidor.getIdJugador());
        
    }

    @Override
    public void processInClient(Client client) {
        client.escribirMensajeConsola("Te rendiste! Perdedor");
        client.enviarMsgServer("Jugador J" + client.getID() + " se rindio!");
        client.disconnect();
    }
    
}
