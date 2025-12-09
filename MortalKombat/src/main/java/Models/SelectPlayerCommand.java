/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import Servidor.ThreadServidor;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author kendall-sanabria
 */
public class SelectPlayerCommand extends Command implements Serializable{

    public SelectPlayerCommand(String[] parameters) {
        super(CommandType.CONSULTAR, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = getParameters();
        int idObjetivo = Integer.parseInt(params[0]);
        ThreadServidor objetivo = threadServidor.getServer().getConnection(idObjetivo);
        System.out.println(objetivo + objetivo.getUsuario());
        String info = objetivo.solicitarInfo(objetivo.getUsuario());
        
        String[] p = {info};
        SelectPlayerResponseCommand cmd = new SelectPlayerResponseCommand(p);
        try {
            threadServidor.getSender().writeObject(cmd);
            threadServidor.getSender().flush();
        } catch (IOException ex) {
            threadServidor.getServer().writeMessage("Error enviando datos de jugador");
        } 
    }

    @Override
    public void processInClient(Client client) {
        
    }
    
}
