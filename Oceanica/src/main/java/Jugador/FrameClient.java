/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

/**
 *
 * @author kendall-sanabria
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FrameClient extends JFrame {
    private static final int GRID_ROWS = 20;
    private static final int GRID_COLS = 20;
    private static final int CELL_SIZE = 10;
    private static final int TOTAL_CELLS = GRID_ROWS * GRID_COLS;
    private int recorridoPintar = 0;
    
    private Celda[][] celdas;
    private JTextArea txaInfo;
    private JPanel rightPanel;
    private JPanel gridPanel;
    private JPanel bottomPanel;
    
    private JLabel luchador1;
    private JLabel luchador2;
    private JLabel luchador3;
    
    public FrameClient() {
        setTitle("Oceanica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(1500, 1000));
        
        // Panel central con la matriz de juego
        gridPanel = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, 1, 1));
        gridPanel.setBackground(Color.BLACK);
        gridPanel.setPreferredSize(new Dimension(GRID_COLS * CELL_SIZE, GRID_ROWS * CELL_SIZE));
        
        celdas = new Celda[GRID_ROWS][GRID_COLS];
        
        // Crear las celdas de la matriz
        for (int i = 0; i < GRID_ROWS; i++) {
            for (int j = 0; j < GRID_COLS; j++) {
                celdas[i][j] = new Celda(i, j);
                
                celdas[i][j].setBackground(Color.WHITE);
                celdas[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                
                
                gridPanel.add(celdas[i][j]);
            }
        }
        
        // Panel derecho
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setPreferredSize(new Dimension(400, 600));
        rightPanel.setBackground(new Color(240, 240, 240));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // panel de abajo con info
        bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setPreferredSize(new Dimension(100, 150));
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        // Área de texto inferior
        txaInfo = new JTextArea(100, 150);
        txaInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaInfo.setText("Bienvenido al juego!\n");
        txaInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
        
        JScrollPane scrollPane = new JScrollPane(txaInfo);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        
        JTextArea txaBitacora = new JTextArea(20, 20);
        txaBitacora.setEditable(false);
        txaBitacora.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaBitacora.setText("Bitacora\n");
        
        JTextArea txaResultado = new JTextArea(20, 20);
        txaResultado.setEditable(false);
        txaResultado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaResultado.setText("Ataque\n");
        
        
        JPanel panelWest = new JPanel(new GridLayout(2, 1, 0, 10)); // 2 filas, 1 columna, gap de 10px
        panelWest.add(txaBitacora);
        panelWest.add(txaResultado);
        
        // Panel contenedor para el grid y el bottomPanel
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(gridPanel, BorderLayout.CENTER);
        centerContainer.add(bottomPanel, BorderLayout.SOUTH);
        
        // Agregar componentes al frame
        add(centerContainer, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
        add(panelWest, BorderLayout.WEST);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public void colocarInfoCasillas(ArrayList<Luchador> luchadores) {
        int x = 20;
        // Label: Vida
        JLabel lblVida = new JLabel("Vida: "+ 100 + "%");
        lblVida.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
        lblVida.setBounds(x, 10, 200, 25);
        bottomPanel.add(lblVida);

        // Label: Casillas destruidas 
        JLabel lblCasillas = new JLabel("Casillas destruidas: 0");
        lblCasillas.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
        lblCasillas.setBounds(x+500, 10, 300, 25);
        bottomPanel.add(lblCasillas);
        
        for (int i = 0; i < luchadores.size(); i++) {
            Luchador luchador = luchadores.get(i);
            // Label: Personaje 
            JLabel lblPersonaje1 = new JLabel(luchador.getNombre());
            lblPersonaje1.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
            lblPersonaje1.setBounds(x, 50, 100, 30);
            bottomPanel.add(lblPersonaje1);


            JLabel lblRepresentacion1 = new JLabel(luchador.getRepresentacion() + "%");
            lblRepresentacion1.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
            lblRepresentacion1.setBounds(x+10, 90, 100, 25);
            bottomPanel.add(lblRepresentacion1);


            JLabel lblCasillas1 = new JLabel((luchador.getRepresentacion() *400/100)+" de " + TOTAL_CELLS); 
            lblCasillas1.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
            lblCasillas1.setBounds(x+10, 120, 100, 25);
            bottomPanel.add(lblCasillas1);
            x = x+300;
        }
    }
    
    public void actualizarInfoCasillas(int vida, int casillasDestruidas){
        //despues de cada turno se actualiza
    }
    
    public void colocarLuchador(Luchador luchador, String direccion, int x, int y, int representacion, int poder, int sanidad, int resistencia, String nombre, String habilidad) {
        JLabel imageLabel = new JLabel();

        ImageIcon originalIcon = new ImageIcon(direccion);
        Image img = originalIcon.getImage();
        Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        imageLabel.setIcon(scaledIcon);
        imageLabel.setBounds(x, y, 150, 150);
        
        JLabel lblRepresentacion = new JLabel(Integer.toString(representacion)+ "%");
        lblRepresentacion.setFont(new Font("Arial", Font.BOLD, 20));
        lblRepresentacion.setBounds(x+200, y, 150, 40);
        
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setBounds(x+200, y+30, 150, 40);
        
        JLabel lblHabilidad = new JLabel(habilidad);
        lblHabilidad.setFont(new Font("Arial", Font.BOLD, 16));
        lblHabilidad.setBounds(x+200, y+60, 150, 40);
        
        JLabel lblPoder = new JLabel("Poder: " + Integer.toString(poder) + "%");
        lblPoder.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPoder.setBounds(x+200, y+100, 150, 40);
        
        JLabel lblSanidad = new JLabel("Sanidad: " + Integer.toString(sanidad)+"%");
        lblSanidad.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSanidad.setBounds(x+200, y+120, 150, 40);
        
        JLabel lblResistencia = new JLabel("Resistencia: "+Integer.toString(resistencia)+"%");
        lblResistencia.setFont(new Font("Arial", Font.PLAIN, 16));
        lblResistencia.setBounds(x+200, y+140, 150, 40);
        
        rightPanel.add(imageLabel);
        rightPanel.revalidate();
        rightPanel.repaint(); 
        rightPanel.add(lblRepresentacion);
        rightPanel.add(lblPoder);
        rightPanel.add(lblSanidad);
        rightPanel.add(lblResistencia);
        rightPanel.add(lblNombre);
        rightPanel.add(lblHabilidad);
        
        //pintar en la matriz
        
        Color c = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        pintarRepresentacion(luchador, c, representacion);
        
    }
    
    public void pintarRepresentacion(Luchador luchador, Color color, int representacion) {
        //pinta las celdas
        int j = 0;
        int porcentaje = representacion * 400 / 100;  //obtiene la cantidad de celdas que va a pintar 
        for (int i = 0; i < porcentaje; i++) {
            celdas[j][recorridoPintar].setBackground(color);
            celdas[j][recorridoPintar].ocupar(luchador);
            j++;
            
            if (j == GRID_COLS){
                j = 0;
                recorridoPintar++;
            }
        }
   }
    
    public void writeMessage(String msg){
        txaInfo.append(msg + "\n");
    }
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrameClient game = new FrameClient();
            game.setVisible(true);
        });
    }
}
