/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Servidor.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 *
 * @author kendall-sanabria
 */
public class UserDataBase {
    private final File dbFile = new File("users.txt");
    private HashMap<String, User> users = new HashMap<>();
    
    public synchronized void load() {
        if (!dbFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile))) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                users = (HashMap<String, User>) obj;
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la BD: " + e.getMessage());
        }
    }

    public synchronized void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error guardando DB: " + e.getMessage());
        }
    }

    public synchronized User getUser(String username) {
        return users.get(username);
    }

    public synchronized User createUser(String username, String password) {
        User u = new User(username, password);
        users.put(username, u);
        save();
        return u;
    }

    public synchronized boolean checkPassword(String username, String password) {
        User u = users.get(username);
        if (u == null) return false;
        return u.getPassword().equals(password);
    }
    
    public synchronized boolean userExists(String username) {
        return users.containsKey(username);
    }
}
