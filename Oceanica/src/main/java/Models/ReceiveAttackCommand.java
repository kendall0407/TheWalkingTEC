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
        int x = Integer.parseInt(params[0]);
        int y = Integer.parseInt(params[1]);
        int atacante = Integer.parseInt(params[2]);
        int tipoAtaque = Integer.parseInt(params[3]);
        String civilizacionAtacante = params[4];
        
        client.agregarMensajeEstado("Has sido atacado por J" + atacante + ", de la civilizacion: " + civilizacionAtacante);
        client.recibirDano(x,y,atacante,tipoAtaque);
    }
    
}
