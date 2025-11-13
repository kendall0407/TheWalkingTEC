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
    private Object contenido; // Luchador, obstáculo, etc.
    
    public Celda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.ocupada = null;
        this.tipo = "vacia";
        
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
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
        });
    }
    
    public void mostrarInfo() {
        /* Mostrar
        % de vida, si está ocupada por volcan o remolino, quién la aniquiló, todos
        los ataques recibidos, sanidad, escudos, etc. Una bitácora de cada casilla. Por supuesto, jugador a
        quién pertenece
        */
        
    }
    
    
    public void ocupar(Luchador luchador) {
        ocupada = luchador;
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
