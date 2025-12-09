/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author josed
 */

import Controller.AppController;
import Models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private AppController controller;
    private final SandwichButtonPanel sandwichPanel;
    private final OrderPanel orderPanel;

    public MainFrame() {
        super("SubGüey Menú");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        sandwichPanel = new SandwichButtonPanel(this::onSandwichSelected);
        orderPanel = new OrderPanel(
                e -> { if (controller != null) controller.pedirFactura(); },
                e -> { if (controller != null) controller.limpiarOrden(); }
        );

        add(new JScrollPane(sandwichPanel), BorderLayout.CENTER);
        add(orderPanel, BorderLayout.EAST);

        // Cargar botones de sandwich desde MenuData (mantenemos el orden)
        for (String nombre : MenuData.SANDWICHES.keySet()) {
            String imgPath = "/images/" + nombre.toLowerCase().replace("ú","u").replace("ñ","n") + ".png";
            ImageIcon icon = loadIcon(imgPath, 140, 90);
            sandwichPanel.addSandwichButton(nombre, icon);
        }
    }

    public void setController(AppController c) {
        this.controller = c;
    }

    private ImageIcon loadIcon(String resourcePath, int w, int h) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) return null;
            Image img = ImageIO.read(is).getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }

    // callback desde SandwichButtonPanel cuando usuario elige un sandwich
    private void onSandwichSelected(String nombre) {
        // Elegir tamaño
        String[] opts = {
            String.format("15 cm (%.2f$)", MenuData.SANDWICHES.get(nombre)[0]),
            String.format("30 cm (%.2f$)", MenuData.SANDWICHES.get(nombre)[1])
        };
        int sel = JOptionPane.showOptionDialog(this, "Seleccione tamaño para: " + nombre, "Tamaño",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
        if (sel == JOptionPane.CLOSED_OPTION) return;

        int tamano = (sel == 0) ? 15 : 30;

        // Abrir dialogo de extras
        ExtrasDialog dialog = new ExtrasDialog(this, nombre, tamano);
        dialog.setVisible(true);
        if (!dialog.isConfirmed()) return;

        Map<String,Integer> extrasCantidad = dialog.getExtrasCantidad(); // LinkedHashMap
        if (controller != null) controller.agregarSandwichALaOrden(nombre, tamano, extrasCantidad);
    }

    public void actualizarOrdenVisual(Order order) {
        orderPanel.updateOrder(order);
    }
}
