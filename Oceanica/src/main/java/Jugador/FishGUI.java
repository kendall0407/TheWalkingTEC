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

public class FishGUI extends JFrame {
    private Client client;

    public FishGUI(Client clientC) {
        this.client = clientC;
        setTitle("FishTelepathyMoveSet");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fondo azul oscuro
        getContentPane().setBackground(new Color(10, 20, 60));
        setLayout(new BorderLayout());

        // ----- TÍTULO -----
        JLabel lblTitulo = new JLabel("Fish Telepathy MoveSet", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false);

        // Crear botones blancos
        JButton btnCardumen = crearBoton("Cardumen");
        JButton btnShark = crearBoton("SharkAttack");
        JButton btnPulp = crearBoton("Pulp");
        
        btnCardumen.addActionListener(e -> abrirOpcionFrame(this.client,"6"));
        btnShark.addActionListener(e -> abrirOpcionFrame(this.client,"7"));
        btnPulp.addActionListener(e -> abrirOpcionFrame(this.client,"8"));

        // Agregar al panel
        panelBotones.add(btnCardumen);
        panelBotones.add(btnShark);
        panelBotones.add(btnPulp);

        add(panelBotones, BorderLayout.CENTER);
    }

    // Crear botón blanco con hover gris
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setPreferredSize(new Dimension(220, 45));
        btn.setFocusPainted(false);

        Color normal = Color.WHITE;
        Color hover = new Color(220, 220, 220);

        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);

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