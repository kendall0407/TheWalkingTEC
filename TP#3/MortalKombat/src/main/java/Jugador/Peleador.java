/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author kendall-sanabria
 */
public class Peleador {
    private HashMap<String, int[]> danosPorTipo = new HashMap<>();
    private String nombre;
    private String tipoAtaque;
    private HashMap<String, int[]> danosPorTipoCopia;
    private int vida;
    private int danoBase;
    
    public Peleador(String[] parametros) {
        this.nombre = parametros[1];
        this.tipoAtaque = parametros[2];
        this.vida = 100;
        
        Random r = new Random();
        this.danoBase = r.nextInt(61) + 20; // 61 = (80 - 20) + 1
        for(int i = 3; i <= 7; i++) {  // 7 por cant de parametros
            // Si el tipo de arma no tiene daño generado, se genera
            int[] danoTipos = generarDano();
            danosPorTipo.put(parametros[i], danoTipos);
        }
        
        danosPorTipoCopia = new HashMap<>();

        for (String key : danosPorTipo.keySet()) {
            int[] original = danosPorTipo.get(key);
            int[] copia = original.clone(); // duplica el array

            danosPorTipoCopia.put(key, copia);
        }
    }

    public String getTipoAtaque() {
        return tipoAtaque;
    }
    
    public String getNombre() {
        return nombre;
    }

    public int[] getDañosPorTipo(String tipo) {
        if(!danosPorTipo.isEmpty()){
            int[] danos = danosPorTipo.get(tipo);
            danosPorTipo.remove(tipo);
            return danos;
        }
        return null;
    }

    private int[] generarDano() {
        Random random = new Random();
        int[] porcentajes = new int[10];

        for (int i = 0; i < 10; i++) {
            porcentajes[i] = random.nextInt(81) + 20;  // 20–100
        }
        return porcentajes;
    }
    
    public int getDano() {
        return 0;
    }
    
    public void recargarArmas() {
        if(!danosPorTipo.isEmpty()) {
            System.out.println("Error, no se puede recargar porque aun hay armas");
            return;
        }
        danosPorTipo = new HashMap<>();

        for (String key : danosPorTipoCopia.keySet()) {
            int[] original = danosPorTipoCopia.get(key);
            int[] copia = original.clone(); // duplica el array

            danosPorTipo.put(key, copia);
        }
    }

    public HashMap<String, int[]> getDanosPorTipo() {
        return danosPorTipo;
    }

    public int getVida() {
        return vida;
    }
    
    public int recibirDano(int dano) {
        this.vida -= dano;
        if (vida <= 0) 
            danosPorTipo.clear();
        
        return vida;
    }

    public int getDanoBase() {
        return danoBase;
    }

    public void setDanoBase(int danoBase) {
        this.danoBase = danoBase;
    }
    
    
}
