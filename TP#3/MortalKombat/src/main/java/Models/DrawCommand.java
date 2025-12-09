/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Jugador.Client;
import Servidor.Servidor;
import Servidor.ThreadServidor;
import java.io.Serializable;

/**
 *
 * @author kendall-sanabria
 */
public class DrawCommand extends Command implements Serializable {

    public DrawCommand(String[] parameters) {
        super(CommandType.DRAW, parameters);
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        ThreadServidor objetivo = threadServidor.getServer().getConnection(threadServidor.getIdObjetivo());
        if (parameters.length >1 && threadServidor.drawPending) {
            Servidor s = threadServidor.getServer();
            if(parameters[1].equals("1")){
                s.writeMessage("J" + threadServidor.getIdJugador() + " decidio" +
                    "empatar con J" + objetivo.getIdJugador());
                s.msg("J" + objetivo.getIdJugador() + " acepto el empate", threadServidor);
                s.msg("J" + threadServidor.getIdJugador() + " acepto el empate", objetivo);
                threadServidor.pasarTurno();
                objetivo.pasarTurno();
            } else {
                s.msg("J" + objetivo.getIdJugador() + " no acepto el empate", threadServidor);
                threadServidor.noEmpate = true;
            }
        } else {
            threadServidor.drawPending = true;
            threadServidor.getServer().msg("[DRAW] J" + threadServidor.getIdJugador() + " solicita empate" +
                    ", para aceptar escribe draw-[1,2]. 1 para si, 2 para no", 
                    threadServidor.getServer().getConnection(threadServidor.getIdObjetivo()));
        }
    }

    @Override
    public void processInClient(Client client) {
        
    }
    
}
