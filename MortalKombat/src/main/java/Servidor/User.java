/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

/**
 *
 * @author josed
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password; // simple plain-text for example (NOT for production)
    private int[] stats; // 6 ints
    private LocalDateTime createdAt;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.stats = new int[6];
        for (int i = 0; i < 6; i++) this.stats[i] = 0;
        this.createdAt = LocalDateTime.now();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int[] getStats() { return stats; }
    public void setStats(int[] stats) { this.stats = stats; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void actualizarStats(int[] statsNuevas) {
        for (int i = 0; i < 6; i++) {
            stats[i] = statsNuevas[i];
        }
    }
    // función inútil por ahora que "cargaría" stats
    public String loadStats() {
        return Arrays.toString(stats);
    }
    
    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", stats=" + Arrays.toString(stats) +
               ", createdAt=" + createdAt +
               '}';
    }
}



