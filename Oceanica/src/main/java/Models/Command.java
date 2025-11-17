package Models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Jugador.Client;
import ServerPackage.ThreadServidor;
import java.io.Serializable;

/**
 *
 * @author josed
 */
public abstract class Command implements Serializable{
    private CommandType type;
    public String[] parameters;
    private boolean isBroadcast;
    
    public Command(CommandType type, String[] parameters){
        this.type = type;
        this.parameters = parameters;
    }
            
    public abstract void processForServer(ThreadServidor threadServidor);
    public abstract void processInClient(Client client);
    
}
