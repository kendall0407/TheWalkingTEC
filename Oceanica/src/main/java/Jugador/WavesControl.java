/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.util.ArrayList;

/**
 *
 * @author kendall-sanabria
 */
public class WavesControl extends Habilidad {
    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y = new ArrayList<>();
    private ArrayList<Celda> celdasBasuras = new ArrayList<>();
    private int contadorRemolinos = 0;
    
    public WavesControl() {
        this.ataqueBase = "Swirl Raising";
        this.ataqueSecundaria = "Send Human Garbage";
        this.ataqueEspecial = "Radioactive Brush";

    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        this.x.add(x);
        this.y.add(y);
        contadorRemolinos++;
        int filas = 20;       
        int columnas = 20; 

        int casillas = (int)(Math.random() * (10 - 2 + 1)) + 2; // 2–10 casillas
        int dano = 100;

        // Definir rango de la onda alrededor del punto (x, y)
        int startX = x - casillas;
        int endX   = x + casillas;
        int startY = y - casillas;
        int endY   = y + casillas;

        // Verificar límites para que no se salga del tablero
        if (startX < 0) startX = 0;
        if (endX >= filas) endX = filas - 1;
        if (startY < 0) startY = 0;
        if (endY >= columnas) endY = columnas - 1;

        // Aplicar daño a todas las casillas en el rango
        for (int xi = startX; xi <= endX; xi++) {
            for (int yi = startY; yi <= endY; yi++) {
                celdas[xi][yi].dano(dano);
            }
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        int dano = 25;
        for (int i = 0; i<10; i++) {
            int puntoX = (int)(Math.random() * 20);
            int puntoY = (int)(Math.random() * 20);
            celdas[puntoX][puntoY].dano(dano);
            celdasBasuras.add(celdas[puntoX][puntoY]);
        }
    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        int dano = 10; //x segundo
        int segundos = (int)(Math.random() * 10)+1;
        for (int i = 0; i < celdasBasuras.size(); i++){
            celdasBasuras.get(i).dano(dano*segundos);
        }
    }
    
    
    @Override
    public String toString() {
        return "Waves Control";
    }

    @Override
    public void desplegarMenu(Client client){
        WavesGUI menu = new WavesGUI(client);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
