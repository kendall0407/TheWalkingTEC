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
public class StatusResponseCommand extends Command implements Serializable {

    public StatusResponseCommand(String[] parameters) {
        super(CommandType.STATUSRESPONSE, parameters);
    }

    @Override
    public void processForServer(ThreadServidor ts) {
        // no se usa en el server
    }

    @Override
    public void processInClient(Client client) {
        String[] lines = getParameters(); // los params que mandó el server

        for (String line : lines) {
            client.getRefFrame().agregarEstado(line); // o como se llame tu método para imprimir en el textarea
        }
    }
}
