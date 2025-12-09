package Models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Jugador.Client;
import Servidor.ThreadServidor;
import java.io.Serializable;

/**
 *
 * @author josed
 */
public abstract class Command implements Serializable{
    private CommandType type;
    public String[] parameters;
    
    public Command(CommandType type, String[] parameters){
        this.parameters = parameters;
    }

    
    
    public abstract void processForServer(ThreadServidor threadServidor);
    public abstract void processInClient(Client client);

    public String[] getParameters() {
        return parameters;
    }

    
    
}
