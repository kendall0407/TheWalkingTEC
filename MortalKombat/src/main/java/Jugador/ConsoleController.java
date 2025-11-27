/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Models.*;
import java.io.IOException;
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
        } else if (command.toLowerCase().equals("iniciar")) {
            this.client.enviarServer(command);
            return;
        }

        CommandProcessor processor = new CommandProcessor();
        Command commandObj = processor.process(command);
        
        if (commandObj == null) {
            consola.append("\n > [ERROR 103] Comando '" + command + "' no reconocido");
            lockedPosition = consola.getDocument().getLength();
            return;
        }
        
        try {
            client.getSender().writeObject(commandObj);
            client.getSender().flush();

            consola.append("\n > [OK] Enviado: " + commandObj.getClass().getSimpleName());
            lockedPosition = consola.getDocument().getLength();
        } catch (IOException e) {
            consola.append("\n > [ERROR] No se pudo enviar el comando: " + e.getMessage());
            lockedPosition = consola.getDocument().getLength();
            
        }
    }
    
    
}
