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
public class UnderseaVolcanoes extends Habilidad {
    private ArrayList<Celda> celdasVolcan = new ArrayList<>();
    
    public UnderseaVolcanoes() {
        this.ataqueBase = "Volcano Rising";
        this.ataqueSecundaria = "Volcano Explosion";
        this.ataqueEspecial = "Thermal Rush";


    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        int dano = 100;

        int numero = (int)(Math.random() * 10) + 1;
        if (x>0 && x <19-numero && y > 0 && y < 19-numero) {
            for (int i = 0; i < numero; i++){
                for (int j = 0; j < numero; j++) {
                    celdas[i][j].dano(dano);
                }
            }
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        x = celdasVolcan.get(x).getColumna();
        y = celdasVolcan.get(x).getFila();
        
        int filas = 20;
        int columnas = 20;

        int piedras = 5 * 10;                     // cantidad de piedras
        int dano = 20;                                       // cada piedra daña 20%

        // Disparar piedras alrededor del volcán
        for (int i = 0; i < piedras; i++) {
            // Elegir una celda aleatoria cerca del volcán (onda)
            int offsetX = (int)(Math.random() * (5 * 2 + 1)) - 5; 
            int offsetY = (int)(Math.random() * (5 * 2 + 1)) - 5;

            int puntoX = x + offsetX;
            int puntoY = y + offsetY;

            // Limitar dentro del tablero
            if (puntoX < 0) puntoX = 0;
            if (puntoX >= filas) puntoX = filas - 1;
            if (puntoY < 0) puntoY = 0;
            if (puntoY >= columnas) puntoY = columnas - 1;

            celdas[puntoX][puntoY].dano(dano);

        }

    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        for (int i = 0; i < celdasVolcan.size(); i++) {
            int centroX = celdasVolcan.get(i).getFila();
            int centroY = celdasVolcan.get(i).getColumna();
            int danoVolcan = 10; // daño base del volcán

            // Generar duración aleatoria del sobrecalentamiento 5–6 segundos
            int segundos = (int)(Math.random() * 2) + 5; // 5 o 6

            // Calcular daño total por casilla
            int danoTotal = danoVolcan * segundos; // ej: 5% × 5s = 25%

            int radio = 5; // radio de 5 casillas

            // Aplicar daño en todas las casillas dentro del radio
            for (int xi = centroX - radio; xi <= centroX + radio; xi++) {
                for (int yi = centroY - radio; yi <= centroY + radio; yi++) {
                    // Limitar a bordes del tablero
                    if (xi < 0 || xi > 19 || yi < 0 || yi > 19) continue;

                    // Aplicar daño
                    celdas[xi][yi].dano(danoTotal);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "Undersea Volcanoes ";
    }
    
    @Override
    public void desplegarMenu(Client client){
        VolcanoGUI menu = new VolcanoGUI(client);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
