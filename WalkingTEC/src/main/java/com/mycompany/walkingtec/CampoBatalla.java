/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.walkingtec;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author josed
 */
public class CampoBatalla extends javax.swing.JPanel {
    private int nivel = 1; 
    private static final int filas = 25;
    private final int columnas = 25;
    private Celda[][] celdas = new Celda[filas][columnas];
    private Reliquia reliquia;
    private ArrayList<Zombie> infectados = new ArrayList<Zombie>();
    private Pantalla refPantalla;
    
    public CampoBatalla(Pantalla refPantalla) {
        initComponents();
        this.refPantalla = refPantalla;
        
        setLayout(new GridLayout(filas,columnas));
        inicializarCeldas();
        
        this.reliquia = colocarReliquia();
        
    }

    
    private void inicializarCeldas(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
                add(celdas[i][j]);
            }
            
        }
    }
    
    private Reliquia colocarReliquia(){
        int centroFila = filas/2;
        int centroColumna = columnas/2;
        
  
        ImageIcon icono = new ImageIcon(getClass().getResource("/images/Reliquia.png"));
        Image imagen = icono.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        JLabel lblReliquia = new JLabel(new ImageIcon(imagen)); 
        Reliquia reliquia = new Reliquia(lblReliquia);
        
        celdas[centroFila][centroColumna].setLayout(new BorderLayout());
        celdas[centroFila][centroColumna].setImgEstructura(lblReliquia);
        
        //colorear alrededor de la reliquia
        for (int i = centroFila - 1; i <= centroFila + 1; i++) {
            for (int j = centroColumna - 1; j <= centroColumna + 1; j++) {
                if (i == centroFila && j == centroColumna) continue; // salta la celda central
                celdas[i][j].setColorEstructura();
            }
        }
        return reliquia;
    }
    
    public void generarZombies(int nivel) {
        // rangos válidos: 0-4 (5 celdas) y 21-24 (4 celdas), total 9 celdas
        int rango = 9;
        int aparicion = (nivel * 4) + 10;
        Random rand = new Random();

        for (int i = 0; i < aparicion; i++) {
            // elegimos un índice uniforme entre las 9 celdas válidas
            int idxFila = rand.nextInt(rango);
            int idxColumna = rand.nextInt(rango);
            int fila = mapIndexToValid(idxFila);
            int columna = mapIndexToValid(idxColumna);
            
            if (celdas[fila][columna].estaOcupada()) { 
                i--; 
                continue; 
            }

            ImageIcon icono = new ImageIcon(getClass().getResource("/images/Zombie.png"));
            Image imagen = icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel zombieLabel = new JLabel(new ImageIcon(imagen));

            celdas[fila][columna].agregarEntidad(zombieLabel);

            infectados.add(new Zombie(zombieLabel));
        }
    }

    // mapea un índice 0..8 a los índices válidos {0,1,2,3,4,21,22,23,24}
    private int mapIndexToValid(int idx) {
        if (idx < 5) return idx;       // 0..4
        else return 21 + (idx - 5);    // 21..24
    }
    
    public Celda getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(800, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1026, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 972, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
