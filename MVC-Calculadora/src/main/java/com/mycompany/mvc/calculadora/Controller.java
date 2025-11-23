/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mvc.calculadora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author kendall-sanabria
 */
public class Controller implements ActionListener{
    private Model modelo;
    private Calculadora vista;

    public Controller(Model modelo, Calculadora vista) {
        this.modelo = modelo;
        this.vista = vista;
        suscripciones();
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
        String texto = botonPresionado.getText();
        
        System.out.println("Botón presionado: " + texto);
        
        // Lógica según el tipo de botón
        if(texto.matches("[0-9]")){ // Números 0-9
            vista.agregarDigito(texto);
            
        } //else if...
        //continua
    }
}
