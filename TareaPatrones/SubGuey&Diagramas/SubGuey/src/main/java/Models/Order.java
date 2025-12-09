/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotal() {
        double t = 0;
        for (OrderItem oi : items) t += oi.getSubtotal();
        return t;
    }

    public void clear() {
        items.clear();
    }
}