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
public class UseJokerCommand extends Command implements Serializable{

    public UseJokerCommand(String[] parameters) {
        super(CommandType.COMODIN, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = getParameters();
        int idObjetivo = Integer.parseInt(params[4]);
        
        ThreadServidor objetivo = threadServidor.getServer().getConnection(idObjetivo);
        objetivo.recibirJoker(params);
    }

    @Override
    public void processInClient(Client client) {
        
    }
    
}
