/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author kendall-sanabria
 */
public class Celda extends JPanel {
    private int fila;
    private int columna;
    private String tipo;
    private Luchador ocupada;
    private Object contenido; // Luchador
    private FrameClient reframe;
    private int estado;  // 0 es viva, 1 es muerta
    private double vida;
    private int poder;
    private int sanidad;
    private int resistencia;
    
    public Celda(int fila, int columna, FrameClient reframe) {
        this.fila = fila;
        this.columna = columna;
        this.ocupada = null;
        this.tipo = "vacia";
        this.reframe = reframe;
        this.estado = 0;
        this.vida = 99.9;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Agregar listener de clic
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarInfo();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
        });
    }
    
    public void mostrarInfo() {
        String estado = "";
        Luchador l = estaOcupada();
        String luchador = "";
        if (this.estado ==1) 
            estado = "Estado: muerta"; 
        else
            estado = "Estado: vida -> 100";
        if (l != null) 
            luchador = l.getNombre();
        else
            luchador = "Celda sin ocupar";
        this.reframe.agregarEstado("x = "+this.fila+", y = "+this.columna
        + "\nJugador de civilizacion: " + reframe.getClient().getCivilizacion().getNombreCivilizacion()
        +"\n" +estado + "\nLuchador: " + luchador + "\nSanidad aplicada: " + this.sanidad 
        + " |  Poder aplicado: " + this.poder + " | Resistencia aplicada: "+ this.resistencia);
        /* falta Mostrar
        quién la aniquiló, todos
        los ataques recibidos, sanidad, escudos, etc.
        */
        
    }
    
    public void morir() {
        //TODO: quien la mato
        this.estado = 1;
        setBackground(Color.BLACK);
    }
    public void ocupar(Luchador luchador) {
        ocupada = luchador;
    }
    
    public void dano (int dano) {
        if (resistencia!=0)
            dano -= dano * resistencia / 100; //resta porcentaje de resistencia de celda
        this.vida -= dano;
        if (this.vida <= 0) 
            morir();
    }
    
    public void aplicarPoder(int poder) {
        this.poder += poder;
    }
    public void aplicarSanidad(int sanidad) {
        this.sanidad += sanidad;
    }
    public void aplicarResistencia(int resistencia) {
        this.resistencia += resistencia;
    }
    
    public Luchador estaOcupada() {
        return ocupada;
    }
    // Getters y setters
    public int getFila() { 
        return fila; 
    }
    
    public int getColumna() { 
        return columna; 
    }

    public Object getContenido() { 
        return contenido; 
    }
 
}
