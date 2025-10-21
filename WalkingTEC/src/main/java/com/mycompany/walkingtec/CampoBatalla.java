/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.walkingtec;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author josed
 */
public class CampoBatalla extends javax.swing.JPanel {

    private static final int filas = 25;
    private final int columnas = 25;
    private Celda[][] celdas = new Celda[filas][columnas];
    
    public CampoBatalla() {
        initComponents();
        
        setLayout(new GridLayout(filas,columnas));
        inicializarCeldas();
        colocarReliquia();
        
    }
    
    private void inicializarCeldas(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
                add(celdas[i][j]);
            }
            
        }
    }
    
    private void colocarReliquia(){
        int centroFila = filas/2;
        int centroColumna = columnas/2;
        
        
       ImageIcon icono = new ImageIcon(getClass().getResource("/src/main/java/com/mycompany/resources/reliquiaSprite.jpg"));
       Image imagen = icono.getImage().getScaledInstance(48,48,Image.SCALE_SMOOTH);
       JLabel labelReliquia = new JLabel(new ImageIcon(imagen));
       
        for (int i = centroFila - 1; i <= centroFila + 1; i++) {
            for (int j = centroColumna - 1; j <= centroColumna + 1; j++) {
                celdas[i][j].setEstructura(labelReliquia);
            }
        }
    }
    
    public Celda getCelda(int fila, int columna){
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
