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
public class NotificarRanking extends Command implements Serializable{
    public NotificarRanking(String[] parameters) {
        super(CommandType.NOTIFICAR, parameters);
    }



    @Override
    public void processForServer(ThreadServidor threadServidor) {
        
    }

    @Override
    public void processInClient(Client client) {
        String[] params = getParameters();
        if(params[0].contains("null"))
            client.actualizarRanking("No hay datos suficientes");
        else
            client.actualizarRanking(params[0]);
    }   
}
