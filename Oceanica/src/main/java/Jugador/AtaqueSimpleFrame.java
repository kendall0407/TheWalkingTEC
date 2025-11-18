/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

/**
 *
 * @author josed
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Models.AttackCommand;

public class AtaqueSimpleFrame extends JFrame {

    private JSpinner spnX;
    private JSpinner spnY;
    private JComboBox<String> comboCivilizacion;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private Client client;
    private String tipo;

    public AtaqueSimpleFrame(Client clientC, String tipoC) {
        this.client = clientC;
        this.tipo = tipoC;
        setTitle("ATACAR");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(360, 240);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label X
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("X (1-20):"), gbc);

        // Spinner X (1..20)
        gbc.gridx = 1;
        spnX = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        add(spnX, gbc);

        // Label Y
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Y (1-20):"), gbc);

        // Spinner Y (0..20)
        gbc.gridx = 1;
        spnY = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        add(spnY, gbc);

        // Label civilizacion
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Civilización:"), gbc);

        // ComboBox (primer elemento es placeholder)
        gbc.gridx = 1;
        comboCivilizacion = new JComboBox<>(new String[] {
            "-- Seleccione --",
            "0",
            "1",
            "2",
            "3",
        });
        // por defecto selecciona el placeholder
        comboCivilizacion.setSelectedIndex(0);
        add(comboCivilizacion, gbc);

        // Panel de botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        // Inicialmente deshabilitado
        btnAceptar.setEnabled(false);

        pnlBotones.add(btnAceptar);
        pnlBotones.add(btnCancelar);
        add(pnlBotones, gbc);

        // Listeners para habilitar/deshabilitar Aceptar
        ChangeListener spinnerListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                actualizarBotonAceptar();
            }
        };
        spnX.addChangeListener(spinnerListener);
        spnY.addChangeListener(spinnerListener);

        comboCivilizacion.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarBotonAceptar();
            }
        });

        // Acción Aceptar: muestra confirmación y cierra
        btnAceptar.addActionListener(e -> {
            String x = spnX.getValue().toString();
            String y = spnY.getValue().toString();
            String civ = (String) comboCivilizacion.getSelectedItem();
            String idA = this.client.getID();
            
            String[] informationCmnd = {idA, civ, this.tipo, x, y};
            
            AttackCommand cmd = new AttackCommand(informationCmnd);
            
            dispose();
        });

        // Acción Cancelar: cierra
        btnCancelar.addActionListener(e -> dispose());
    }

    // Habilita Aceptar solo si la civilización seleccionada no es el placeholder
    private void actualizarBotonAceptar() {
        String seleccionado = (String) comboCivilizacion.getSelectedItem();
        boolean civSeleccionada = seleccionado != null && !seleccionado.equals("-- Seleccione --");

        // Los spinners ya están acotados por su modelo (1..20 y 0..20), así que no hace falta comprobar rangos
        btnAceptar.setEnabled(civSeleccionada);
    }

}