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
    
    public ReceiveAttackCommand(String[] parameters) {
        super(CommandType.RECEIVEATTACK, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }

    @Override
    public void processInClient(Client client) {
        String[] params = getParameters();
        int atacante = Integer.parseInt(params[0]);
        String arma = params[1];
        int dano = Integer.parseInt(params[2]);
        String peleador = params[3];
        
        client.actualizarReceivedAttacks("Has sido atacado por J" + atacante + 
                " con "+peleador +" ARMA -> " + arma);
        //client.recibirDano(x,y,atacante,tipoAtaque);
    }
    
}
