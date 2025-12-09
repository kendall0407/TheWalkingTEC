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
public class OwnInfoCommand extends Command implements Serializable {
    public OwnInfoCommand(String[] parameters) {
        super(CommandType.OWNINFO, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = getParameters();
        String usuario = params[0];
        String password = params[1];
        String tipo = params[2];
        String vida = params[3];
        
        threadServidor.setUsuario(usuario);
        threadServidor.setContrasena(password);
        threadServidor.setUltimoTipo(tipo);
        threadServidor.setVidaUltimo(Integer.parseInt(vida));
    }

    @Override
    public void processInClient(Client client) {
        
    }
    
}
