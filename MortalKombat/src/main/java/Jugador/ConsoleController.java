/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Models.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JTextArea;

/**
 *
 * @author kendall-sanabria
 */
public class ConsoleController {
    private JTextArea consola;
    private Client client;
    private int lockedPosition;
    
    public ConsoleController(Client client) {
        this.client = client; 
        this.consola = client.getRefFrame().getConsola();
        lockedPosition = consola.getDocument().getLength();
        suscripciones();
        
    }
    
    public void escribirConsola(String msg) {
        consola.append("[SERVIDOR] " + msg + "\n > ");
        lockedPosition = consola.getDocument().getLength();
    }
    public void suscripciones() {
        //------- Cursor no borre nada anterior y se quede en la linea que va a escribir ---------
        // Guardar la posición donde el usuario no puede borrar        

        // Listener para prevenir borrado de texto anterior
        consola.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int keyCode = e.getKeyCode();
                int caretPos = consola.getCaretPosition();

                // Prevenir backspace y delete antes de la posicion bloqueada
                if ((keyCode == java.awt.event.KeyEvent.VK_BACK_SPACE || 
                     keyCode == java.awt.event.KeyEvent.VK_DELETE) && 
                    caretPos <= lockedPosition) {
                    e.consume();
                }

                // Cuando presiona ENTER, procesar comando y bloquear nueva posicion
                if (keyCode == java.awt.event.KeyEvent.VK_ENTER) {
                    e.consume(); // Prevenir el Enter por defecto
                    recibirComandos();
                    consola.append("\n > ");
                    lockedPosition = consola.getDocument().getLength();
                    consola.setCaretPosition(lockedPosition);
                    
                    
                }
            }
        });

        // Prevenir mover el cursor antes de la posicion bloqueada con mouse
        consola.addCaretListener(e -> {
            if (consola.getCaretPosition() < lockedPosition) {
                consola.setCaretPosition(lockedPosition);
            }
        });
    }
    
    public void recibirComandos() {
        String command = consola.getText().substring(lockedPosition).trim();
        if (command.isEmpty()) {
            return;
        } else if (command.toLowerCase().equals("iniciar") && client.getClientModel().getPeleadores().size() == 4) {
            this.client.enviarServer(command);
            return;
        } else if (command.toLowerCase().contains("crear")) {
            String[] partes = command.split("-");
            
            if (client.crearLuchador(partes) != -1) {
                client.getRefFrame().crearLuchadorEquipo(partes[1], "100%",partes[2]);
                consola.append("\n > Creado exitosamente!");
                lockedPosition = consola.getDocument().getLength();
                return;
            }             
        } else if (command.toLowerCase().contains("log")) {
            File file = new File("LOG.txt");
            try {
                Desktop.getDesktop().open(file);
                return;
            } catch (IOException ex) {
                System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } else if (command.toLowerCase().contains("predeterminado")) {
            crearLuchadoresPredeterminados();
            return;
        }
        //TODO: revisar comandos
        CommandProcessor processor = new CommandProcessor();
        Command commandObj = processor.process(command, this.client.getID());
        
        if (commandObj == null) {
            consola.append("\n > [ERROR 103] Comando '" + command + "' no reconocido");
            lockedPosition = consola.getDocument().getLength();
            return;
        } else if (commandObj instanceof AttackCommand) {
            String[] partes = command.split("-");
            Peleador luch = this.client.getClientModel().getLuchador(partes[1]);
            
            if (luch == null) {
                consola.append("\n > [ERROR] Luchador no existe");
                return;
            }

            int[] danos = this.client.getClientModel().getLuchador(partes[1]).getDañosPorTipo(partes[2]);

            if (danos == null) {
                consola.append("\n > [ERROR 00] Ya no hay armas por usar, recargalas!");
                return;
            }
            
            String danosT = Arrays.toString(danos);
            commandObj = processor.processAtack(command, danosT, Integer.toString(client.getID()));
            
            if (commandObj == null) {
                consola.append("\n > [ERROR] No se pudo procesar el ataque");
                return;
            }
            this.client.getClientModel().setUltimoPeleador(luch); 

        }
        try {
            client.getSender().writeObject(commandObj);
            client.getSender().flush();
            commandObj.processInClient(client);
            consola.append("\n > [OK] Enviado: " + commandObj.getClass().getSimpleName());
            lockedPosition = consola.getDocument().getLength();
        } catch (IOException e) {
            consola.append("\n > [ERROR] No se pudo enviar el comando: " + e.getMessage());
            lockedPosition = consola.getDocument().getLength();
            
        }
        

        try (FileWriter fw = new FileWriter("LOG.txt", true)) { 
            LocalDateTime fechaHora = LocalDateTime.now();
            fw.write("[" + fechaHora.toString() + "] Comando '" + commandObj.getClass().getSimpleName() + 
                    "' con parametros '" + command + "'\n");//falta resultado
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private void crearLuchadoresPredeterminados() {
        String[] comandos = {
            "crear-Pyron-fuego-espada-daga-arco-hacha-baston",
            "crear-Aqualon-agua-tridente-red-lanza-escudo-orbe",
            "crear-Terron-tierra-martillo-mazo-hacha-pico-escudo",
            "crear-Ventus-aire-dagas-arco-lanza-espada-shuriken"
        };

        for (String cmd : comandos) {
            String[] partes = cmd.split("-");

            client.crearLuchador(partes);
            client.getRefFrame().crearLuchadorEquipo(partes[1], "100%",partes[2]);

        }

        lockedPosition = consola.getDocument().getLength();
    }

}
