/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package com.mycompany.walkingtec;

import java.awt.*;
import javax.swing.SwingUtilities;


/**
 *
 * @author josed
 */
public class Pantalla extends javax.swing.JFrame {
    private Reliquia reliquia;
    private CampoBatalla campo;
    private int nivel = 1;
    
    public Pantalla() {
        initComponents();
        inicializar();
    }
    
    private void inicializar() {
        setLayout(new BorderLayout());
        
        CampoBatalla campo = new CampoBatalla(this);
        setContentPane(campo);
        this.campo = campo;
        pack();
        setLocationRelativeTo(null);

        //while {  condiciones para que el mae reinicie o avance, continua
        campo.jugar(nivel);
        nivel++;
        
        //}
    }
   
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WalkingTEC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            new Pantalla().setVisible(true);
        });
    }
}
