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
public class RechargeGunsCommand extends Command implements Serializable{

    public RechargeGunsCommand(String[] parameters) {
        super(CommandType.RECARGAR, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }

    @Override
    public void processInClient(Client client) {
        
    }
    
}
