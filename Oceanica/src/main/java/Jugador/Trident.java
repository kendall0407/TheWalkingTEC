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
public class Trident extends Habilidad {

    public Trident() {
        this.ataqueBase = "Three Lines";
        this.ataqueSecundaria = "Three Numbers";
        this.ataqueEspecial = "Control The Kraken";
  
    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        int direccion = ThreadLocalRandom.current().nextInt(1, 5);
        int dano = 100;
        switch (direccion) {
            case 1: //izquierda
                if (y > 4 && y < 19){
                    for (int i = 0; i < 4; i++) {
                        if (x>0 && x < 19-i) {
                            celdas[x+i][y].dano(dano);
                            celdas[x+i][y-1].dano(dano);
                            celdas[x+i][y-2].dano(dano);
                            celdas[x+i][y-3].dano(dano);
                            x = x+ 3;
                        }
                    }
                }
            case 2:
                if (y > 0 && y < 15){
                    for (int i = 0; i < 4; i++) {
                        if (x>0 && x < 19-i) {
                            celdas[x+i][y].dano(dano);
                            celdas[x+i][y+1].dano(dano);
                            celdas[x+i][y+2].dano(dano);
                            celdas[x+i][y+3].dano(dano);
                            x = x+ 3;
                        }
                    }
                }
                
            case 3:
                if (x > 4 && x < 19){
                    for (int i = 0; i < 4; i++) {
                        if (y>0 && y < 19-i) {
                            celdas[x][y+i].dano(dano);
                            celdas[x-1][y+i].dano(dano);
                            celdas[x-2][y+i].dano(dano);
                            celdas[x-3][y+i].dano(dano);
                            y = y+ 3;
                        }
                    }
                }
                
            case 4:
                if (x > 0 && x < 15){
                    for (int i = 0; i < 4; i++) {
                        if (y>0 && y < 19-i) {
                            celdas[x][y+i].dano(dano);
                            celdas[x+1][y+i].dano(dano);
                            celdas[x+2][y+i].dano(dano);
                            celdas[x+3][y+i].dano(dano);
                            y = y+ 3;
                        }
                    }
                }
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        //Complicado: envía 3 números distintos entre 0 y 9 al contrincante. Este último debe
        //escribir 3 números distintos entre 0 y 9, si atina al menos uno de los 3 del tridente enviado,
        //explotará la cantidad de casillas que de la multiplicación de los números del Tridente
    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        //Complicado si es atacado con el Kraken, retornará ese ataque al enemigo que lo atacó
    }
    
    @Override
    public String toString() {
        return "The Trident";
    }

    @Override
    public void desplegarMenu(Client client){
        PoseidonGUI menu = new PoseidonGUI(client);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}