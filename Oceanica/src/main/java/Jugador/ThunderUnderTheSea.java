/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author kendall-sanabria
 */
public class ThunderUnderTheSea extends Habilidad {

    public ThunderUnderTheSea() {
        this.ataqueBase = "Thunder Rain";
        this.ataqueSecundaria = "Poseidon Thunders";
        this.ataqueEspecial = "Eel Atack";

    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        int rayos = 100;
        for (int i = 0; i < rayos; i++){
            int dano = (int)(Math.random() * (20 - 10 + 1)) + 10;
            x = ThreadLocalRandom.current().nextInt(0, 20);
            y = ThreadLocalRandom.current().nextInt(0, 20);
            celdas[x][y].dano(dano);
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        int rayos = ThreadLocalRandom.current().nextInt(5, 11);      // 5–10 rayos
        int ondaCasillas = ThreadLocalRandom.current().nextInt(2, 11);  // 2–10 casillas
        int dano = 40;

        int filas = 20;      
        int columnas = 20;

        x = ThreadLocalRandom.current().nextInt(0, 20);
        y = ThreadLocalRandom.current().nextInt(0, 20);

        for (int i = 0; i < rayos; i++) {
            // recalculemos un nuevo punto aleatorio alrededor de (x, y) dentro de la onda
            int startX = x - ondaCasillas;
            int endX   = x + ondaCasillas;
            int startY = y - ondaCasillas;
            int endY   = y + ondaCasillas;

            // Verificar límites del tablero
            if (startX < 0) startX = 0;
            if (endX >= filas) endX = filas - 1;
            if (startY < 0) startY = 0;
            if (endY >= columnas) endY = columnas - 1;

            // Aplicar daño a todas las celdas en la onda
            for (int xi = startX; xi <= endX; xi++) {
                for (int yi = startY; yi <= endY; yi++) {
                    celdas[xi][yi].dano(dano);
                }
            }

            // mover el centro de la onda un poco aleatoriamente
            x = ThreadLocalRandom.current().nextInt(0, filas); 
            y = ThreadLocalRandom.current().nextInt(0, columnas); 
        }
    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        int anguilas = (int)(Math.random() * (100 - 25 + 1)) + 25;
        int descargas = ThreadLocalRandom.current().nextInt(1, 10); 
        int dano = 10*descargas;
        
        celdas[x][y].dano(dano*anguilas);
        celdas[x][y+1].dano(dano*anguilas);
        celdas[x+1][y].dano(dano*anguilas);
        celdas[x+2][y+1].dano(dano*anguilas);
        
    }
    @Override
    public String toString() {
        return "Thunders Under The Sea ";
    }

    @Override
    public void desplegarMenu(Client client){
        ThunderGUI menu = new ThunderGUI(client);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}