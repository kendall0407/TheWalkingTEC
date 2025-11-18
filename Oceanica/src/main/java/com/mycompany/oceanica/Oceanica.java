/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.oceanica;

import java.awt.*;
import javax.swing.*;
import Jugador.Configuracion;

/**
 * Ventana principal grande con imagen de fondo y botón "Jugar".
 * Al presionar Jugar se abre Configuracion y se cierra esta ventana.
 */
public class Oceanica extends JFrame {

    public Oceanica() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 720); // pantalla grande
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel que dibuja la imagen de fondo escalada
        BackgroundPanel bgPanel = new BackgroundPanel("/images/fondo.png");
        bgPanel.setLayout(new GridBagLayout()); // para centrar fácilmente

        // Botón celeste "Jugar"
        JButton btnJugar = new JButton("Jugar");
        btnJugar.setPreferredSize(new Dimension(220, 60));
        btnJugar.setFont(new Font("SansSerif", Font.BOLD, 24));
        btnJugar.setFocusPainted(false);
        btnJugar.setBackground(new Color(135, 206, 250)); // celeste
        btnJugar.setForeground(Color.BLACK);

        // Acción del botón: abrir Configuracion y cerrar esta ventana
        btnJugar.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                Configuracion cfg = new Configuracion();
                cfg.setLocationRelativeTo(null);
                cfg.setVisible(true);
            });
            this.dispose();
        });

 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(600, 0, 80, 0); // mover hacia abajo
        bgPanel.add(btnJugar, gbc);

        add(bgPanel, BorderLayout.CENTER);
    }

    // Panel que dibuja la imagen de fondo escalada al tamaño del panel
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String resourcePath) {
            try {
                java.net.URL imgUrl = getClass().getResource(resourcePath);
                if (imgUrl != null) {
                    backgroundImage = new ImageIcon(imgUrl).getImage();
                } else {
                    System.err.println("No se encontró " + resourcePath +
                        " en el classpath. Coloca fondo.png en resources o en la carpeta src.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                int w = getWidth();
                int h = getHeight();
                g.drawImage(backgroundImage, 0, 0, w, h, this);
            }
        }
    }

    // Main para probar esta pantalla directamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Oceanica m = new Oceanica();
            m.setVisible(true);
        });
    }
}