/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuData {
    // Sandwiches: nombre -> {precio15, precio30}
    public static final Map<String, double[]> SANDWICHES = new LinkedHashMap<>();
    // Extras: nombre -> {precio15, precio30}
    public static final Map<String, double[]> EXTRAS = new LinkedHashMap<>();

    static {
        SANDWICHES.put("Pavo", new double[]{12.0, 18.0});
        SANDWICHES.put("Italiano", new double[]{9.0, 16.0});
        SANDWICHES.put("Beef", new double[]{10.0, 16.0});
        SANDWICHES.put("Veggie", new double[]{8.0, 14.0});
        SANDWICHES.put("Atún", new double[]{11.0, 17.0});
        SANDWICHES.put("Pollo", new double[]{12.0, 18.0});

        EXTRAS.put("Aguacate", new double[]{1.5, 2.5});
        EXTRAS.put("Doble proteína", new double[]{4.5, 8.0});
        EXTRAS.put("Hongos", new double[]{0.85, 1.45});
        EXTRAS.put("Refresco", new double[]{1.0, 1.0});
        EXTRAS.put("Sopa", new double[]{4.2, 4.2});
        EXTRAS.put("Postre", new double[]{3.5, 3.5});
    }
}
