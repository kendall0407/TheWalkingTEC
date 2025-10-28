/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.walkingtec;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author josed
 */
public class CampoBatalla extends javax.swing.JPanel {
    private int nivel = 1; 
    private static final int filas = 25;
    private final int columnas = 25;
    private Celda[][] celdas = new Celda[filas][columnas];
    private Reliquia reliquia;
    private ArrayList<Zombie> infectados = new ArrayList<Zombie>();
    private PantallaJuego refPantalla;
    
    private Configuracion configPanel;
    
    private JPanel tablero;
    public CampoBatalla(PantallaJuego refPantalla) {
        this.refPantalla = refPantalla;

       // Inicializar subpanel Configuración
        configPanel = new Configuracion(this); // ahora recibe CampoBatalla
        configPanel.setPreferredSize(new Dimension(250, 800)); // ancho fijo
        add(configPanel, BorderLayout.EAST);

        // Panel central: tablero de celdas
        tablero = new JPanel(new GridLayout(filas, columnas));
        tablero.setPreferredSize(new Dimension(800, 800));
        inicializarCeldas(tablero);
        add(tablero, BorderLayout.CENTER);

        this.reliquia = colocarReliquia();
    }

    public PantallaJuego getRefPantalla() {
        return refPantalla;
    }
    
    
    public Reliquia getReliquia() {
        return reliquia;
    }
    
    public void moverZombie(JLabel lblMover, int x, int y, int xAntiguo, int yAntiguo) {

        celdas[xAntiguo][yAntiguo].removeAll();
        celdas[xAntiguo][yAntiguo].revalidate();
        celdas[xAntiguo][yAntiguo].repaint();
        celdas[x][y].add(lblMover);
        celdas[x][y].revalidate();
        celdas[x][y].repaint();
    }
    
    private void inicializarCeldas(JPanel tablero){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
                tablero.add(celdas[i][j]);
            }
        }
    }

    //metodo para colocar tropas desde el pnl de configuracion
    public boolean colocarTropa(Point p, JLabel lblPoner) {

        Point tableroP = SwingUtilities.convertPoint(configPanel, p, tablero);
        int celdaX = tableroP.x / 32;
        int celdaY = tableroP.y / 32;

        if (celdaX >= 0 && celdaY >= 0 && celdaX < columnas && celdaY < filas) {
            if (celdas[celdaX][celdaY].esConstruible() && !celdas[celdaX][celdaY].estaOcupada()) {
                crearTropaEnCelda(celdaX, celdaY, lblPoner);
                repaint();
                return true;
            } else {
                System.out.println("No se puede construir ahi!!");
                return false;
            }
            
        } else {
            System.out.println("Error, fuera del campo");
            return false;
        }
    }

    private void crearTropaEnCelda(int y, int x, JLabel lblPoner) {
        celdas[x][y].agregarEntidad(lblPoner);
        celdas[x][y].ocupar();
    }

    private Reliquia colocarReliquia(){
        int centroFila = filas/2;
        int centroColumna = columnas/2;
        
  
        ImageIcon icono = new ImageIcon(getClass().getResource("/images/Reliquia.png"));
        Image imagen = icono.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        JLabel lblReliquia = new JLabel(new ImageIcon(imagen)); 
        Reliquia reliquia = new Reliquia(lblReliquia, this);
        reliquia.setX(centroFila);
        reliquia.setY(centroColumna);
        
        
        celdas[centroFila][centroColumna].setLayout(new BorderLayout());
        celdas[centroFila][centroColumna].setImgEstructura(lblReliquia);
        
        //colorear alrededor de la reliquia
        for (int i = centroFila - 1; i <= centroFila + 1; i++) {
            for (int j = centroColumna - 1; j <= centroColumna + 1; j++) {
                if (i == centroFila && j == centroColumna) continue; // salta la celda central
                celdas[i][j].setColorEstructura();
            }
        }
        return reliquia;
    }
    
    public void generarZombiesTerrestres(int nivel) {
        // rangos válidos: 0-4 (5 celdas) y 21-24 (4 celdas), total 9 celdas
        // esto hace que se generen en los limites del juego y no sobrepasen de ahi
        int rango = 9;
        int aparicion = (nivel * 4) + 10;   // patron aleatorio de generar segun el nivel
        Random rand = new Random(); //en posiciones aleatorias
        
        for (int i = 0; i < aparicion; i++) {
            // elegimos un índice uniforme entre las 9 celdas válidas
            int idxFila = rand.nextInt(rango);
            int idxColumna = rand.nextInt(rango);
            int fila = mapIndexToValid(idxFila);
            int columna = mapIndexToValid(idxColumna);
            
            if (celdas[fila][columna].estaOcupada()) { 
                i--; 
                continue; 
            }

            ImageIcon icono = new ImageIcon(getClass().getResource("/images/Zombies.png"));
            Image imagen = icono.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
            JLabel lblZombie = new JLabel(new ImageIcon(imagen));

            celdas[fila][columna].agregarEntidad(lblZombie);
            celdas[fila][columna].ocupar();

            infectados.add(new ZombieTerrestre(lblZombie, refPantalla, 50, 4, 2000, fila, columna, "Terrestre", "/images/Zombies.png"));
        }
    }
    
    public void generarZombiesVolador(int nivel) {
        // rangos válidos: 0-4 (5 celdas) y 21-24 (4 celdas), total 9 celdas
        // esto hace que se generen en los limites del juego y no sobrepasen de ahi
        int rango = 9;
        int aparicion = nivel *2;   // patron de generar segun el nivel
        Random rand = new Random(); //en posiciones aleatorias
        
        for (int i = 0; i < aparicion; i++) {
            // elegimos un índice uniforme entre las 9 celdas válidas
            int idxFila = rand.nextInt(rango);
            int idxColumna = rand.nextInt(rango);
            int fila = mapIndexToValid(idxFila);
            int columna = mapIndexToValid(idxColumna);
            
            if (celdas[fila][columna].estaOcupada()) { 
                i--; 
                continue; 
            }

            ImageIcon icono = new ImageIcon(getClass().getResource("/images/ZombieVolador.png"));
            Image imagen = icono.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
            JLabel lblZombie = new JLabel(new ImageIcon(imagen));

            celdas[fila][columna].agregarEntidad(lblZombie);
            celdas[fila][columna].ocupar();

            infectados.add(new ZombieVolador(lblZombie, refPantalla, 30, 10, 2000, fila, columna, "Volador", "/images/ZombieVolador.png"));
        }
    }
     public void generarZombiesAlcance(int nivel) {
        // rangos válidos: 0-4 (5 celdas) y 21-24 (4 celdas), total 9 celdas
        // esto hace que se generen en los limites del juego y no sobrepasen de ahi
        int rango = 9;

        int aparicion = nivel + 1;  //
        Random rand = new Random(); //en posiciones aleatorias
        for (int i = 0; i < aparicion; i++) {
            // elegimos un índice uniforme entre las 9 celdas válidas
            int idxFila = rand.nextInt(rango);
            int idxColumna = rand.nextInt(rango);
            int fila = mapIndexToValid(idxFila);
            int columna = mapIndexToValid(idxColumna);

            if (celdas[fila][columna].estaOcupada()) { 
                i--; 
                continue; 
            }

            ImageIcon icono = new ImageIcon(getClass().getResource("/images/ZombieAlcance.png"));
            Image imagen = icono.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
            JLabel lblZombie = new JLabel(new ImageIcon(imagen));

            celdas[fila][columna].agregarEntidad(lblZombie);
            celdas[fila][columna].ocupar();

            infectados.add(new ZombieAlcance(lblZombie, refPantalla, 30, 12, 2000, fila, columna, "Tanque", "/images/ZombieAlcance.png"));
        }
   
    }
     
    public void generarZombiesTanque(int nivel) {
        // rangos válidos: 0-4 (5 celdas) y 21-24 (4 celdas), total 9 celdas
        // esto hace que se generen en los limites del juego y no sobrepasen de ahi
        int rango = 9;

        int aparicion = nivel-2;  //
        Random rand = new Random(); //en posiciones aleatorias
        for (int i = 0; i < aparicion; i++) {
            // elegimos un índice uniforme entre las 9 celdas válidas
            int idxFila = rand.nextInt(rango);
            int idxColumna = rand.nextInt(rango);
            int fila = mapIndexToValid(idxFila);
            int columna = mapIndexToValid(idxColumna);

            if (celdas[fila][columna].estaOcupada()) { 
                i--; 
                continue; 
            }

            ImageIcon icono = new ImageIcon(getClass().getResource("/images/ZombieTanque.png"));
            Image imagen = icono.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
            JLabel lblZombie = new JLabel(new ImageIcon(imagen));

            celdas[fila][columna].agregarEntidad(lblZombie);
            celdas[fila][columna].ocupar();

            infectados.add(new ZombieTanque(lblZombie, refPantalla, 200, 8, 2000, fila, columna, "Tanque", "/images/ZombieTanque.png"));
            }

        
    }

    // mapea un índice 0..8 a los índices válidos {0,1,2,3,4,21,22,23,24}
    private int mapIndexToValid(int idx) {
        if (idx < 5) return idx;       // 0..4
        else return 21 + (idx - 5);    // 21..24
    }
    
    public Celda getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }
    
    public void aumentarEstadisticas() {
        //se recorren los zombies
        for (Zombie zombie : infectados) {
            int nuevoDano = (int)(zombie.getDano() * 1.10);
            zombie.setDano(nuevoDano);
            int nuevaVida = (int)(zombie.getVida() * 1.10);
            zombie.setVida(nuevaVida);
        }
        
    }
    
    public int jugar(int nivel){
        //se agregan los zombies que se deseen
        System.out.println(nivel + "\n");
        switch (nivel){
            case 1:
                generarZombiesTerrestres(nivel);
                generarZombiesVolador(nivel);
                break;
                
            case 2:
                generarZombiesTerrestres(nivel);
                generarZombiesVolador(nivel);
                generarZombiesAlcance(nivel);
                break;
                
            case 3:
                generarZombiesTerrestres(nivel);
                generarZombiesVolador(nivel);
                generarZombiesAlcance(nivel);
                generarZombiesTanque(nivel);
                break;
                
            default:
                generarZombiesTerrestres(nivel);
                generarZombiesVolador(nivel);
                generarZombiesAlcance(nivel);
                generarZombiesTanque(nivel);
                break;
        }
        
        for (int i = 0; i < infectados.size(); i++) {
            infectados.get(i).start();
        }

        //maten o mueran
        refPantalla.avanzarNivel();
        aumentarEstadisticas();
        return 1; //si gano, 2 si perdio pero falta agregarle esas condiciones 
    }
    
    public void detenerJuego() {
        for (int i = 0; i < infectados.size(); i++) {
            infectados.get(i).setStop();
        }
        
        //falta parar estructuras 
        JOptionPane.showMessageDialog(
            null,
            "Perdiste, los zombies destruyeron la reliquia de la vida",
            "Game Over",
            JOptionPane.ERROR_MESSAGE
        );

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(800, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1026, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 972, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
