/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;

/**
 *
 * @author josed
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class AuthGUI {

    public static User promptForUser(UserDatabase db) {
        final CountDownLatch latch = new CountDownLatch(1);
        final User[] result = new User[1];

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Autenticación - Servidor");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(350, 200);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel userLabel = new JLabel("Usuario:");
            JTextField userField = new JTextField();
            JLabel passLabel = new JLabel("Contraseña:");
            JPasswordField passField = new JPasswordField();

            panel.add(userLabel);
            panel.add(userField);
            panel.add(passLabel);
            panel.add(passField);

            JButton loginBtn = new JButton("Aceptar");
            JButton cancelBtn = new JButton("Cancelar");

            JPanel buttons = new JPanel();
            buttons.add(loginBtn);
            buttons.add(cancelBtn);

            frame.add(panel, BorderLayout.CENTER);
            frame.add(buttons, BorderLayout.SOUTH);

            loginBtn.addActionListener(e -> {
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword());
                if (user.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Ingrese un nombre de usuario.");
                    return;
                }
                User existing = db.getUser(user);
                if (existing == null) {
                    // crear cuenta
                    int opt = JOptionPane.showConfirmDialog(frame, "Usuario no existe. Crear cuenta?", "Crear cuenta", JOptionPane.YES_NO_OPTION);
                    if (opt == JOptionPane.YES_OPTION) {
                        User nu = db.createUser(user, pass);
                        JOptionPane.showMessageDialog(frame, "Cuenta creada. Bienvenido " + user);
                        result[0] = nu;
                        frame.dispose();
                        latch.countDown();
                    }
                } else {
                    if (db.checkPassword(user, pass)) {
                        JOptionPane.showMessageDialog(frame, "Bienvenido " + user);
                        result[0] = existing;
                        frame.dispose();
                        latch.countDown();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Contraseña incorrecta. Intente de nuevo.");
                        passField.setText("");
                    }
                }
            });

            cancelBtn.addActionListener(e -> {
                frame.dispose();
                latch.countDown();
            });

            frame.setVisible(true);
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return result[0];
    }
}

