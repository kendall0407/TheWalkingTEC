/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package com.mycompany.walkingtec;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;


/**
 *
 * @author josed
 */
public class Pantalla extends JFrame {
    
    public Pantalla() {
        initComponents();
    }
      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WalkingTEC");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panelFondo = new JPanel() {
            private Image fondo = new ImageIcon(getClass().getResource("/images/fondo.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout()); 
        setContentPane(panelFondo);

        JButton nuevaPartida = new JButton("Partida Nueva");
        JButton continuarPartida = new JButton("Continuar Juego");

        Font fuente = new Font("Arial", Font.BOLD, 18);
        nuevaPartida.setFont(fuente);
        continuarPartida.setFont(fuente);   
        // Cambiar el color a naranja pastel
        Color naranjaPastel = new Color(255, 178, 102);
        nuevaPartida.setBackground(naranjaPastel);
        continuarPartida.setBackground(naranjaPastel);
        nuevaPartida.setForeground(Color.WHITE);
        continuarPartida.setForeground(Color.WHITE);
        nuevaPartida.setFocusPainted(false);
        continuarPartida.setFocusPainted(false);

        nuevaPartida.addActionListener(e -> {
            new PantallaJuego(1).setVisible(true);
            dispose();
        });

        continuarPartida.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona tu partida guardada");

            int opcion = chooser.showOpenDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                File archivo = chooser.getSelectedFile();
                try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(archivo))) {
                    Partida partida = (Partida) input.readObject();
                    new PantallaJuego(partida.getNivel()).setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar la partida.");
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFondo.add(nuevaPartida, gbc);
        gbc.gridx = 1;
        panelFondo.add(continuarPartida, gbc);

    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            new Pantalla().setVisible(true);
        });
    }
}
