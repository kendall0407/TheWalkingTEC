/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

/**
 *
 * @author kendall-sanabria
 */
public class FishTelepathy extends Habilidad {

    public FishTelepathy() {
        this.ataqueBase = "Cardumen";
        this.ataqueSecundaria = "Shark Attack";
        this.ataqueEspecial = "Pulp";

    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        
        int numero = (int)(Math.random() * 201) + 100;  // 100–300
        int distribuido = (int)(Math.random() * (numero + 1));  // 0–numero
        int dano = 33*distribuido;
        for (int i = 0; i < numero; i++) {
            x = (int)(Math.random() * 20);
            y = (int)(Math.random() * 20);
            celdas[x][y].dano(dano);
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        // Escoger esquina
        int[][] esquinas = {
            {0, 0}, {19, 0}, {0, 19}, {19, 19}
        };
        int idx = (int)(Math.random() * 4);

        x = esquinas[idx][0];
        y = esquinas[idx][1];

        // rango a destruir
        int n = (int)(Math.random() * 10) + 1; // 1–10

        // Determinar dirección según esquina
        int dx = 0;
        int dy = 0;

        if (x == 0 && y == 0) {            // arriba izq
            dx = 1;  dy = 1;
        } else if (x == 19 && y == 0) {    // arriba der
            dx = -1; dy = 1;
        } else if (x == 0 && y == 19) {    // abajo izq
            dx = 1;  dy = -1;
        } else if (x == 19 && y == 19) {   // abajo der
            dx = -1; dy = -1;
        }

        // Destruir desde la esquina
        for (int i = 0; i < n; i++) {
            // Validar bordes
            if (x < 0 || x > 19 || y < 0 || y > 19) break;

            celdas[x][y].dano(50); 

            x += dx;
            y += dy;
        }
    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        int filas = 20;
        int columnas = 20;

        //enerar entre 20 y 50 pulpos
        int numeroPulpos = (int)(Math.random() * (50 - 20 + 1)) + 20;

        int tentaculosPorPulpo = 8;

        //Contador de tentáculos por casilla
        int[][] contador = new int[filas][columnas];

        //cada pulpo genera sus tentáculos
        for (int p = 0; p < numeroPulpos; p++) {
            for (int t = 0; t < tentaculosPorPulpo; t++) {
                x = (int)(Math.random() * filas);    // 0–19
                y = (int)(Math.random() * columnas); // 0–19

                contador[x][y]++;             // contar tentáculos en la celda
                celdas[x][y].dano(25);       // cada tentáculo daña 25%
            }
        }

        // Destruir casillas donde ≥4 tentáculos coinciden
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (contador[i][j] >= 4) {
                    celdas[x][y].dano(100);
                    System.out.println("Casilla (" + i + "," + j + ") destruida!");
                }
            }
        }

    }
    
    @Override
    public String toString() {
        return "Fish Telepathy";
    }
    
    @Override
    public void desplegarMenu(Client client){
        FishGUI menu = new FishGUI(client);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
