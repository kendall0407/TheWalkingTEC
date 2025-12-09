/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */

import java.util.HashMap;
import java.util.Map;

public class OrderItem {
    private final Sandwich sandwichWithExtras;
    private final Map<String, Integer> extrasCount;
    private final double subtotal;
    
    public OrderItem(Sandwich sandwichWithExtras, Map<String, Integer> extrasCount) {
        this.sandwichWithExtras = sandwichWithExtras;
        this.extrasCount = new HashMap<>(extrasCount);
        this.subtotal = sandwichWithExtras.getPrecio();
    }
    
    public String getDescripcionLegible() {
        // Convertir descripcion base y agregar extras con cantidades
        String base = getBase();
        if (extrasCount.isEmpty()) return base + String.format("  => %.2f$", subtotal);

        StringBuilder sb = new StringBuilder(base);
        sb.append("  + ");
        boolean first = true;
        for (Map.Entry<String,Integer> e : extrasCount.entrySet()) {
            if (!first) sb.append(" + ");
            sb.append(String.format("%dx%s", e.getValue(), e.getKey()));
            first = false;
        }
        sb.append(String.format("  => %.2f$", subtotal));
        return sb.toString();
    }

    public double getSubtotal() { 
        return subtotal; 
    }
    
    public String getBase() {

        String desc = sandwichWithExtras.getDescripcion();
            if (desc.contains(",")) {
            return desc.substring(0, desc.indexOf(",")).trim();
        }
        return desc.trim();
    }
}
