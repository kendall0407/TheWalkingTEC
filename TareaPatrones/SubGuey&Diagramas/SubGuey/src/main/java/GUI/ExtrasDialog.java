/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author josed
 */

import Models.MenuData;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExtrasDialog extends JDialog {
    private final Map<String, JSpinner> spinners = new LinkedHashMap<>();
    private boolean confirmed = false;

    public ExtrasDialog(JFrame owner, String sandwichName, int tamano) {
        super(owner, "Extras - " + sandwichName + " " + tamano + "cm", true);
        setSize(420, 360);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(6,6));

        JPanel panel = new JPanel(new GridLayout(MenuData.EXTRAS.size(), 3, 6, 6));
        for (String extra : MenuData.EXTRAS.keySet()) {
            double precio = (tamano == 15) ? MenuData.EXTRAS.get(extra)[0] : MenuData.EXTRAS.get(extra)[1];
            panel.add(new JLabel(extra));
            panel.add(new JLabel(String.format("%.2f$", precio)));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
            spinners.put(extra, spinner);
            panel.add(spinner);
        }

        add(panel, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Agregar a la orden");
        ok.addActionListener(e -> { confirmed = true; setVisible(false); });
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener(e -> { confirmed = false; setVisible(false); });
        btns.add(cancel);
        btns.add(ok);
        add(btns, BorderLayout.SOUTH);
    }

    public boolean isConfirmed() { return confirmed; }

    public LinkedHashMap<String,Integer> getExtrasCantidad() {
        LinkedHashMap<String,Integer> map = new LinkedHashMap<>();
        for (Map.Entry<String,JSpinner> e : spinners.entrySet()) {
            map.put(e.getKey(), (Integer)e.getValue().getValue());
        }
        return map;
    }
}
