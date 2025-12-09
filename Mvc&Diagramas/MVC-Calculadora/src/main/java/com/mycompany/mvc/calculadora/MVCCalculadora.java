/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mvc.calculadora;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

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
        LocalDateTime d = LocalDateTime.now();
        
        try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
            writer.write("\n-----" + d.toString() + "------\n"
                    + "------------------BITACORA------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
