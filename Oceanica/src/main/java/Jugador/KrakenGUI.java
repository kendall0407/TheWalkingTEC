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

public class KrakenGUI extends JFrame {

    public KrakenGUI() {
        setTitle("Kraken MoveSet");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fondo verde pastel
        getContentPane().setBackground(new Color(152, 251, 152));
        setLayout(new BorderLayout());

        // ----- TÍTULO -----
        JLabel lblTitulo = new JLabel("KRAKEN MOVESET", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false); // Transparente para mostrar fondo

        // Crear botones
        JButton btnTentacles = crearBoton("Kraken's Tentacles");
        JButton btnBreath = crearBoton("Kraken's Breath");
        JButton btnRelease = crearBoton("Release the Kraken");

        // Agregar al panel
        panelBotones.add(btnTentacles);
        panelBotones.add(btnBreath);
        panelBotones.add(btnRelease);

        // Agregar panel al centro
        add(panelBotones, BorderLayout.CENTER);
    }

    // Método para crear botones grises con hover oscuro
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setPreferredSize(new Dimension(200, 45));
        btn.setFocusPainted(false);

        Color normal = new Color(38, 115, 38);   
        Color hover = new Color(140, 140, 140);      

        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(normal);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KrakenGUI().setVisible(true));
    }
}