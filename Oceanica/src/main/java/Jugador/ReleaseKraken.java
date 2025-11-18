/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author kendall-sanabria
 */
public class ReleaseKraken extends Habilidad {

    public ReleaseKraken() {
        this.ataqueBase = "Tentaculos";
        this.ataqueSecundaria = "Kraken Breath";
        this.ataqueEspecial = "Release The Kraken!";

    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        int filas = celdas.length;
        int columnas = celdas[0].length;
        int dano = 100;

        // Posiciones relativas de los 3 tentáculos (pueden ser abajo, derecha, diagonal)
        int[][] offsets = { {0, 0}, {1, 2}, {2, 3} };

        for (int[] offset : offsets) {
            int tx = x + offset[0];
            int ty = y + offset[1];

            // validar límites del tablero
            if (tx >= 0 && tx < filas && ty >= 0 && ty < columnas) {

                // recorrer radio de 1 alrededor de la celda del tentáculo
                for (int i = tx - 1; i <= tx + 1; i++) {
                    for (int j = ty - 1; j <= ty + 1; j++) {
                        if (i >= 0 && i < filas && j >= 0 && j < columnas) {
                            celdas[i][j].dano(dano);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        int filas = celdas.length;
        int columnas = celdas[0].length;
        int dano = 100;

        // Número de casillas a destruir (1 a 8)
        int numero = ThreadLocalRandom.current().nextInt(1, 9);

        // Escoger dirección aleatoria: 0=arriba, 1=abajo, 2=derecha, 3=izquierda
        int direccion = ThreadLocalRandom.current().nextInt(0, 3);

        for (int i = 0; i < numero; i++) {
            int nx = x;
            int ny = y;

            switch (direccion) {
                case 0 -> nx = x - i; // arriba
                case 1 -> nx = x + i; // abajo
                case 2 -> ny = y + i; // derecha
                case 3 -> ny = y - i; // izquierda
            }

            // Validar límites
            if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas) {
                celdas[nx][ny].dano(dano);
            }
        }
    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        int dano = 100;
        int filas = celdas.length;
        int columnas = celdas[0].length;

        // Radio máximo del ataque
        int radioMax = 9;

        for (int radio = 1; radio <= radioMax; radio++) {
            // Recorremos todas las posiciones dentro del radio actual
            for (int dx = -radio; dx <= radio; dx++) {
                for (int dy = -radio; dy <= radio; dy++) {
                    int nx = x + dx;
                    int ny = y + dy;

                    // Validar que la celda esté dentro del tablero y dentro del radio circular
                    if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas) {
                        if (Math.abs(dx) + Math.abs(dy) <= radio) { // opción de radio en "manhattan"
                            celdas[nx][ny].dano(dano);
                        }
                    }
                }
            }
        }
    }

    
    @Override
    public String toString() {
        return "Release Kraken";
    }
    
    @Override
    public void desplegarMenu(Client client){
        KrakenGUI menu = new KrakenGUI(client);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}