/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;

/**
 *
 * @author josed
 */
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password; // simple plain-text for example (NOT for production)
    private int[] stats; // 6 ints

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.stats = new int[6];
        for (int i = 0; i < 6; i++) this.stats[i] = 0;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int[] getStats() { return stats; }
    public void setStats(int[] stats) { this.stats = stats; }

    // función inútil por ahora que "cargaría" stats
    public void loadStats() {
        System.out.println("Cargando stats de " + username + ":");
        for (int i = 0; i < stats.length; i++) System.out.println("stat["+i+"]="+stats[i]);
    }
}



