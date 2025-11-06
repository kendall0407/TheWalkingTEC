/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

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
    private String direccion;

    public Luchador(String civilizacion, String nombre, Habilidad habilidad, int representacion, int poder, int sanidad, int resistencia) {
        this.civilizacion = civilizacion;
        this.nombre = nombre;
        this.habilidad = habilidad;
        this.representacion = representacion;
        this.poder = poder;
        this.sanidad = sanidad;
        this.resistencia = resistencia;
    }
    
    
}
