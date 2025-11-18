/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Models.AttackCommand;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.event.ChangeListener;

public class AtaqueTripleFrame extends JFrame {

    private JSpinner[] xs = new JSpinner[3];
    private JSpinner[] ys = new JSpinner[3];
    private JComboBox<String> comboCivilizacion;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private Client client;
    private String tipo;

    public AtaqueTripleFrame(Client clientC, String tipoC) {
        this.tipo = tipoC;
        this.client = clientC;
        setTitle("Ataque Triple");
        setSize(420, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---------- TÍTULO ----------
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        JLabel lblTitulo = new JLabel("ATAQUE TRIPLE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // ---------- CABECERAS ----------
        gbc.gridy = 1;
        gbc.gridx = 1;
        add(new JLabel("X"), gbc);
        gbc.gridx = 2;
        add(new JLabel("Y"), gbc);

        // ---------- CAMPOS X/Y ----------
        for (int i = 0; i < 3; i++) {
            gbc.gridy = i + 2;

            gbc.gridx = 0;
            add(new JLabel("Ataque " + (i + 1) + ":"), gbc);

            // X spinner (1–20)
            gbc.gridx = 1;
            xs[i] = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
            igualarTamano(xs[i]);
            add(xs[i], gbc);

            // Y spinner (0–20)
            gbc.gridx = 2;
            ys[i] = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
            igualarTamano(ys[i]);
            add(ys[i], gbc);
        }

        // ---------- CIVILIZACIÓN ----------
        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Civilización:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        comboCivilizacion = new JComboBox<>(new String[]{
            "-- Seleccione --",
            "0",
            "1",
            "2",
            "3"
        });
        add(comboCivilizacion, gbc);

        gbc.gridwidth = 1;

        // ---------- BOTONES ----------
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 3;

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        btnAceptar.setEnabled(false); // Deshabilitado hasta elegir civilización

        botones.add(btnAceptar);
        botones.add(btnCancelar);

        add(botones, gbc);

        // ---------- LISTENERS ----------
        ChangeListener listener = e -> actualizarBoton();
        for (int i = 0; i < 3; i++) {
            xs[i].addChangeListener(listener);
            ys[i].addChangeListener(listener);
        }

        comboCivilizacion.addActionListener(e -> actualizarBoton());

        // Acción aceptar
        btnAceptar.addActionListener(e -> {
            String civ = comboCivilizacion.getSelectedItem().toString();

            String[] valoresX = new String[3];
            String[] valoresY = new String[3];
            String idA = this.client.getID();
            
            for (int i = 0; i < 3; i++) {
                valoresX[i] = xs[i].getValue().toString();
                valoresY[i] = ys[i].getValue().toString();
                String[] informationCmnd = {idA, civ, this.tipo, valoresX[i], valoresY[i]};
            
                AttackCommand cmd = new AttackCommand(informationCmnd);
                
                ObjectOutputStream sender = this.client.getSender();
            try {
                sender.writeObject(cmd);
                sender.flush();
            } catch (IOException ex) {
                System.out.println("Error enviando STATUS");
            }
            
            }
            
            dispose();
    });

        // Acción cancelar
        btnCancelar.addActionListener(e -> dispose());
    }

    private void actualizarBoton() {
        boolean civValida = comboCivilizacion.getSelectedIndex() != 0;
        btnAceptar.setEnabled(civValida);
    }

    private void igualarTamano(JSpinner spinner) {
    Dimension d = new Dimension(70, 25); 
    spinner.setPreferredSize(d);
    spinner.setMinimumSize(d);
    spinner.setMaximumSize(d);
}
    
}
