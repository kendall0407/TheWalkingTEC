/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author josed
 */

import Models.*;
import GUI.*;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppController {
    private final Order order;
    private final MainFrame view;

    public AppController(MainFrame view) {
        this.order = new Order();
        this.view = view;
        this.view.setController(this);
    }
    public void agregarSandwichALaOrden(String nombre, int tamanoCm, Map<String, Integer> extrasCantidad) {
        double precioBase = (tamanoCm == 15) ? MenuData.SANDWICHES.get(nombre)[0] : MenuData.SANDWICHES.get(nombre)[1];
        Sandwich s = new ConcreteSandwich(nombre, tamanoCm, precioBase);
        
        // Aplicar decorator por cada unidad de extra
        // Usamos extrasCantidad en orden de inserción (LinkedHashMap) para legibilidad.
        for (Map.Entry<String,Integer> e : extrasCantidad.entrySet()) {
            String ext = e.getKey();
            int qty = e.getValue();
            if (qty <= 0) continue;
            double precioExtra = (tamanoCm == 15) ? MenuData.EXTRAS.get(ext)[0] : MenuData.EXTRAS.get(ext)[1];
            for (int i=0;i<qty;i++) {
                s = new ExtraItem(ext, precioExtra, s);
            }
        }
        Map<String,Integer> filtrados = new LinkedHashMap<>();
        for (Map.Entry<String,Integer> e : extrasCantidad.entrySet()) {
            if (e.getValue() > 0)
                filtrados.put(e.getKey(), e.getValue());
        }

        OrderItem item = new OrderItem(s, filtrados);
        order.addItem(item);
        view.actualizarOrdenVisual(order);
    }
    
    public void pedirFactura() {
        // Aquí se podría expandir: guardar en DB, imprimir, exportar PDF, etc.
        double total = order.getTotal();
        JOptionPane.showMessageDialog(view, String.format("Total a pagar: %.2f$", total), "Factura", JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarOrden() {
        order.clear();
        view.actualizarOrdenVisual(order);
    }
}
