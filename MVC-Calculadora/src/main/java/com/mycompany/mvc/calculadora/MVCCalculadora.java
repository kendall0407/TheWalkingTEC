/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mvc.calculadora;

/**
 *
 * @author kendall-sanabria
 */
public class MVCCalculadora {

    public static void main(String[] args) {
        Calculadora vista = new Calculadora();
        Model modelo = new Model();
        Controller controller = new Controller(modelo, vista);
        vista.setVisible(true);
    }
}
