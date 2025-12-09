/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author josed
 */

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SandwichButtonPanel extends JPanel {
    private final Consumer<String> onSelect;

    public SandwichButtonPanel(Consumer<String> onSelect) {
        this.onSelect = onSelect;
        setLayout(new GridLayout(2, 3, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    public void addSandwichButton(String nombre, ImageIcon icon) {
        JPanel card = new JPanel(new BorderLayout());
        JLabel lblImg;
        if (icon != null) {
            lblImg = new JLabel(icon);
        } else {
            lblImg = new JLabel("No image", SwingConstants.CENTER);
        }
        lblImg.setPreferredSize(new Dimension(140,90));
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        JButton btn = new JButton("Seleccionar");
        btn.addActionListener(e -> onSelect.accept(nombre));

        card.add(lblImg, BorderLayout.CENTER);
        card.add(lblNombre, BorderLayout.NORTH);
        card.add(btn, BorderLayout.SOUTH);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(card);
    }
}
