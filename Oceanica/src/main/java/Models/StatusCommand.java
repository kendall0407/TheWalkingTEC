/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import ServerPackage.Servidor;
import ServerPackage.ThreadServidor;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author kendall-sanabria
 */
public class StatusCommand extends Command implements Serializable {

    public StatusCommand(CommandType type, String[] parameters) {
        super(CommandType.STATUS, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        Servidor server = threadServidor.getServer();

        enviarStatusTodos(server, threadServidor);
    }

    @Override
    public void processInClient(Client client) {

    }
    
    public String[] getParameters() {
        return parameters;
    }
    
    private void enviarStatusTodos(Servidor server, ThreadServidor solicitante) {
        // cantidad de jugadores conectados
        int n = server.getConnectedClients().size();

        //un mensaje por jugador
        String[] params = new String[n];

        int i = 0;
        for (ThreadServidor ts : server.getConnectedClients()) {
            params[i] = "Jugador " + ts.getIdJugador()
                        + " Vida: " + ts.getCivilizacion()
                      + " Vida: " + ts.getVida()
                      + " Ataques hechos: " + ts.getAtaquesHechos();
            i++;
        }

        // crea el comando de respuesta con esos strings
        StatusResponseCommand cmd = new StatusResponseCommand(params);

        try {
            solicitante.getSender().writeObject(cmd);
            solicitante.getSender().flush();
        } catch (IOException e) {
            server.writeMessage("Error enviando respuesta STATUS");
        }
    }
    
}
