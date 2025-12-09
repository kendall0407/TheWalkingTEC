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
public class PassCommand extends Command implements Serializable{

    public PassCommand(String[] parameters) {
        super(CommandType.PASS, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        threadServidor.pasarTurno();
    }

    @Override
    public void processInClient(Client client) {
        client.escribirMensajeConsola("Turno saltado!");
    }
    
}
