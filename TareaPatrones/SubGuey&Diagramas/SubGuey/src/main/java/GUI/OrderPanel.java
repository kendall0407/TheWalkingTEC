/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author josed
 */

import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.*;

public class OrderPanel extends JPanel {
    private final JTextArea area;
    private final JButton btnFacturar;
    private final JButton btnLimpiar;

    public OrderPanel(ActionListener onFacturar, ActionListener onLimpiar) {
        setLayout(new BorderLayout(6,6));
        setPreferredSize(new Dimension(340, 600));
        setBorder(BorderFactory.createTitledBorder("Ver mi orden"));

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(1,2,6,6));
        btnFacturar = new JButton("Generar Factura");
        btnFacturar.addActionListener(onFacturar);
        btnLimpiar = new JButton("Limpiar Orden");
        btnLimpiar.addActionListener(onLimpiar);
        bottom.add(btnLimpiar);
        bottom.add(btnFacturar);
        add(bottom, BorderLayout.SOUTH);
    }

    public void updateOrder(Order order) {
        StringBuilder sb = new StringBuilder();
        List<OrderItem> items = order.getItems();
        if (items.isEmpty()) {
            sb.append("Orden vacía.\n");
        } else {
            for (int i=0;i<items.size();i++){
                OrderItem it = items.get(i);
                sb.append(String.format("%d) %s\n", i+1, it.getDescripcionLegible()));
            }
            sb.append("\n");
            sb.append(String.format("TOTAL: %.2f$\n", order.getTotal()));
        }
        area.setText(sb.toString());
    }
}