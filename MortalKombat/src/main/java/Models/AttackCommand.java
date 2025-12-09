/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import Servidor.ThreadServidor;
import java.io.Serializable;

public class AttackCommand extends Command implements Serializable {
    
    int[] parameters = new int[3];
    int idA;//Atacante
    int idO;//Objetivo
    String arma; //
    
    
    public AttackCommand(String[] parameters) {
        super(CommandType.ATAQUE, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = getParameters();
        int idObjetivo = Integer.parseInt(params[2]);
        
        ThreadServidor objetivo = threadServidor.getServer().getConnection(idObjetivo);
        objetivo.recibirAtaque(params);

    }
    
    @Override
    public void processInClient(Client client) {
        
    }


}