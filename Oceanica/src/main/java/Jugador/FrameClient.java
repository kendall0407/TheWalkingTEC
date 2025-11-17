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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrameClient extends JFrame {
    private static final int GRID_ROWS = 20;
    private static final int GRID_COLS = 20;
    private static final int CELL_SIZE = 10;
    private static final int TOTAL_CELLS = GRID_ROWS * GRID_COLS;
    private int recorridoPintar = 0;
    private Client client;
    
    private Celda[][] celdas;
    private JTextArea txaInfo;
    private JPanel rightPanel;
    private JPanel gridPanel;
    private JPanel bottomPanel;
    private JTextArea txaBitacora;
    private JTextArea txaResultado;
    private JTextArea txaInstrucciones;
    private JLabel luchador1;
    private JLabel luchador2;
    private JLabel luchador3;
    
    public FrameClient(Client client) {
        this.client = client;
        setTitle(client.getCivilizacion().getNombreCivilizacion());
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
                celdas[i][j] = new Celda(i, j, this);
                
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
        
        
        // Área de texto inferior, este envia los ataques
        txaInfo = new JTextArea(100, 150);
        txaInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaInfo.setText("Aqui escribiras los numeros");
        //para que se borre despues
        new javax.swing.Timer(5000, e -> {
            txaInfo.setText(""); 
            }) {{
                setRepeats(false); 
                start();
        }};
        txaInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(txaInfo);
        scrollPane.setPreferredSize(new Dimension(1000, 140));
        
        JButton btnSend = new JButton();
        btnSend.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnSend.setFont(new java.awt.Font("Ubuntu Sans Mono", 0, 14));
        btnSend.setText("Enviar");
        btnSend.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                String txt = txaInfo.getText();
                client.procesarEntradaUsuario(txt);
            }
        });
        btnSend.setPreferredSize(new Dimension(50, 50));
        
        txaInstrucciones = new JTextArea(5,5);
        txaInstrucciones.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaInstrucciones.setText("\"Bienvenido al juego! Instrucciones:\n1. Atacar\n2. Consultar estado enemigo\n");
        txaInstrucciones.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
        txaInstrucciones.setPreferredSize(new Dimension(420, 140));
        //-----------Bitacora y Resultado solo reciben strings, no envian nada---------------
        txaBitacora = new JTextArea(20, 20);
        txaBitacora.setEditable(false);
        txaBitacora.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaBitacora.setText("Bitacora\n");
        txaBitacora.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
        txaBitacora.setLineWrap(true);
        txaBitacora.setWrapStyleWord(true);
        JScrollPane scrollPaneBitacora = new JScrollPane(txaBitacora);
        scrollPaneBitacora.setPreferredSize(new Dimension(250, 100));
        
        txaResultado = new JTextArea(20, 20);
        txaResultado.setEditable(false);
        txaResultado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaResultado.setText("Esperando a atacar (o ser atacado)...\nADVERTENCIA: Si atacas cuando no es tu turno lo perderas\n!");
        txaResultado.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
        txaResultado.setLineWrap(true);
        txaResultado.setWrapStyleWord(true);
        new javax.swing.Timer(5000, e -> {
            txaResultado.setText(""); 
            }) {{
                setRepeats(false); 
                start();
        }};
        JScrollPane scrollPaneResultado = new JScrollPane(txaResultado);
        scrollPaneResultado.setPreferredSize(new Dimension(250, 120));
        
        //Panel contenedor para bitacora y resultado
        JPanel panelWest = new JPanel(new GridLayout(2, 1, 0, 10)); // 2 filas, 1 columna, gap de 10px
        panelWest.add(scrollPaneBitacora);
        panelWest.add(scrollPaneResultado);
        
        //Panel contenedor para instrucciones y area de consola
        JPanel panelSur = new JPanel(new FlowLayout()); // 1 filas, 2 columna, gap de 10px
        panelSur.add(txaInstrucciones);
        panelSur.add(scrollPane);
        panelSur.add(btnSend);
                
        // Panel contenedor para el grid y el bottomPanel
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(gridPanel, BorderLayout.CENTER);
        centerContainer.add(bottomPanel, BorderLayout.SOUTH);
        
        // Agregar componentes al frame
        add(centerContainer, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(panelSur, BorderLayout.SOUTH);
        add(panelWest, BorderLayout.WEST);
        
        
        
        pack();
        setLocationRelativeTo(null);
        
    }
    
    public void colocarInfoCasillas(ArrayList<Luchador> luchadores) {
        int x = 20;
        int y = 10;
        int z = 20;
        // Label: Vida
        JLabel lblVida = new JLabel("Vida: "+ 100 + "%");
        lblVida.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
        lblVida.setBounds(x, y, 200, 25);
        bottomPanel.add(lblVida);

        // Label: Casillas destruidas 
        JLabel lblCasillas = new JLabel("Casillas destruidas: 0");
        lblCasillas.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
        lblCasillas.setBounds(x+600, y, 300, 25);
        bottomPanel.add(lblCasillas);
        
        for (int i = 0; i < luchadores.size(); i++) {
            Luchador luchador = luchadores.get(i);
            // Label: Personaje 
            JLabel lblPersonaje1 = new JLabel(luchador.getNombre());
            lblPersonaje1.setFont(new Font("Showcard Gothic", Font.BOLD, 18));
            lblPersonaje1.setBounds(x, 40, 100, 30);
            bottomPanel.add(lblPersonaje1);


            JLabel lblRepresentacion1 = new JLabel(luchador.getRepresentacion() + "%");
            lblRepresentacion1.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
            lblRepresentacion1.setBounds(x+10, 75, 100, 25);
            bottomPanel.add(lblRepresentacion1);


            JLabel lblCasillas1 = new JLabel((luchador.getRepresentacion() *400/100)+" de " + TOTAL_CELLS + " casillas"); 
            lblCasillas1.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
            lblCasillas1.setBounds(x-10, 120, 150, 25);
            bottomPanel.add(lblCasillas1);
            

            JLabel lblColores = new JLabel(luchador.getNombre());
            lblColores.setFont(new Font("Showcard Gothic", Font.BOLD, 12));
            lblColores.setBounds(z+720, y+40, 80, 25);
            lblColores.setBackground(luchador.getColor());
            lblColores.setOpaque(true);
            bottomPanel.add(lblColores);
            x +=300;
            y +=20;
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
        lblHabilidad.setBounds(x+200, y+60, 170, 40);
        
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
        luchador.setColor(c);
        pintarRepresentacion(luchador, c, representacion);
        
    }
    
    public void pintarRepresentacion(Luchador luchador, Color color, int representacion) {
        //pinta las celdas
        int j = 0;
        int porcentaje = representacion * 400 / 100;  //obtiene la cantidad de celdas que va a pintar 
        for (int i = 0; i < porcentaje; i++) {
            celdas[j][recorridoPintar].setBackground(color);
            celdas[j][recorridoPintar].ocupar(luchador);
            luchador.addCeldas(celdas[j][recorridoPintar]);
            j++;
            
            if (j == GRID_COLS){
                j = 0;
                recorridoPintar++;
            }
            
        }
        
   }
    
    public void agregarBitacora(String msg){ 
        txaBitacora.append(msg+"\n");
    }
    
    public void agregarEstado(String msg) {
        txaResultado.append(msg+"\n");
    }
    
    public void writeMessage(String msg){
        txaInfo.append(msg + "\n");
    }
    
    public void agregarInstrucciones(String msg) {
        txaInstrucciones.append(msg + "\n");
    }
    public Client getClient() {
        return client;
    }
    
    
    
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrameClient game = new FrameClient(null); //solo para probar como se va viendo
            game.setVisible(true);
        });
        
    }
}
