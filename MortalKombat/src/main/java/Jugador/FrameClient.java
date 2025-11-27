/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author kendall-sanabria
 */
public class FrameClient extends JFrame {
    private JTextArea consola;
    private Client client;
    private JTextArea txaContrincante;
    private JTextArea txaRanking;
    private JTextArea txaStatus;
    private JTextArea txaReceivedAttacks;
    private JTextArea txaAttacksDone;
    
    
    public FrameClient(Client client) {
        this.client = client;
        
        setTitle("Mortal Kombat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // PANEL IZQUIERDO - 3 paneles verticales
        JPanel leftPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        leftPanel.setPreferredSize(new Dimension(240, 0));
        leftPanel.setBackground(Color.BLACK);

        JPanel pnlRanking = crearPanel("RANKING", Color.GREEN.darker());
        txaRanking = new JTextArea();
        txaRanking.setLineWrap(true);       // Ajusta líneas automáticamente
        txaRanking.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaRanking.setEditable(false);
        txaRanking.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnlRanking.add(txaRanking);
        
        
        JPanel pnlContrincante = crearPanel("CONTRINCANTE: ", Color.GREEN.darker());
        txaContrincante = new JTextArea();
        txaContrincante.setLineWrap(true);       // Ajusta líneas automáticamente
        txaContrincante.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaContrincante.setEditable(false);
        txaContrincante.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnlContrincante.add(txaContrincante);
        
        JPanel pnlStatus = crearPanel("ESTADISTICAS", Color.GREEN.darker());
        txaStatus = new JTextArea();
        txaStatus.setLineWrap(true);       // Ajusta líneas automáticamente
        txaStatus.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaStatus.setEditable(false);
        txaStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnlStatus.add(txaStatus);
        
        leftPanel.add(pnlRanking);
        leftPanel.add(pnlContrincante);
        leftPanel.add(pnlStatus);

        // PANEL DERECHO - Team
        JPanel rightPanel = crearPanel("TU EQUIPO", Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(500, 0));

        // PANELES CENTRALES - 2 paneles verticales
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBackground(Color.BLACK);

        JPanel pnlReceivedAttacks = crearPanel("No has recibido ataques (aun)", Color.CYAN);
        txaReceivedAttacks = new JTextArea();
        txaReceivedAttacks.setLineWrap(true);       // Ajusta líneas automáticamente
        txaReceivedAttacks.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaReceivedAttacks.setEditable(false);
        txaReceivedAttacks.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnlReceivedAttacks.add(txaReceivedAttacks);
        
        JPanel pnlAttacksDone = crearPanel("Aqui se mostraran tus ataques", Color.RED.darker());
        txaAttacksDone = new JTextArea();
        txaAttacksDone.setLineWrap(true);       // Ajusta líneas automáticamente
        txaAttacksDone.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaAttacksDone.setEditable(false);
        txaAttacksDone.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnlAttacksDone.add(txaAttacksDone);
        

        centerPanel.add(pnlReceivedAttacks);
        centerPanel.add(pnlAttacksDone);

        // PANEL INFERIOR - Consola
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setPreferredSize(new Dimension(0, 150));
        consolePanel.setBackground(Color.BLACK);
        consolePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN.darker(), 2));

        consola = new JTextArea();
        consola.setBackground(Color.BLACK);
        consola.setForeground(Color.GREEN);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 14));
        consola.setText("\t\t\t\t\t\t\t\t\t   ┏━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                        "\t\t\t\t\t\t\t\t\t   ┃      ¡BIENVENIDO!       ┃\n" +
                        "\t\t\t\t\t\t\t\t\t   ┗━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                        "Instrucciones de comandos" +
                        "\n1. 'Ataque-arma-enemigo(ID)'" +
                        "\n2. 'Consultar-luchador'" +
                        "\n3. 'Recargar'" +
                        "\n4. 'Comodin'" +
                        "\n5. 'Pass'" +
                        "\n6. 'Rendirse'" +
                        "\n7. 'Draw'" +
                        "\n8. 'all-msg'" +
                        "\n9. 'dm-msg-jugador(ID)'\n > ");

        
        JScrollPane scrollPane = new JScrollPane(consola);
        scrollPane.setBorder(null);
        consolePanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar todos los paneles al panel principal
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(consolePanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
    

    
    private JPanel crearPanel(String title, Color borderColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        
        return panel;
    }

    public JTextArea getTxaContrincante() {
        return txaContrincante;
    }

    public JTextArea getTxaRanking() {
        return txaRanking;
    }

    public JTextArea getTxaStatus() {
        return txaStatus;
    }

    public JTextArea getTxaReceivedAttacks() {
        return txaReceivedAttacks;
    }

    public JTextArea getTxaAttacksDone() {
        return txaAttacksDone;
    }
    
    public JTextArea getConsola() {
        return consola;
    }

    public Client getClient() {
        return client;
    }
    

}
