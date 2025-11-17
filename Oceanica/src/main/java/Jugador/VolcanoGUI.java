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

public class VolcanoGUI extends JFrame {

    public VolcanoGUI() {
        setTitle("VolcanoMoveSet");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fondo café rojizo muy oscuro
        getContentPane().setBackground(new Color(60, 20, 10)); 
        setLayout(new BorderLayout());

        // ----- TÍTULO -----
        JLabel lblTitulo = new JLabel("Volcano MoveSet", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false);

        // Crear botones naranjas
        JButton btnRising = crearBoton("Volcano Rising");
        JButton btnExplosion = crearBoton("Volcano Explosion");
        JButton btnThermal = crearBoton("Thermal Rush");

        // Agregar botones
        panelBotones.add(btnRising);
        panelBotones.add(btnExplosion);
        panelBotones.add(btnThermal);

        add(panelBotones, BorderLayout.CENTER);
    }

    // Crear botón naranja con hover más oscuro
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setPreferredSize(new Dimension(220, 45));
        btn.setFocusPainted(false);

        Color normal = new Color(255, 140, 0);   // naranja
        Color hover = new Color(210, 110, 0);    // naranja oscuro

        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VolcanoGUI().setVisible(true));
    }
}
