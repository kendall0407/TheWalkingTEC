/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mvc.calculadora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author kendall-sanabria
 */
public class Controller implements ActionListener{
    private Model modelo;
    private Calculadora vista;
    private boolean borrarMsg = false;
    
    public Controller(Model modelo, Calculadora vista) {
        this.modelo = modelo;
        this.vista = vista;
        suscripciones();
        inicializarBorrar();
        vista.getTxfResultado().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                manejarTecla(e);
            }
        });
    }
    
    public void suscripciones(){
        JButton[] botones = {
            vista.getBtn0(), vista.getBtn1(), vista.getBtn2(),
            vista.getBtn3(), vista.getBtn4(), vista.getBtn5(),
            vista.getBtn6(), vista.getBtn7(), vista.getBtn8(),
            vista.getBtn9(), vista.getBtnSuma(), vista.getBtnResta(),
            vista.getBtnMultiplicacion(), vista.getBtnDivision(), 
            vista.getBtnIgual(), vista.getBtnBorrar(), vista.getBtnPunto(),
            vista.getBtnAvg(), vista.getBtnBinario(), vista.getBtnData(),
            vista.getBtnM(), vista.getBtnPrimo()
        };
        
        for(JButton btn : botones){
            btn.addActionListener(this);
        }
    }    

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Falta hacer la logica
        JButton botonPresionado = (JButton) ae.getSource();
        String numero = botonPresionado.getText();
        procesarEntrada(numero);

    }
    
    public void manejarTecla(KeyEvent e) {
        String tecla = "";

        switch (e.getKeyCode()) {
            case KeyEvent.VK_0: 
                tecla = "0"; 
                break;
            case KeyEvent.VK_1: 
                tecla = "1"; 
                break;
            case KeyEvent.VK_2: 
                tecla = "2"; 
                break;
            case KeyEvent.VK_3: 
                tecla = "3"; 
                break;
            case KeyEvent.VK_4: 
                tecla = "4"; 
                break;
            case KeyEvent.VK_5: 
                tecla = "5"; 
                break;
            case KeyEvent.VK_6: 
                tecla = "6"; 
                break;
            case KeyEvent.VK_7: 
                tecla = "7"; 
                break;
            case KeyEvent.VK_8: 
                tecla = "8"; 
                break;
            case KeyEvent.VK_9: 
                tecla = "9"; 
                break;
                //rango de teclas
            case KeyEvent.VK_ADD: 
            case KeyEvent.VK_PLUS: 
            case KeyEvent.VK_EQUALS:
                tecla = "+"; 
                break;

            case KeyEvent.VK_MINUS: 
                tecla = "-"; 
                break;
            case KeyEvent.VK_MULTIPLY: 
                tecla = "*"; 
                break;
            case KeyEvent.VK_DIVIDE:
            case KeyEvent.VK_SLASH:
                tecla = "÷"; 
                break;
            case KeyEvent.VK_ENTER:
                tecla = "="; 
                break;

            case KeyEvent.VK_BACK_SPACE:
                tecla = "C"; 
                break;

        }

        if (!tecla.equals("")) {
            // Simula que presionaste un botón real
            procesarEntrada(tecla);
        }
    }
    
    public void procesarEntrada(String numero) {
        switch (numero) {
            case "=":
                if (esValido(this.vista.getTxfResultado().getText())) {
                    double resultado = this.modelo.calcular(this.vista.getTxfResultado().getText());

                    if (resultado == Math.floor(resultado)) {
                        int resultadoE = (int) resultado;
                        this.vista.getTxfResultado().setText(resultadoE + "");
                    } else {
                        this.vista.getTxfResultado().setText(resultado + "");
                    }
                    
                } else {
                    this.vista.getTxfResultado().setText("ERR0R");
                    borrarMsg = true;
                }
                this.vista.actualizarBitacora();
                break;
                
            case "DATA":
                this.vista.verBitacora("Bitacora");
                
                break;
            case "AVG":
                String pr = "";
                double promedio = this.modelo.calcularPromedio();
                if (promedio%1!=0) {
                    pr = String.format("%.5f", promedio); //5 decimales maximo
                } else {
                    int p = (int)promedio;
                    pr = Integer.toString(p);
                }
                
                if (promedio != -1) {
                    this.vista.getTxfResultado().setText(pr);
                    this.vista.actualizarBitacora();
                    break;
                }
                this.vista.getTxfResultado().setText("ERR0R");
                borrarMsg = true;
                
                break;
                
            case "M+":
                if (esNumero(this.vista.getTxfResultado().getText())) {
                    this.modelo.agregarMemoriaNum(Double.parseDouble(this.vista.getTxfResultado().getText()));
                } else {
                    this.vista.getTxfResultado().setText("ERR0R");
                    borrarMsg = true;
                }
                this.vista.actualizarBitacora();
                break;
                
            case "Binario":
                if (esNumero(this.vista.getTxfResultado().getText())) {
                    double numBinarioDouble = this.modelo.convertirBinario(Double.parseDouble(this.vista.getTxfResultado().getText()));
                    if (numBinarioDouble %1 == 0) {
                        int numBinario = (int) numBinarioDouble;
                        System.out.println(numBinario);
                        this.vista.getTxfResultado().setText(numBinario + "");
                    } else {
                        this.vista.getTxfResultado().setText(numBinarioDouble + "");
                    }

                    
                } else {
                    this.vista.getTxfResultado().setText("ERR0R!");
                    borrarMsg = true;
                }
                this.vista.actualizarBitacora();
                break;
                
            case "Primo":
                if (esNumero(this.vista.getTxfResultado().getText())) {
                    boolean esPrimo = this.modelo.esPrimo(Double.parseDouble(this.vista.getTxfResultado().getText()));
                    if (esPrimo) 
                        this.vista.getTxfResultado().setText("TRUE");
                    else 
                        this.vista.getTxfResultado().setText("FALSE");
                    
                } else { 
                    this.vista.getTxfResultado().setText("ERR0R!");
                    
                }
                this.vista.actualizarBitacora();
                borrarMsg = true;
                break;
                
            case "C":
                this.vista.getTxfResultado().setText("");
                break;
                
            default:  //Numeros, punto decimal y operadores
                if (borrarMsg) {
                    this.vista.getTxfResultado().setText("");
                    borrarMsg = false;
                }
                vista.agregarDigito(numero);
        }
    }
    public boolean esValido(String s) {
        return s.matches("\\d+(\\.\\d+)?([+\\-*÷]\\d+(\\.\\d+)?)*");
    }

    public boolean esNumero(String s) {
        try {
            Double.parseDouble(s); // intenta convertir a número
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void inicializarBorrar() {
        this.vista.getLblBorrarBitacora().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vista.borrarBitacora();
                modelo.borrarMemoria();
            }
        });
    }
    
}
