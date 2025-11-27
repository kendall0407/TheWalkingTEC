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
public class ReceiveAttackCommand extends Command implements Serializable {
    
    public ReceiveAttackCommand(CommandType type, String[] parameters) {
        super(type, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }

    @Override
    public void processInClient(Client client) {
        String[] params = getParameters();
        int atacante = Integer.parseInt(params[0]);
        String arma = params[1];
        
        client.actualizarReceivedAttacks("Has sido atacado por J" + atacante);
        //client.recibirDano(x,y,atacante,tipoAtaque);
    }
    
}
