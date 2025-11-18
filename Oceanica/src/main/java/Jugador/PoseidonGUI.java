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

public class PoseidonGUI extends JFrame {
    private Client client;

    public PoseidonGUI(Client clientC) {
        this.client = clientC;
        setTitle("PoseidonMoveSet");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fondo azul oscuro
        getContentPane().setBackground(new Color(10, 20, 60));
        setLayout(new BorderLayout());

        // ----- TÍTULO -----
        JLabel lblTitulo = new JLabel("Poseidon's Trident MoveSet", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(255, 255, 180)); // amarillo pastel
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
        panelBotones.setOpaque(false);

        // Crear botones
        JButton btnNumbers = crearBoton("Three Numbers");
        JButton btnLines = crearBoton("Three Lines");
        JButton btnKrakenAlpha = crearBoton("Kraken's Alpha");

        btnNumbers.addActionListener(e -> abrirSorteoTriple(this.client));
        btnKrakenAlpha.addActionListener(e -> abrirOpcionFrame(this.client,"5"));
        btnLines.addActionListener(e -> abrirAtacarTripleFrame(this.client,"4"));
        
        // Agregar al panel
        panelBotones.add(btnNumbers);
        panelBotones.add(btnLines);
        panelBotones.add(btnKrakenAlpha);

        add(panelBotones, BorderLayout.CENTER);
    }

    // Crear botón estilo beige dorado con hover oscuro
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setPreferredSize(new Dimension(220, 45));
        btn.setFocusPainted(false);

        Color normal = new Color(240, 220, 170);    // beige dorado claro
        Color hover = new Color(200, 180, 130);     // beige dorado más oscuro

        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);

        // Hover effect
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
    
    public void abrirAtacarTripleFrame(Client client, String tipo){
        AtaqueTripleFrame atacar = new AtaqueTripleFrame(client, tipo);
        atacar.setVisible(true);
    }
    
    public void abrirSorteoTriple(Client client){
        SorteoTripleFrame sorteo = new SorteoTripleFrame(client);
        sorteo.setVisible(true);
    }
    
}
