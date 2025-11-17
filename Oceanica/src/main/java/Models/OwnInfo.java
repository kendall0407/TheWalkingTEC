/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import ServerPackage.ThreadServidor;
import java.io.Serializable;


/**
 *
 * @author kendall-sanabria
 */
public class OwnInfo extends Command implements Serializable {

    public OwnInfo(CommandType type, String parameters) {
        super(CommandType.CIVILIZATION, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String c = getCivilizacionClient();
        threadServidor.setCivilizacion(c);
        
    }

    @Override
    public void processInClient(Client client) {
        
    }

}
