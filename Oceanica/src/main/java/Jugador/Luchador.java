/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.awt.Color;
import java.util.ArrayList;
/**
 *
 * @author kendall-sanabria
 */
public class Luchador {
    private String nombre;
    private String civilizacion;
    private Habilidad habilidad;
    private int representacion;
    private int poder;
    private int sanidad;
    private int resistencia;
    private String direccionImagen;
    private Color color;
    public ArrayList<Celda> celdas = new ArrayList<>();
    
    public Luchador(String civilizacion, String nombre, Habilidad habilidad, int representacion, int poder, int sanidad, int resistencia, String direccionImagen) {
        this.civilizacion = civilizacion;
        this.nombre = nombre;
        this.habilidad = habilidad;
        this.representacion = representacion;
        this.poder = poder;
        this.sanidad = sanidad;
        this.resistencia = resistencia;
        this.direccionImagen = direccionImagen;
    }
    
    public void atacar(){
        //TODO
    }
    public String getNombre() {
        return nombre;
    }

    public String getCivilizacion() {
        return civilizacion;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }

    public int getRepresentacion() {
        return representacion;
    }

    public int getPoder() {
        return poder;
    }

    public int getSanidad() {
        return sanidad;
    }

    public int getResistencia() {
        return resistencia;
    }

    public String getDireccion() {
        return direccionImagen;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Celda> getCeldas() {
        return celdas;
    }

    public void addCeldas(Celda c) {
        this.celdas.add(c);
    }
    
    
    
}
