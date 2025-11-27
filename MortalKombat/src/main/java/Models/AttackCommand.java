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
        int idAtacante = Integer.parseInt(params[0]);
        int idObjetivo = Integer.parseInt(params[1]);
        String arma = params[2];

        ThreadServidor objetivo = threadServidor.getServer().getConnection(idObjetivo);
        objetivo.recibirAtaque(idAtacante, arma);
    }
    
    @Override
    public void processInClient(Client client) {
        String[] params = getParameters();
        int idA = Integer.parseInt(params[0]);
        int idO = Integer.parseInt(params[1]);
        int tipo = Integer.parseInt(params[2]);
        int x = Integer.parseInt(params[3]);
        int y = Integer.parseInt(params[4]);
        
        //actualizar el area de bitácora
        //String mensaje = "⚔️ " + idA + " atacó a " + idO + " con " + tiposAtaques[tipo] + "\n";
        //client.agregarMensajeBitacora(mensaje);
        
        
    }


}