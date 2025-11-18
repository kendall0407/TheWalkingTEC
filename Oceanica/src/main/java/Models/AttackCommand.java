/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import ServerPackage.ThreadServidor;
import java.io.Serializable;

public class AttackCommand extends Command implements Serializable {
    
    int[] parameters = new int[5];
    int idA;//Atacante
    int idO;//Objetivo
    int tipo;// 0 a 17
    int x;//coordenada x
    int y;//coordenada y
    
    private static final String[] tiposAtaques = {
    "Kraken's Tentacles",     // tipo = 0
    "Kraken's Breath",        // tipo = 1
    "Release the Kraken",     // tipo = 2
    "Three Numbers",          // tipo = 3
    "Three Lines",            // tipo = 4
    "Kraken's Alpha",         // tipo = 5
    "Cardumen",               // tipo = 6
    "SharkAttack",            // tipo = 7
    "Pulp",                   // tipo = 8
    "Volcano Rising",         // tipo = 9
    "Volcano Explosion",      // tipo = 10
    "Thermal Rush",           // tipo = 11
    "Thunder Rain",           // tipo = 12
    "Poseidon's Thunder",     // tipo = 13
    "Eel Attack",             // tipo = 14
    "Swirl Raising",          // tipo = 15
    "Human Garbage",          // tipo = 16
    "Radioactive Rush"        // tipo = 17
};
    
    public AttackCommand(String[] parameters) {
        super(CommandType.ATTACK, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = getParameters();
        int idAtacante = Integer.parseInt(params[0]);
        int idObjetivo = Integer.parseInt(params[1]);
        int tipo = Integer.parseInt(params[2]);
        int x = Integer.parseInt(params[3]);
        int y = Integer.parseInt(params[4]);

        ThreadServidor objetivo = threadServidor.getServer().getConnection(idObjetivo);
        objetivo.recibirAtaque(x, y, idAtacante, tipo);
        // Log en servidor
        threadServidor.getServer().writeMessage(
            ">>> " + idA + " atacó a " + idO + "en la casilla (" + x + "," + y + ") con " + tiposAtaques[tipo]
        );
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
        String mensaje = "⚔️ " + idA + " atacó a " + idO + " con " + tiposAtaques[tipo] + "\n";
        client.agregarMensajeBitacora(mensaje);
        
        
    }


}