/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

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
    private HashMap<String, Integer> status = new HashMap<>();
    private HashMap<String, Integer> contrincante = new HashMap<>();
    private HashMap<String, Integer> ranking = new HashMap<>();
    private Peleador ultimoPeleador;
    
    public ClientModel(Client cl) {
        this.client = cl;
        status.put("Wins", 0);
        status.put("Losses", 0);
        status.put("Attacks", 0);
        status.put("Success", 0);
        status.put("Failed", 0);
        status.put("Giveup", 0); 
    }

    public ArrayList<Peleador> getPeleadores() {
        return peleadores;
    }
    
    public void crearLuchador(String[] parametros) {
        Peleador p = new Peleador(parametros);
        peleadores.add(p);
    }
    
    public void recibirDano(int dano) {
        
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
        int idObjetivo = Integer.parseInt(params[2]);
        int[] arregloDanos = stringAEnteros(params[3]);
        int idAtacante = Integer.parseInt(params[4]);
        int dano = 0;
    }
    
    public int[] stringAEnteros(String s) {
        s = s.replace("[", "").replace("]", "");
        String[] partes = s.split(",");
        int[] arr = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            arr[i] = Integer.parseInt(partes[i].trim());
        }
        return arr;
    }
}
