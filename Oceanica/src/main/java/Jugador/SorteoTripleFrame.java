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

public class SorteoTripleFrame extends JFrame {

    private JSpinner[] spinners = new JSpinner[3];
    private int[] prevValues = new int[3];
    private boolean ignoreChange = false; // para evitar reentradas al setear valores programáticamente

    private JComboBox<String> comboCivilizacion;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private Client client;

    public SorteoTripleFrame(Client clientC) {
        this.client = clientC;
        setTitle("SORTEO");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel lblTitulo = new JLabel("SORTEO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitulo, gbc);

        // Subtítulo
        gbc.gridy = 1;
        JLabel lblSub = new JLabel("Escoge 3 números entre 1 y 9:", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblSub, gbc);

        // Labels de columna
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Núm 1:"), gbc);
        gbc.gridx = 1;
        add(new JLabel("Núm 2:"), gbc);
        gbc.gridx = 2;
        add(new JLabel("Núm 3:"), gbc);

        // Inicializar spinners con valores distintos por defecto (0,1,2)
        for (int i = 0; i < 3; i++) {
            int inicial = i; // 0,1,2
            prevValues[i] = inicial;
            SpinnerNumberModel model = new SpinnerNumberModel(inicial, 0, 9, 1);
            spinners[i] = new JSpinner(model);
            igualarTamano(spinners[i]);

            final int idx = i;
            spinners[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (ignoreChange) return;
                    onSpinnerChanged(idx);
                }
            });

            gbc.gridy = 3;
            gbc.gridx = i;
            add(spinners[i], gbc);
        }

        // Combo de civilización
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Civilización:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        comboCivilizacion = new JComboBox<>(new String[] {
            "-- Seleccione --",
            "0",
            "1",
            "2",
            "3"
        });
        add(comboCivilizacion, gbc);
        gbc.gridwidth = 1;

        // Botones
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        btnAceptar.setEnabled(false);
        pnlBotones.add(btnAceptar);
        pnlBotones.add(btnCancelar);
        add(pnlBotones, gbc);

        // Listeners
        comboCivilizacion.addActionListener(e -> actualizarAceptar());

        btnAceptar.addActionListener(e -> {
            String civ = (String) comboCivilizacion.getSelectedItem();
            StringBuilder sb = new StringBuilder("Sorteo seleccionado:\nCivilización: ").append(civ).append("\nNúmeros: ");
            for (int i = 0; i < 3; i++) {
                sb.append(spinners[i].getValue());
                if (i < 2) sb.append(", ");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        // Inicializar estado del botón aceptar
        actualizarAceptar();
    }

    // Maneja cambios en el spinner de índice idx
    private void onSpinnerChanged(int idx) {
        int newVal = (Integer) spinners[idx].getValue();

        // Verificar si alguno de los otros spinners ya tiene ese valor
        for (int j = 0; j < 3; j++) {
            if (j == idx) continue;
            int otherVal = (Integer) spinners[j].getValue();
            if (otherVal == newVal) {
                // Duplicado: mostrar aviso y volver al valor anterior (prevValues[idx])
                JOptionPane.showMessageDialog(this,
                        "El número " + newVal + " ya está seleccionado. Elige otro número.",
                        "Número duplicado",
                        JOptionPane.WARNING_MESSAGE);

                // Revertir al valor anterior de forma segura sin disparar listeners
                ignoreChange = true;
                try {
                    // Si prevValues[idx] está fuera de rango (caso raro), buscar un valor disponible
                    int revert = prevValues[idx];
                    if (revert < 0 || revert > 9) {
                        revert = primerValorDisponibleExcluyendo(j, idx);
                    }
                    spinners[idx].setValue(revert);
                } finally {
                    ignoreChange = false;
                }
                return;
            }
        }

        // Si llegamos aquí, el nuevo valor es válido: guardarlo y actualizar estado
        prevValues[idx] = newVal;
        actualizarAceptar();
    }

    // Encuentra el primer valor disponible entre 0-9 que no esté presente en otros spinners
    private int primerValorDisponibleExcluyendo(int... excludeIndices) {
        boolean[] usado = new boolean[10];
        for (int i = 0; i < 3; i++) {
            boolean skip = false;
            for (int ex : excludeIndices) if (ex == i) skip = true;
            if (skip) continue;
            int v = (Integer) spinners[i].getValue();
            if (v >= 0 && v <= 9) usado[v] = true;
        }
        for (int v = 0; v <= 9; v++) if (!usado[v]) return v;
        return 0; // fallback
    }

    // Habilita aceptar solo si civilización seleccionada y los 3 números son distintos
    private void actualizarAceptar() {
        boolean civValida = comboCivilizacion.getSelectedIndex() != 0;
        boolean distintos = sonTresDistintos();
        btnAceptar.setEnabled(civValida && distintos);
    }

    private boolean sonTresDistintos() {
        int a = (Integer) spinners[0].getValue();
        int b = (Integer) spinners[1].getValue();
        int c = (Integer) spinners[2].getValue();
        return a != b && b != c && a != c;
    }

    // Fuerza tamaño uniforme a los spinners para que se vean iguales
    private void igualarTamano(JSpinner spinner) {
        Dimension d = new Dimension(60, 26);
        spinner.setPreferredSize(d);
        spinner.setMinimumSize(d);
        spinner.setMaximumSize(d);
    }

}
