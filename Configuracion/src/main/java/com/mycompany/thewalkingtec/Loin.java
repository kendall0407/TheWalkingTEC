/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thewalkingtec;

/*
private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String usuario = txfUsuario.getText().trim();
        String password = txfPswUsuario.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setText("¡Por favor, digite su usuario y contraseña!");
        } else {
            pnlDatosUsuario.setVisible(false);
        }
    }      
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Loin {
    public static void main(String[] args) {
        JPanel panel = new JPanel();

        // Campos de texto
        JTextField usuarioField = new JTextField(10);
        JPasswordField passField = new JPasswordField(10);

        // Arreglo de objetos a mostrar en el diálogo
        Object[] message = {
            "Usuario:", usuarioField,
            "Contraseña:", passField
        };

        int option = JOptionPane.showConfirmDialog(
            panel, message, "Login", JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String usuario = usuarioField.getText();
            String contrasena = new String(passField.getPassword());
            System.out.println("Usuario: " + usuario);
            System.out.println("Contraseña: " + contrasena);
        } else {
            System.out.println("Login cancelado");
        }
    }
}