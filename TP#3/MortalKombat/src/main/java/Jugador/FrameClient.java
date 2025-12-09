/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import java.awt.*;
import java.util.HashMap;
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
    private JPanel teamRow;
    private JPanel statsPanel;
    private JPanel rightPanel;
    
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
        txaRanking.setBackground(Color.GRAY);
        txaRanking.setLineWrap(true);       // Ajusta líneas automáticamente
        txaRanking.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaRanking.setEditable(false);
        txaRanking.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Crear JScrollPane para el ranking
        JScrollPane scrollRanking = new JScrollPane(txaRanking);
        scrollRanking.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollRanking.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollRanking.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Configurar tamaño preferido para el scroll
        scrollRanking.setPreferredSize(new Dimension(200, 150));

        pnlRanking.add(scrollRanking);


        JPanel pnlContrincante = crearPanel("CONTRINCANTE: ", Color.GREEN.darker());
        txaContrincante = new JTextArea();
        txaContrincante.setBackground(Color.GRAY);
        txaContrincante.setLineWrap(true);       // Ajusta líneas automáticamente
        txaContrincante.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaContrincante.setEditable(false);
        txaContrincante.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Crear JScrollPane para el contrincante
        JScrollPane scrollContrincante = new JScrollPane(txaContrincante);
        scrollContrincante.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollContrincante.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContrincante.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollContrincante.setPreferredSize(new Dimension(200, 150));

        pnlContrincante.add(scrollContrincante);


        JPanel pnlStatus = crearPanel("ESTADISTICAS", Color.GREEN.darker());
        txaStatus = new JTextArea();
        txaStatus.setBackground(Color.GRAY);
        txaStatus.setLineWrap(true);       // Ajusta líneas automáticamente
        txaStatus.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaStatus.setEditable(false);
        txaStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Crear JScrollPane para estadísticas
        JScrollPane scrollStatus = new JScrollPane(txaStatus);
        scrollStatus.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollStatus.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollStatus.setPreferredSize(new Dimension(200, 150));

        pnlStatus.add(scrollStatus);
        
        leftPanel.add(pnlRanking);
        leftPanel.add(pnlContrincante);
        leftPanel.add(pnlStatus);

        // PANEL DERECHO - Team
        JPanel rightPanel = crearPanelDerecho();
        // Título
        JLabel titulo = new JLabel("TU EQUIPO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        rightPanel.add(titulo, BorderLayout.NORTH);
        
        teamRow = new JPanel(new GridLayout(1, 4, 10, 10));
        teamRow.setBackground(Color.GRAY);

        rightPanel.add(teamRow, BorderLayout.CENTER);
        
                
        // PANELES CENTRALES - 2 paneles verticales
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBackground(Color.BLACK);

        JPanel pnlReceivedAttacks = crearPanel("Ataques recibidos", Color.BLUE);
        txaReceivedAttacks = new JTextArea();
        txaReceivedAttacks.setBackground(Color.GRAY);
        txaReceivedAttacks.setLineWrap(true);       // Ajusta líneas automáticamente
        txaReceivedAttacks.setWrapStyleWord(true);  // Evita cortar palabras a la mitad
        txaReceivedAttacks.setEditable(false);
        txaReceivedAttacks.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pnlReceivedAttacks.add(txaReceivedAttacks);
        
        JPanel pnlAttacksDone = crearPanel("Aqui se mostraran tus ataques", Color.RED.darker());
        txaAttacksDone = new JTextArea();
        txaAttacksDone.setBackground(Color.GRAY);
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
        consola.setText("\t\t\t\t\t\t\t\t   ┏━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                        "\t\t\t\t\t\t\t\t   ┃      ¡BIENVENIDO!       ┃\n" +
                        "\t\t\t\t\t\t\t\t   ┗━━━━━━━━━━━━━━━━━━━━━━━━━┛\n" +
                        "Por favor, introduzca su usario 'login-usuario-contrasena'" +
                        "o si no tiene cuenta 'register-usuario-contrasena'\n > ");

        
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
    
    private JPanel crearPanelDerecho() {

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.BLACK);

        // === 1) Crear fila de luchadores (arriba) ===
        teamRow = new JPanel();
        teamRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        teamRow.setBackground(Color.BLACK);

        rightPanel.add(teamRow, BorderLayout.NORTH);

        // === 2) Stats Panel inicial (abajo) ===
        statsPanel = new JPanel();  
        statsPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(statsPanel, BorderLayout.CENTER);

        return rightPanel;
    }

    public void crearLuchadorEquipo(String nombre, String porcentaje, String poder) {
        if (teamRow == null) 
            return;

        teamRow.add(crearTarjetaLuchador(nombre, porcentaje, poder));
        teamRow.revalidate();
        teamRow.repaint();
    }

    
    
    public void crearStats(String nombre, String porcentaje, HashMap<String, int[]> armas) {

        // Si ya existe statsPanel, lo quitamos primero
        if (statsPanel != null) {
            rightPanel.remove(statsPanel);
        }

        // Crear nuevo panel
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.GRAY);

        JLabel title = new JLabel(nombre + "   " + porcentaje + "%");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        statsPanel.add(title);

        // Recorrer HashMap: clave = arma, valor = int[]
        for (String arma : armas.keySet()) {
            statsPanel.add(crearFilaStat(arma, armas.get(arma)));
        }

        // Agregar el panel actualizado
        rightPanel.add(statsPanel, BorderLayout.SOUTH);

        // Refrescar
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public JPanel crearTarjetaLuchador(String nombre, String porcentaje, String poder) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);

        JLabel img = new JLabel();
        img.setPreferredSize(new Dimension(110, 180));
        img.setOpaque(true);
        img.setBackground(Color.LIGHT_GRAY);

        JLabel lblPorcentaje = new JLabel(porcentaje, SwingConstants.CENTER);
        lblPorcentaje.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblPoder = new JLabel(poder.toUpperCase(), SwingConstants.CENTER);
        lblPoder.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPoder.setForeground(Color.RED);

        p.add(img);
        p.add(lblPorcentaje);
        p.add(lblNombre);
        p.add(lblPoder);

        // Guardar referencias por si quieres actualizar luego
        p.putClientProperty("lblPorcentaje", lblPorcentaje);
        p.putClientProperty("lblNombre", lblNombre);
        p.putClientProperty("lblPoder", lblPoder);

        return p;
    }
    private JPanel crearFilaStat(String arma, int[] valores) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(Color.GRAY);

        JLabel nombre = new JLabel(arma + "   ");
        nombre.setPreferredSize(new Dimension(80, 20));
        row.add(nombre);

        for (int v : valores) {
            JLabel celda = new JLabel(String.valueOf(v));
            celda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            celda.setPreferredSize(new Dimension(30, 20));
            celda.setHorizontalAlignment(SwingConstants.CENTER);
            row.add(celda);
        }

        return row;
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

    public JPanel getTeamRow() {
        return teamRow;
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }
    


}
