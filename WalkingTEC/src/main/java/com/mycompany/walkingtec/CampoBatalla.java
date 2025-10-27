/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.walkingtec;

import java.awt.*;
import java.util.ArrayList;
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

    private void inicializarCeldas(JPanel tablero){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
                tablero.add(celdas[i][j]);
            }
        }
    }

    // Método para colocar tropas desde Configuración
    public void colocarTropa(Point p, JLabel lblPoner) {
        // Convertimos punto global a panel central (tablero)
        Point tableroP = SwingUtilities.convertPoint(configPanel, p, tablero);
        System.out.println(tableroP.x + "\n");
        System.out.println(tableroP.y + "\n");
        int celdaX = tableroP.x / 32;
        int celdaY = tableroP.y / 32;

        if (celdaX >= 0 && celdaY >= 0 && celdaX < columnas && celdaY < filas) {
            crearTropaEnCelda(celdaX, celdaY, lblPoner);
            repaint();
        } else {
            //TODO: Falta como manejar el error de que lo ponga afuera entonces que lo borre
            System.out.println("Error, fuera del campo");
        }
    }

    private void crearTropaEnCelda(int y, int x, JLabel lblPoner) {
        System.out.println("Tropa agregada en: " + x + ", " + y);
        celdas[x][y].agregarEntidad(lblPoner);
        // Aquí tu lógica de matriz o JLabel con imagen
    }

    private Reliquia colocarReliquia(){
        int centroFila = filas/2;
        int centroColumna = columnas/2;
        
  
        ImageIcon icono = new ImageIcon(getClass().getResource("/images/Reliquia.png"));
        Image imagen = icono.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        JLabel lblReliquia = new JLabel(new ImageIcon(imagen)); 
        Reliquia reliquia = new Reliquia(lblReliquia);
        
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

            infectados.add(new ZombieTerrestre(lblZombie, refPantalla, 50, 4, 5, fila, columna, "Terrestre", "/images/Zombies.png"));
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

            infectados.add(new ZombieVolador(lblZombie, refPantalla, 30, 10, 10, fila, columna, "Volador", "/images/ZombieVolador.png"));
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

            infectados.add(new ZombieAlcance(lblZombie, refPantalla, 30, 12, 4, fila, columna, "Tanque", "/images/ZombieAlcance.png"));
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

            infectados.add(new ZombieTanque(lblZombie, refPantalla, 200, 8, 2, fila, columna, "Tanque", "/images/ZombieTanque.png"));
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
        //la idea de este metodo es que desde la clase que llama a jugar, termina el nive,
        //y se aumenten estadisticas si es que se gano, tanto de defensas como de zombie
        //con sets de cada clase
    }
    
    public int jugar(int nivel){
        //se agregan los zombies que se deseen
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
        
        //hacer que se muevan
        //ataquen
        //maten o mueran
        
        return 1; //si gano, 2 si perdio pero falta agregarle esas condiciones 
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
