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
public class ReleaseKraken extends Habilidad {

    public ReleaseKraken() {
        this.ataqueBase = "Tentaculos";
        this.ataqueSecundaria = "Kraken Breath";
        this.ataqueEspecial = "Release The Kraken!";

    }

    @Override
    public void ataqueBase(int x, int y, Celda[][] celdas) {
        if (x> 0 && x <17 && y > 0 && y < 17){
            int dano = 100;
            celdas[x][y].dano(dano);
            celdas[x+1][y+2].dano(dano);
            celdas[x+2][y+3].dano(dano);
        }
    }

    @Override
    public void ataqueSecundario(int x, int y, Celda[][] celdas) {
        int numero = (int)(Math.random() * 8) + 1; //dana de 1 a 8 casillas
        int dano = 100;
        if (x>0 && x <11 && y > 0 && y < 11) {
            for (int i = 0; i < numero; i++){
                celdas[x+i][y].dano(i);
            }
        }
        
    }

    @Override
    public void ataqueEspecial(int x, int y, Celda[][] celdas) {
        int dano = 100;
        if (x>0 && x <10 && y > 0 && y < 10) {
            for (int i = 0; i < 10; i++){
               for (int j = 0; j < 10; j++){
                   celdas[i][j].dano(dano);
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
