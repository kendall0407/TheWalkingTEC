/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Models.*;
import Servidor.User;
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
    public boolean turno = false;
    public boolean sesionIniciada = false;
    
    public ConsoleController(Client client) {
        this.client = client; 
        this.consola = client.getRefFrame().getConsola();
        lockedPosition = consola.getDocument().getLength();
        suscripciones();
        
        
        
        
    }
    
    public void escribirConsola(String msg) {
        consola.append("\n > [SERVIDOR] " + msg + "\n > ");
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
        if(sesionIniciada) {
            this.client.getClientModel().getStatus();
            client.actualizarStatus(client.getClientModel().getUser().loadStats());
        }
        if (command.isEmpty()) {
            return;
        } else if (command.toLowerCase().equals("iniciar") && client.getClientModel().getPeleadores().size() == 4) {
            if (sesionIniciada) {
                this.client.enviarServer(command);
                return;
            } else {
                consola.append("\n -> [ERROR02] No has iniciado sesion aun");
            }
            
            
        } else if (command.toLowerCase().contains("crear")) {
            String[] partes = command.split("-");
            
            if (client.crearLuchador(partes) != -1) {
                client.getRefFrame().crearLuchadorEquipo(partes[1], "100%",partes[2]);
                consola.append("\n > Creado exitosamente!");
                lockedPosition = consola.getDocument().getLength();
                String[] params = {client.getClientModel().getUsuario(), 
                client.getClientModel().getUsuario(),
                client.getClientModel().getUltimoPeleador().getTipoAtaque(),
                Integer.toString(client.getClientModel().getUltimoPeleador().getVida())};
                OwnInfoCommand cmd = new OwnInfoCommand(params);
                try {
                    client.getSender().writeObject(cmd);
                    client.getSender().flush();
                } catch (IOException ex) {
                    System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                return;
            }   
        
        } else if (command.toLowerCase().contains("login")) {
            //login-usuario-contra
            //verificar en database si ya existe, sino crear con 
            //register-usuario-contra
            //de esta manera se cargan las cosas y el server tambien puede acceder a eso
            String[] partes = command.split("-");
            String usuario = partes[1];
            String contrasena = partes[2];
            User u = client.getClientModel().getUb().getUser(usuario);
            client.getClientModel().setUser(u);
            if(!client.getClientModel().getUb().userExists(usuario)) {
                consola.append("\n > Usuario no encontrado, por favor cree una cuenta");
                return;
            } else {
                if(u.getPassword().equals(contrasena)) {
                    sesionIniciada = true;
                    consola.append("\n > Se inicio sesion existosamente! Ahora crea peleadores!");
                    consola.append("Instrucciones de comandos" +
                        "\n1. 'Ataque-peleador-arma-enemigo(ID)'" +
                        "\n2. 'Consultar-jugador(ID)'" +
                        "\n3. 'Recargar'" +
                        "\n4. 'Comodin'" +
                        "\n5. 'Pass'" +
                        "\n6. 'Rendirse'" +
                        "\n7. 'Draw'" +
                        "\n8. 'all-msg'" +
                        "\n9. 'dm-msg-jugador(ID)'\nPor favor crear un personaje" +
                        "\n10. 'comodin-peleador1-arma1-peleador2-arma2-enemigo(ID)" +
                        " \n------'crear-nombre-poder-arma1-arma2-arma3-arma4-arma5'---\n > ");
                    lockedPosition = consola.getDocument().getLength();
                    client.actualizarStatus("No hay stats todavia");
                    client.getClientModel().setStatus(u.getStats());
                    String[] params = {usuario, contrasena, "0" ,"100"};
                    OwnInfoCommand cmd = new OwnInfoCommand(params);
                    try {
                        client.getSender().writeObject(cmd);
                        client.getSender().flush();
                    } catch (IOException ex) {
                        System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                    
                    return;
                } else {
                    consola.append("\n -> Contrasena incorrecta!");
                    return;
                }
            }
        } else if (command.toLowerCase().contains("register")) {
            String[] partes = command.split("-");
            String usuario = partes[1];
            String contrasena = partes[2];
            if(!client.getClientModel().getUb().userExists(usuario)) {
                User u = client.getClientModel().getUb().createUser(usuario, contrasena);
                client.getClientModel().setUser(u);
                consola.append("\n > Se creo el usuario existosamente! Ahora crea peleadores!");
                sesionIniciada = true;
                consola.append("Instrucciones de comandos" +
                        "\n1. 'Ataque-peleador-arma-enemigo(ID)'" +
                        "\n2. 'Consultar-jugador(ID)'" +
                        "\n3. 'Recargar'" +
                        "\n4. 'Comodin'" +
                        "\n5. 'Pass'" +
                        "\n6. 'Rendirse'" +
                        "\n7. 'Draw'" +
                        "\n8. 'all-msg'" +
                        "\n9. 'dm-msg-jugador(ID)'\nPor favor crear un personaje" +
                        "\n10. 'comodin-peleador1-arma1-peleador2-arma2-enemigo(ID)" +
                        " \n------'crear-nombre-poder-arma1-arma2-arma3-arma4-arma5'---\n > ");
                lockedPosition = consola.getDocument().getLength();
                client.actualizarStatus("No hay stats todavia");
                client.getClientModel().setStatus(u.getStats());
                String[] params = {usuario, contrasena, "0" ,"100"};
                OwnInfoCommand cmd = new OwnInfoCommand(params);
                try {
                    client.getSender().writeObject(cmd);
                    client.getSender().flush();
                } catch (IOException ex) {
                    System.getLogger(ConsoleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                    
                return;
            } else {
                consola.append("\n > Este suario ya existe!");
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
            
        } else if (command.toLowerCase().contains("select")) {
            String[] partes = command.split("-");
            Peleador p = client.getClientModel().getLuchador(partes[1]);
            client.getRefFrame().crearStats(p.getNombre(), Integer.toString(p.getVida()), p.getDanosPorTipo());
            return;
        } 
     
        CommandProcessor processor = new CommandProcessor();
        Command commandObj = processor.process(command, this.client.getID());
        
        if (commandObj == null) {
            consola.append("\n > [ERROR 103] Comando '" + command + "' no reconocido");
            lockedPosition = consola.getDocument().getLength();
            return;
        }

        // VERIFICAR SI EL COMANDO REQUIERE TURNO
        boolean requiereTurno = true;

        // Comandos que NO requieren turno
        if (commandObj instanceof MsgAllCommand || 
            commandObj instanceof PrivateMsgCommand ||
            commandObj instanceof SelectPlayerCommand ||
            commandObj instanceof RechargeGunsCommand) {
            requiereTurno = false;
        }

        // Si requiere turno y no es el turno del jugador
        if (requiereTurno && !turno) {
            consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
            return;
        }
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
                consola.append("\n > [ERROR 00] Ya no hay armas por usar, recargalas!"+ "\n > ");
                return;
            }
            int dano = 0;
            switch (client.getClientModel().getAtaqueEnemigo().toLowerCase()) {
                case "fuego":
                    dano = danos[0];
                    break;

                case "aire":
                    dano = danos[1];
                    break;

                case "agua":
                    dano = danos[2];
                    break;

                case "magia blanca":
                    dano = danos[3];
                    break;

                case "magia negra":
                    dano = danos[4];
                    break;

                case "electricidad":
                    dano = danos[5];
                    break;

                case "hielo":
                    dano = danos[6];
                    break;

                case "acid":
                    dano = danos[7];
                    break;

                case "espiritualidad":
                    dano = danos[8];
                    break;

                case "hierro":
                    dano = danos[9];
                    break;

                default:
                    // ataque no reconocido
                    break;
            }
            dano = luch.getDanoBase()*dano/100;
            
            String danosT = Arrays.toString(danos);
            commandObj = processor.processAtack(command, danosT, Integer.toString(client.getID()), luch.getTipoAtaque(), Integer.toString(dano));
            
            if (commandObj == null) {
                consola.append("\n > [ERROR] No se pudo procesar el ataque"+ "\n > ");
                return;
            } else if (!turno) {
                consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
                return;
            }
            this.client.getClientModel().setUltimoPeleador(luch); 
            
            
            this.client.actualizarAtaquesHechos("Atacaste al jugador J" +
                    partes[3] + "con " + luch.getNombre() + "[" + luch.getTipoAtaque() +
                    "]" + "\nArma: " + partes[2] + " Dano: " + dano + "%");
            turno = false;
        } else if (commandObj instanceof DrawCommand) {
            if (!turno) {
                consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
                return;
            }
            turno = false;
        } else if (commandObj instanceof GiveupCommand) {
            if (!turno) {
                consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
                return;
            }
            turno = false;
        } else if (commandObj instanceof PassCommand) {
            if (!turno) {
                consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
                return;
            }
            turno = false;
        } else if (commandObj instanceof UseJokerCommand) {
            if (!turno) {
                consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
                return;
            }
            
            String[] partes = command.split("-");
            Peleador luch1 = this.client.getClientModel().getLuchador(partes[1]);
            Peleador luch2 = this.client.getClientModel().getLuchador(partes[3]);
            if (luch1 == null || luch2 == null) {
                consola.append("\n > [ERROR] Luchador no existe");
                return;
            }
            if(partes.length < 6) {
                //verificar si tiene comodin
                if (client.getClientModel().comodin) {
                    //si tiene
                    if (partes.length > 0 && partes[0].equalsIgnoreCase("ataque")) {
                        partes[0] = "comodin";
                    }
                    int[] danos1 = this.client.getClientModel().getLuchador(partes[1]).getDañosPorTipo(partes[2]);
                    int[] danos2 = this.client.getClientModel().getLuchador(partes[3]).getDañosPorTipo(partes[4]);
                    if (danos1 == null || danos2 == null) {
                        consola.append("\n > [ERROR 00] Ya no hay armas por usar, recargalas!"+ "\n > ");
                        return;
                    }

                    String danosT = Arrays.toString(danos1);
                    String danosT2 = Arrays.toString(danos2);
                    commandObj = processor.processJoker(command, danosT, danosT2, 
                        Integer.toString(client.getID()), luch1.getTipoAtaque(), luch2.getTipoAtaque(), Integer.toString(luch1.getDanoBase()));

                    if (commandObj == null) {
                        consola.append("\n > [ERROR] No se pudo procesar el ataque"+ "\n > ");
                        return;
                    } else if (!turno) {
                        consola.append("\n > [ERROR161] NO ES TU TURNO!"+ "\n > ");
                        return;
                    }
                    this.client.getClientModel().setUltimoPeleador(luch2); 
                    this.client.actualizarAtaquesHechos("Atacaste al jugador J" +
                    partes[3] + "con " + luch1.getNombre() + "[" + luch1.getTipoAtaque() +
                    "]" + "\nArma: " + partes[2] + " usando comodin atacaste con " + luch2.getNombre() +
                            "["+luch2.getTipoAtaque() + "]\nArma: " + partes[4]);
                    turno = false;
                    this.client.getClientModel().comodin = false;
                    return;
                } else {
                    consola.append("\n > NO TIENES UN COMODIN QUE APLICAR AUN" + "\n > ");
                }
            } else{
                consola.append("\n > MUCHOS PARAMETROS" + "\n > ");
            }
            turno = false;
        }
            try {
                client.getSender().writeObject(commandObj);
                client.getSender().flush();
                commandObj.processInClient(client);
                consola.append("\n > [OK] Enviado: " + commandObj.getClass().getSimpleName()+ "\n > ");
                lockedPosition = consola.getDocument().getLength();
            } catch (IOException e) {
                consola.append("\n > [ERROR] No se pudo enviar el comando: " + e.getMessage()+ "\n > ");
                lockedPosition = consola.getDocument().getLength();
            }

            // VERIFICAR QUE HAY ULTIMO PELEADOR ANTES DE ENVIAR OwnInfoCommand
            Peleador ultimoPeleador = client.getClientModel().getUltimoPeleador();
            if (ultimoPeleador != null) {
                String[] params = {
                    client.getClientModel().getUsuario(), 
                    client.getClientModel().getUsuario(),
                    ultimoPeleador.getTipoAtaque(),
                    Integer.toString(ultimoPeleador.getVida())
                };
                OwnInfoCommand cmd = new OwnInfoCommand(params);
                try {
                    client.getSender().writeObject(cmd);
                    client.getSender().flush();
                } catch (IOException ex) {
                    
                }
            } else {
                consola.append("\n > [ADVERTENCIA] No hay peleador activo para enviar info al servidor");
            }

            try (FileWriter fw = new FileWriter("LOG.txt", true)) { 
                LocalDateTime fechaHora = LocalDateTime.now();
                fw.write("[" + fechaHora.toString() + "] Comando '" + 
                         (commandObj != null ? commandObj.getClass().getSimpleName() : "null") + 
                         "' con parametros '" + command + "'\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Solo resetear turno para comandos que lo requieren
            if (commandObj instanceof AttackCommand ||
                commandObj instanceof DrawCommand ||
                commandObj instanceof GiveupCommand ||
                commandObj instanceof PassCommand ||
                commandObj instanceof UseJokerCommand) {
                turno = false;


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
            try (FileWriter fw = new FileWriter("LOG.txt", true)) { 
            LocalDateTime fechaHora = LocalDateTime.now();
            fw.write("[" + fechaHora.toString() + "] Comando '" + cmd.getClass().getSimpleName() + 
                    "' con parametros '" + cmd + "'\n ");//falta resultado
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        lockedPosition = consola.getDocument().getLength();
        
                    

        turno = false;
    }

}
