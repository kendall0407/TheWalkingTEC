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
    public Peleador(String[] parametros) {
        this.nombre = parametros[1];
        this.tipoAtaque = parametros[2];
        
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
        danosPorTipo = new HashMap<>();

        for (String key : danosPorTipoCopia.keySet()) {
            int[] original = danosPorTipoCopia.get(key);
            int[] copia = original.clone(); // duplica el array

            danosPorTipo.put(key, copia);
        }
    }
}
