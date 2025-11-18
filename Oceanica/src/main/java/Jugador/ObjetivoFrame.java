/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

/**
 *
 * @author josed
 */

import Models.AttackCommand;
import java.awt.*;
import javax.swing.*;

public class ObjetivoFrame extends JFrame {

    private JComboBox<String> comboCivilizacion;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private Client client;
    private String tipo;

    public ObjetivoFrame(Client clientC, String tipoC) {
        this.tipo = tipoC;
        this.client = clientC;
        setTitle("Seleccionar Objetivo");
        setSize(320, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---- LABEL ----
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Civilización:"), gbc);

        // ---- COMBO ----
        gbc.gridx = 1;
        comboCivilizacion = new JComboBox<>(new String[]{
                "-- Seleccione --",
                "0", "1", "2", "3"
        });
        add(comboCivilizacion, gbc);

        // ---- BOTONES ----
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        btnAceptar.setEnabled(false);

        pnlBotones.add(btnAceptar);
        pnlBotones.add(btnCancelar);

        add(pnlBotones, gbc);

        // Listener para habilitar aceptar
        comboCivilizacion.addActionListener(e -> {
            btnAceptar.setEnabled(comboCivilizacion.getSelectedIndex() != 0);
        });

        // Acción aceptar
        btnAceptar.addActionListener(e -> {
            String civ = (String) comboCivilizacion.getSelectedItem();
            String idA = this.client.getID();
            
            String[] informationCmnd = {idA, civ, this.tipo, "1", "1"};
            
            AttackCommand cmd = new AttackCommand(informationCmnd);
            dispose();
        });

        // Acción cancelar
        btnCancelar.addActionListener(e -> dispose());
    }


}
