/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerPackage;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author josed
 */
public class Servidor {
    private final int PORT = 1509;
    ServerSocket server;
    Socket[] clientesSocket = new Socket[4]; //Arreglo de clientes.

    public Servidor() {
        
        
        
    }
    
    //Levantar el servidor.
    public void conectarServidor(){
        try {
        server = new ServerSocket(PORT);
        } catch (IOException ex){
            
        }
    }
    
    //Recibir conexión.
    public void recibirConexion(){
        int jugadoresContador = 0;
        System.out.println("Esperando jugadores...");
        try {
            clientesSocket[jugadoresContador++] = server.accept();
            if (jugadoresContador < 4){
                System.out.println("Se conectó un jugador a la sala ()");
            } else {
                System.out.println("Sala llena, comenzando la partida...");
            }
        } catch (IOException ex) {
          
        }
    }
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.conectarServidor();
        s.recibirConexion();
    }
    
    
}
