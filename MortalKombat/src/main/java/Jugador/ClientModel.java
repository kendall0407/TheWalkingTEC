/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Servidor.User;
import Servidor.UserDatabase;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kendall-sanabria
 */
public class ClientModel {
    private Client client;
    private ArrayList<Peleador> peleadores = new ArrayList<>();
    private int vida;
    private int[] status;
    private HashMap<String, Integer> contrincante = new HashMap<>();
    private HashMap<String, Integer> ranking = new HashMap<>();
    private Peleador ultimoPeleador;
    public boolean comodin = false;
    private String usuario;
    private String contrasena;
    private UserDataBase ub;
    private User user;
    
    public ClientModel(Client cl) {
        ub = new UserDataBase();
        ub.load();
        this.client = cl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Peleador> getPeleadores() {
        return peleadores;
    }
    
    public void crearLuchador(String[] parametros) {
        Peleador p = new Peleador(parametros);
        peleadores.add(p);
    }

    public int[] getStatus() {
        return status;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }
    
    public int getVida() {
        return vida;
    }
    
    public Peleador getLuchador(String luchador) {
        for (int i = 0; i < peleadores.size(); i++)  {
            if (peleadores.get(i).getNombre().equals(luchador))
                return peleadores.get(i);
        }
        return null;
    }
    
    public int getLuchadorArma(String peleador, String arma, int tipo) {
        for (int i = 0; i < peleadores.size(); i++)  {
            if (peleadores.get(i).getNombre().equals(peleador)) {
                int[] armaTipos = peleadores.get(i).getDañosPorTipo(arma);
                int dano = armaTipos[tipo];
                return dano;
            }            
        }
        return -1;
    }

    public Peleador getUltimoPeleador() {
        return ultimoPeleador;
    }

    public void setUltimoPeleador(Peleador ultimoPeleador) {
        this.ultimoPeleador = ultimoPeleador;
    }
    
    public void recibirDano(String[] params) {
        String peleador = params[0];
        String arma = params[1];
        String texto = params[3];
        int atacante = Integer.parseInt(params[4]);
        String tipoAtaque = params[5];
        
        int dano = 0;
        texto = texto.replace("[", "").replace("]", "");
        String[] partes = texto.split(",");
        int[] numeros = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {
            numeros[i] = Integer.parseInt(partes[i].trim());
            System.out.println(numeros[i]);
            
        }
        
        switch (tipoAtaque.toLowerCase()) {
            case "fuego":
                dano = numeros[0];
                break;

            case "aire":
                dano = numeros[1];
                break;

            case "agua":
                dano = numeros[2];
                break;

            case "magia blanca":
                dano = numeros[2];
                break;

            case "magia negra":
                dano = numeros[2];
                break;

            case "electricidad":
                dano = numeros[2];
                break;

            case "hielo":
                dano = numeros[2];
                break;

            case "acid":
                dano = numeros[2];
                break;

            case "espiritualidad":
                dano = numeros[2];
                break;

            case "hierro":
                dano = numeros[2];
                break;

            default:
                // ataque no reconocido
                break;
        }
        client.actualizarReceivedAttacks("Has sido atacado por J" + atacante + 
                " con "+peleador + " [" + tipoAtaque  +"] con ARMA -> " + arma+
                " dano: " + dano + "%");
        if(ultimoPeleador == null)
            ultimoPeleador = peleadores.getFirst();
        int vidaS = this.ultimoPeleador.recibirDano(dano);
        if(vidaS <=0) {
            client.escribirMensajeConsola("Se te murio un peleador :(");
            client.enviarMsgServer("muerte");
            client.actualizarLuchadorEquipo(peleadores.indexOf(ultimoPeleador), ultimoPeleador.getNombre(), Integer.toString(vidaS));
            if (!peleadores.isEmpty()){
                ultimoPeleador = peleadores.getFirst();
                this.peleadores.remove(ultimoPeleador);
            } else {
                client.enviarMsgServer("J" + client.getID() + " perdio!");
                client.escribirMensajeConsola("Perdiste!!!");
                client.disconnect();
                
            }
        }
        vida -= vidaS * 35 /100; //representacion
        client.escribirMensajeConsola("Vida total: " + vida);
        System.out.println("Vida total: " + vida);
    }
    
    public void recibirComodin(String[] params) {
        String peleador1 = params[0];
        String arma1 = params[1];
        String peleador2 = params[2];
        String arma2 = params[3];
        String texto1 = params[4];
        String texto2 = params[5];
        int atacante = Integer.parseInt(params[6]);
        String tipoAtaque1 = params[7];
        String tipoAtaque2 = params[8];
        
        int dano1 = 0;
        int dano2 = 0;
        texto1 = texto1.replace("[", "").replace("]", "");
        String[] partes = texto1.split(",");
        int[] numeros = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {
            numeros[i] = Integer.parseInt(partes[i].trim());
            
        } 
        
        switch (tipoAtaque1.toLowerCase()) {
            case "fuego":
                dano1 = numeros[0];
                dano2 = numeros[0];
                break;

            case "aire":
                dano1 = numeros[1];
                dano2 = numeros[1];
                break;

            case "agua":
                dano1 = numeros[2];
                dano2 = numeros[2];
                break;

            case "magia blanca":
                dano1 = numeros[3];
                dano2 = numeros[3];
                break;

            case "magia negra":
                dano1 = numeros[4];
                dano2 = numeros[4];
                break;

            case "electricidad":
                dano1 = numeros[5];
                dano2 = numeros[5];
                break;

            case "hielo":
                dano1 = numeros[6];
                dano2 = numeros[6];
                break;

            case "acid":
                dano1 = numeros[7];
                dano2 = numeros[7];
                break;

            case "espiritualidad":
                dano1 = numeros[8];
                dano2 = numeros[8];
                break;

            case "hierro":
                dano1 = numeros[9];
                dano2 = numeros[9];
                break;

            default:
                // ataque no reconocido
                break;
        }
        client.actualizarReceivedAttacks("Has sido atacado por J" + atacante + 
                " con "+peleador1 + " [" + tipoAtaque1  +"] con ARMA -> " + arma1+
                " dano: " + dano1 + "%" + "uso comodin, asi que ataco tambien con:\n" +
                peleador2 + "[" + tipoAtaque2 + "] con ARMA -> " + arma2 + "dano: " +
                dano2 + "%");
        int vidaS = this.ultimoPeleador.recibirDano(dano1);
        vidaS = this.ultimoPeleador.recibirDano(dano2);
        if(vidaS <=0) {
            client.escribirMensajeConsola("Se te murio un peleador :(");
            client.actualizarLuchadorEquipo(peleadores.indexOf(ultimoPeleador), ultimoPeleador.getNombre(), Integer.toString(vidaS));
            if (!peleadores.isEmpty()){
                ultimoPeleador = peleadores.getFirst();
                this.peleadores.remove(ultimoPeleador);
            } else {
                client.enviarMsgServer("J" + client.getID() + " perdio!");
                client.escribirMensajeConsola("Perdiste!!!");
                client.disconnect();
            }
            
        }
        vida -= vidaS * 35 /100; //representacion
        client.escribirMensajeConsola("Vida total: " + vida);
        System.out.println("Vida total: " + vida);
    }
    
    private int[] stringAEnteros(String s) {
        s = s.replace("[", "").replace("]", "");
        String[] partes = s.split(",");
        int[] arr = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            arr[i] = Integer.parseInt(partes[i].trim());
        }
        return arr;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public UserDataBase getUb() {
        return ub;
    }



    public HashMap<String, Integer> getContrincante() {
        return contrincante;
    }

    public void setContrincante(HashMap<String, Integer> contrincante) {
        this.contrincante = contrincante;
    }

    public HashMap<String, Integer> getRanking() {
        return ranking;
    }

    public void setRanking(HashMap<String, Integer> ranking) {
        this.ranking = ranking;
    }
    
    
}
