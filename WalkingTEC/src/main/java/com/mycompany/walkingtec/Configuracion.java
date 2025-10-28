/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

/**
 *
 * @author kendall-sanabria
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Configuracion extends JPanel {
    private CampoBatalla campo;
    private JLabel selectedLabel;
    private Point offset;
    private PantallaJuego refPantalla;

    // Labels dinámicos para poder actualizarlos
    private JLabel nivelValor;
    private JLabel capacidadValor;

    public Configuracion(CampoBatalla campo) {
        this.campo = campo;
        this.refPantalla = campo.getRefPantalla();
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        // Crear los lbl de las estructuras arrastrables
        JLabel Mina = new JLabel("🧟");
        Mina.setBounds(15, 80, 50, 50);
        Mina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Mina.png")));
        Mina.setFont(new Font("Sans", Font.PLAIN, 40));
        addArrastrable(Mina);
        add(Mina);
        
        
        JLabel Canon = new JLabel("🧟");
        Canon.setBounds(65, 80, 50, 50);
        Canon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cañon.png")));
        Canon.setFont(new Font("Sans", Font.PLAIN, 40));
        addArrastrable(Canon);
        add(Canon);
        //new Cannon();
        
       
        JLabel TorreArquero = new JLabel("🧟");
        TorreArquero.setBounds(115, 80, 50, 50);
        TorreArquero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TorreArquero.png")));
        TorreArquero.setFont(new Font("Sans", Font.PLAIN, 40));
        addArrastrable(TorreArquero);
        add(TorreArquero);
        
    
        JLabel Sierra = new JLabel("🧟");
        Sierra.setBounds(15, 130, 50, 50);
        Sierra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SierraMovil.png")));
        Sierra.setFont(new Font("Sans", Font.PLAIN, 40));
        addArrastrable(Sierra);
        add(Sierra);
        
    
        JLabel Muro = new JLabel("🧟");
        Muro.setBounds(65, 130, 50, 50);
        Muro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Muro.png")));
        Muro.setFont(new Font("Sans", Font.PLAIN, 40));
        addArrastrable(Muro);
        add(Muro);
        
        JLabel TorreSangre = new JLabel("🧟");
        TorreSangre.setBounds(115, 130, 50, 50);
        TorreSangre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TorreSangre.png")));
        TorreSangre.setFont(new Font("Sans", Font.PLAIN, 40));
        addArrastrable(TorreSangre);
        add(TorreSangre);

        // Título Agregables
        JLabel lblAgregables = new JLabel("Agregables:");
        lblAgregables.setFont(new Font("Chiller", Font.PLAIN, 25));
        lblAgregables.setForeground(Color.WHITE);
        lblAgregables.setBounds(10, 20, 150, 45);
        add(lblAgregables);

        // Nivel
        JLabel lblNivel = new JLabel("Nivel:");
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setFont(new Font("Chiller", Font.PLAIN, 25));
        lblNivel.setBounds(10, 400, 80, 45);
        add(lblNivel);

        nivelValor = new JLabel("1"); // Valor inicial
        nivelValor.setForeground(Color.YELLOW);
        nivelValor.setFont(new Font("Chiller", Font.BOLD, 25));
        nivelValor.setBounds(90, 400, 50, 45);
        add(nivelValor);

        // Capacidad máxima
        JLabel lblCapacidad = new JLabel("Capacidad máxima:");
        lblCapacidad.setForeground(Color.WHITE);
        lblCapacidad.setFont(new Font("Chiller", Font.PLAIN, 20));
        lblCapacidad.setBounds(10, 450, 200, 45);
        add(lblCapacidad);

        capacidadValor = new JLabel("20"); // Valor inicial
        capacidadValor.setForeground(Color.RED);
        capacidadValor.setFont(new Font("Chiller", Font.BOLD, 25));
        capacidadValor.setBounds(200, 450, 50, 45);
        add(capacidadValor);
        
        //Boton para jugar
        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setBounds(50, 612, 150, 45);
        // Fuente y tamaño estilo retro/pixel
        btnJugar.setFont(new Font("Monospaced", Font.BOLD, 24));

        // Colores llamativos
        btnJugar.setBackground(Color.CYAN); 
        btnJugar.setForeground(Color.BLACK);

        // Quitar foco y resaltar el estilo
        btnJugar.setFocusPainted(false);
        btnJugar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        btnJugar.addActionListener(e -> campo.jugar(refPantalla.getNivel()));
        btnJugar.setRolloverEnabled(true);
        // Opcional: efecto hover
        btnJugar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnJugar.setBackground(Color.GRAY); // más claro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnJugar.setBackground(Color.CYAN);
            }
            
        });
        


        // Agregar al panel
        add(btnJugar);
    }

    private void addArrastrable(JLabel label) {
        final Point[] originalPos = {label.getLocation()}; // posición original

        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel = label;
                offset = e.getPoint();
                originalPos[0] = label.getLocation();
            }

            public void mouseReleased(MouseEvent e) {
                Point punto = SwingUtilities.convertPoint(label, e.getPoint(), campo);

                // crear una nueva tropa para colocar en el tablero
                JLabel nuevaTropa = new JLabel();
                nuevaTropa.setIcon(selectedLabel.getIcon());
                nuevaTropa.setSize(selectedLabel.getSize());
                nuevaTropa.setFont(selectedLabel.getFont());

 
                campo.colocarTropa(punto, nuevaTropa); 
                

                selectedLabel.setLocation(originalPos[0]);

                selectedLabel = null;
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedLabel != null) {
                    int x = selectedLabel.getX() + e.getX() - offset.x;
                    int y = selectedLabel.getY() + e.getY() - offset.y;
                    selectedLabel.setLocation(x, y);
                }
            }
        });
    }
    
  

    public void setNivel(int nivel) {
        nivelValor.setText(String.valueOf(nivel));
    }

    public void setCapacidad(int capacidad) {
        capacidadValor.setText(String.valueOf(capacidad));
    }
}
