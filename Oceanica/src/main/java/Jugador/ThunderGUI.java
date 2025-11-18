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

public class ThunderGUI extends JFrame {
    private Client client;

    public ThunderGUI(Client clientC) {
        this.client = clientC;
        setTitle("ThunderMoveSet");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fondo morado pastel
        getContentPane().setBackground(new Color(200, 160, 220));
        setLayout(new BorderLayout());

        // ----- TÍTULO -----
        JLabel lblTitulo = new JLabel("Thunder MoveSet", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false);

        // Crear botones morados con texto azul pastel
        JButton btnRain = crearBoton("Thunder Rain");
        JButton btnPoseidon = crearBoton("Poseidon's Thunder");
        JButton btnEel = crearBoton("Eel Attack");
        
        btnRain.addActionListener(e -> abrirOpcionFrame(this.client,"12"));
        btnPoseidon.addActionListener(e -> abrirOpcionFrame(this.client,"13"));
        btnEel.addActionListener(e -> abrirOpcionFrame(this.client,"14"));
        

        // Agregar botones al panel
        panelBotones.add(btnRain);
        panelBotones.add(btnPoseidon);
        panelBotones.add(btnEel);

        add(panelBotones, BorderLayout.CENTER);
    }

    // Crear botón morado con hover y texto azul pastel
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setPreferredSize(new Dimension(220, 45));
        btn.setFocusPainted(false);

        Color normal = new Color(160, 90, 200);   // morado
        Color hover = new Color(130, 60, 170);    // morado más oscuro
        Color textoAzulPastel = new Color(150, 180, 255);

        btn.setBackground(normal);
        btn.setForeground(textoAzulPastel);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });

        return btn;
    }

    public void abrirOpcionFrame(Client client, String tipo) {
        ObjetivoFrame atacar = new ObjetivoFrame(client,tipo);
        atacar.setVisible(true);
    }
}
