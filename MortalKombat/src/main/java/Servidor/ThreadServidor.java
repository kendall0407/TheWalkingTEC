/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Models.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author josed
 */
public class ThreadServidor extends Thread{
    private Servidor server;
    private Socket socket;

    public ObjectInputStream listener;
    public ObjectOutputStream sender;
    private int idJugador;
    private int idObjetivo;
    private boolean isRunning = true;
    private boolean esMiTurno = false;
    private final Object turnoLock;
    private ArrayList<Integer> objetivos = new ArrayList<>();
    public boolean drawPending = false;
    public boolean noEmpate = false;
    
     // Información del jugador
    private int vida = 100;
    private int ataquesHechos = 0;
    
    public ThreadServidor(Servidor server, Socket socket, int id) {
        this.server = server;
        this.socket = socket;
        this.idJugador = id;
        this.turnoLock = this.server.turnoLock;
        try {
            sender = new ObjectOutputStream(socket.getOutputStream());
            sender.flush(); 
            listener = new ObjectInputStream(socket.getInputStream());
            
        } catch (IOException ex) {

        }
    }
    
    
    @Override
    public void run() {
        while (isRunning) {
            try {
                Object obj = listener.readObject();
               
                if (!server.gameStarted) {
                    if (obj instanceof String msg) {    
                        if (msg.equalsIgnoreCase("iniciar")) {
                            server.gameStarted = true;
                            server.setMaxConnections(server.getConections());
                            if (server.getConections() == 1) {
                                isRunning = false;
                                server.writeMessage("Se necesitan mas jugadores para iniciar la partida\n");
                                server.broadcast("Felicidades! Has ganado! Iniciaste la partida con 0 contrincantes");
                            }
                            
                            server.writeMessage("Juego iniciado");
                            server.writeMessage("Sala llena, comenzando partida...");
                            int random = new Random().nextInt(server.getConections());
                            Thread timer = new Thread(() -> {
                                try {
                                    Thread.sleep(5 * 60 * 1000); // 5 minutos
                                    System.out.println("⏳ Tiempo finalizado, un comodin saldra aleatoriamente!");

                                    String[] params = {"1"};
                                    GiveJokerCommand cmd = new GiveJokerCommand(params);
                                    try {
                                        server.getConnectedClients().get(random).getSender().writeObject(cmd);
                                        server.getConnectedClients().get(random).getSender().flush();
                                    } catch (IOException ex) {
                                        server.writeMessage("Error recibiendo ataque");
                                    } 
                                } catch (InterruptedException e) {
                                    System.out.println("Timer detenido");
                                }
                            });
                            timer.start();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                System.getLogger(ThreadConnections.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                            }
                            server.iniciarNuevaRonda(); 
                        }
                    }
                    continue;
                }

                if (obj instanceof MsgAllCommand) {
                    ((Command)obj).processForServer(this);
                    continue;
                } else if (obj instanceof PrivateMsgCommand) {
                    ((Command)obj).processForServer(this);
                    continue;
                }

                // Esperar hasta que sea mi turno
                esperarMiTurno();
                
                // Si el juego no ha iniciado o no esta corriendo, salir
                if (!isRunning) {
                    Thread.sleep(100);
                    continue;
                }
                
                // Es mi turno, esperar comando del cliente
                server.writeMessage("Esperando acción de J" + idJugador + "...");
                                
                if (obj instanceof Command) {
                    Command cmd = (Command) obj;
                    
                    // Procesar el comando
                    cmd.processForServer(this);
                    if (noEmpate) {
                        noEmpate = false;

                        // Darle turno al otro jugador sin avanzar ronda
                        darTurnoA(idObjetivo);

                        // Bloquear este hilo hasta que el servidor lo vuelva a activar
                        esperarMiTurno();

                        // Cuando vuelva a ser mi turno, simplemente continuar
                        continue;
                    }
                    // Avanzar al siguiente turno
                    server.siguienteTurno();
                    
                } else {
                    server.broadcast((String) obj);
                }
                
                // Resetear flag de turno
                synchronized (turnoLock) {
                    esMiTurno = false;
                }
            } catch (IOException ex) {
                server.writeMessage("Jugador J" + idJugador + " desconectado");
                server.getDesconectados().add(idJugador);
                isRunning = false;
                server.getConnectedClients().remove(this);
            } catch (ClassNotFoundException ex) {
                server.writeMessage("Error: Clase no encontrada");

            } catch (InterruptedException ex) {
                server.writeMessage("Thread J" + idJugador + " interrumpido");
                break;
            }
            
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Error cerrando socket: " + ex.getMessage());
        }
    }
    
    private void esperarMiTurno() throws InterruptedException {
        synchronized (turnoLock) {
            while (!esMiTurno && isRunning) {
                turnoLock.wait(100); // Esperar con timeout
            }
        }
    }
    
    public synchronized void darTurnoA(int idJugador) {
        server.setTurnoActual(idJugador); 
        notificarTurno(); // algo como notifyAll() a los hilos
    }

    
    public void notificarTurno() {
        synchronized (turnoLock) {
            esMiTurno = true;
            turnoLock.notifyAll();
        }
    }
    
    public void asignarNuevoObjetivo() {
        idObjetivo = server.elegirObjetivo(this);
        String[] params = { 
            Integer.toString(idJugador), 
            Integer.toString(idObjetivo)
        };
        AssignTargetCommand cmd = new AssignTargetCommand(params);
        
        try {
            sender.writeObject(cmd);
            sender.flush();
        } catch (IOException ex) {
            server.writeMessage("Error enviando nuevo objetivo a jugador " + idJugador);
        } 
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void addAtaqueHecho() {
        this.ataquesHechos++;
    }
    
    public int getVida() { return vida; }
    public int getAtaquesHechos() { return ataquesHechos; }
    public int getIdObjetivo() { return idObjetivo; }
    public void setIdJugador(int idJugador) { this.idJugador = idJugador; }
    public void setIdObjetivo(int idObjetivo) { this.idObjetivo = idObjetivo;  }
    
    public ObjectOutputStream getSender() {
        return sender;
    }
    
    public void nuevaRonda() {
        asignarNuevoObjetivo();
        
    }
    
    
    public void stopThread() {
        isRunning = false;
    }
        
    public Servidor getServer() {
        return server;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getIdJugador() {
        return idJugador;
    }
    
    public void recibirAtaque(String[] params){        
        //recibirDano(dano);
        ReceiveAttackCommand cmd = new ReceiveAttackCommand(params);
        try {
            sender.writeObject(cmd);
            sender.flush();
        } catch (IOException ex) {
            server.writeMessage("Error recibiendo ataque");
        } 
    }
    
    
    public ArrayList<Integer> getObjetivos() {
        return objetivos;
    }
    
    public void pasarTurno() {
        //this. empates++
        server.saltarTurno(this.idJugador);
    }
    
    public void recibirJoker(String[] params) {
        ReceiveJokerCommand cmd = new ReceiveJokerCommand(params);
        try {
            sender.writeObject(cmd);
            sender.flush();
        } catch (IOException ex) {
            server.writeMessage("Error recibiendo ataque");
        } 
    }
}
