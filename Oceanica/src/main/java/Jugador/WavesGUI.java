/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

/**
 *
 * @author josed
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WavesGUI extends JFrame {

    public WavesGUI() {
        setTitle("WavesMoveSet");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fondo azul pastel
        getContentPane().setBackground(new Color(150, 200, 255));
        setLayout(new BorderLayout());

        // ----- TÍTULO -----
        JLabel lblTitulo = new JLabel("Waves MoveSet", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false);

        // Crear botones azul oscuro con letras blancas
        JButton btnSwirl = crearBoton("Swirl Raising");
        JButton btnGarbage = crearBoton("Human Garbage");
        JButton btnRadioactive = crearBoton("Radioactive Rush");

        panelBotones.add(btnSwirl);
        panelBotones.add(btnGarbage);
        panelBotones.add(btnRadioactive);

        add(panelBotones, BorderLayout.CENTER);
    }

    // Botones azul oscuro con hover más claro
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setPreferredSize(new Dimension(220, 45));
        btn.setFocusPainted(false);

        Color normal = new Color(20, 40, 120);    // azul oscuro
        Color hover = new Color(40, 70, 150);     // azul oscuro aclarado

        btn.setBackground(normal);
        btn.setForeground(Color.WHITE);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WavesGUI().setVisible(true));
    }
}

