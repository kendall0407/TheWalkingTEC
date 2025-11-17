/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author kendall-sanabria
 */
public class Civilizacion implements Serializable {
    private String nombreCivilizacion;
    private Luchador luchador1;
    private Luchador luchador2;
    private Luchador luchador3;
    private ArrayList<Luchador> luchadores;

    public Civilizacion(String nombreCivilizacion, Luchador luchador1, Luchador luchador2, Luchador luchador3) {
        this.nombreCivilizacion = nombreCivilizacion;
        this.luchador1 = luchador1;
        this.luchador2 = luchador2;
        this.luchador3 = luchador3;
        this.luchadores = new ArrayList<>();
        if (luchador1 != null) this.luchadores.add(luchador1);
        if (luchador2 != null) this.luchadores.add(luchador2);
        if (luchador3 != null) this.luchadores.add(luchador3);
        
    }

    public String getNombreCivilizacion() {
        return nombreCivilizacion;
    }

    public Luchador getLuchador(int num) {
        switch (num) {
            case 1:
                return luchador1;
            case 2:
                return luchador2;
            case 3:
                return luchador3;
            default:
                return null;
        }
    }

    public void setLuchadores(ArrayList<Luchador> luchadores) {
        this.luchadores = luchadores;
    }

    public ArrayList<Luchador> getLuchadores() {
        return luchadores;
    }
    
}
