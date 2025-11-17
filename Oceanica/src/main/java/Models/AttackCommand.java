/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import ServerPackage.ThreadServidor;
import java.io.Serializable;

public class AttackCommand extends Command implements Serializable {
    
    // parameters[0] = nombre del atacante
    // parameters[1] = nombre del objetivo
    // parameters[2] = tipo de ataque
    // parameters[3] = daño
    
    public AttackCommand(String[] parameters) {
        super(CommandType.ATTACK, parameters);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = getParameters();
        String atacante = params[0];
        String objetivo = params[1];
        String tipoAtaque = params[2];
        String danio = params[3];
        
        // Log en servidor
        threadServidor.getServer().writeMessage(
            ">>> " + atacante + " atacó a " + objetivo + " con " + tipoAtaque + " (" + danio + " daño)"
        );
        
        threadServidor.getServer().broadcast("actualizar");
    }
    
    @Override
    public void processInClient(Client client) {
        String[] params = getParameters();
        String atacante = params[0];
        String objetivo = params[1];
        String tipoAtaque = params[2];
        String dano = params[3];
        
        //actualizar el area de bitácora
        String mensaje = "⚔️ " + atacante + " atacó a " + objetivo + " con " + tipoAtaque + " causando " + dano + " de daño\n";
        client.agregarMensajeBitacora(mensaje);
        
        //si es el objetivo actualizar la vida
        if (client.getCivilizacion().toString().equals(objetivo)) {
            client.recibirDano(Integer.parseInt(dano));
            client.agregarMensajeEstado("Estado: Estas siendo atacado");
        }
        
        //si es el atacante actualizar el estado
        if (client.getCivilizacion().toString().equals(atacante)) {
            client.agregarMensajeEstado("Estado: Ataque enviado con exito");
        }
    }


}