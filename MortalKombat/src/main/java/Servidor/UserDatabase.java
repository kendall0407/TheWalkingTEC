package Servidor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private final File dbFile = new File("users.txt");
    private HashMap<String, User> users = new HashMap<>();

    public UserDatabase() {
        load();
    }

    public synchronized void load() {
        if (!dbFile.exists()) {
            System.out.println("Base de datos no existe, creando nueva...");
            users = new HashMap<>();
            return;
        }
        
        System.out.println("=== INTENTANDO CARGAR ARCHIVO ===");
        System.out.println("Ruta del archivo: " + dbFile.getAbsolutePath());
        System.out.println("Tamaño del archivo: " + dbFile.length() + " bytes");
        
        // Primero intenta leer como texto para ver qué hay
        try {
            System.out.println("=== CONTENIDO EN BRUTO (HEX) ===");
            try (FileInputStream fis = new FileInputStream(dbFile)) {
                int byteRead;
                int count = 0;
                while ((byteRead = fis.read()) != -1) {
                    System.out.printf("%02X ", byteRead);
                    if (++count % 16 == 0) System.out.println();
                }
                System.out.println("\n=== FIN DEL ARCHIVO ===");
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo en bruto: " + e.getMessage());
        }
        
        // Ahora intenta cargar como objeto serializado
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile))) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                users = (HashMap<String, User>) obj;
                System.out.println("✓ Base de datos cargada correctamente");
                System.out.println("Usuarios encontrados: " + users.size());
                
                // Mostrar todos los usuarios
                printAllUsers();
            } else {
                System.err.println("✗ El archivo no contiene un HashMap");
            }
        } catch (FileNotFoundException e) {
            System.err.println("✗ Archivo no encontrado: " + e.getMessage());
            users = new HashMap<>();
        } catch (EOFException e) {
            System.err.println("✗ Archivo vacío o corrupto (EOF): " + e.getMessage());
            users = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Error cargando la base de datos: " + e.getMessage());
            e.printStackTrace();
            users = new HashMap<>();
        }
    }

    public synchronized void save() {
        System.out.println("=== GUARDANDO BASE DE DATOS ===");
        System.out.println("Usuarios a guardar: " + users.size());
        printAllUsers();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile))) {
            oos.writeObject(users);
            oos.flush();
            System.out.println("✓ Base de datos guardada correctamente");
            System.out.println("Tamaño del archivo: " + dbFile.length() + " bytes");
        } catch (IOException e) {
            System.err.println("✗ Error guardando la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para imprimir todos los usuarios
    public synchronized void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No hay usuarios en la base de datos");
            return;
        }
        
        System.out.println("\n=== LISTA DE USUARIOS ===");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            System.out.println("Usuario: " + user.getUsername());
            System.out.println("  Contraseña: " + user.getPassword());
            System.out.println("  Stats: " + user.loadStats());
            System.out.println("  Creado: " + user.getCreatedAt());
            System.out.println("---");
        }
        System.out.println("Total: " + users.size() + " usuarios\n");
    }

    // Métodos restantes igual que antes...
    public synchronized boolean userExists(String username) {
        return users.containsKey(username);
    }

    public synchronized User getUser(String username) {
        return users.get(username);
    }

    public synchronized User createUser(String username, String password) {
        if (userExists(username)) {
            System.err.println("Usuario '" + username + "' ya existe");
            return null;
        }
        
        User u = new User(username, password);
        users.put(username, u);
        save();
        System.out.println("Usuario '" + username + "' creado exitosamente");
        return u;
    }

    public synchronized boolean checkPassword(String username, String password) {
        User u = users.get(username);
        if (u == null) {
            System.err.println("Usuario '" + username + "' no encontrado");
            return false;
        }
        boolean correct = u.getPassword().equals(password);
        System.out.println("Verificación de contraseña para '" + username + "': " + correct);
        return correct;
    }
    
    public synchronized int getUserCount() {
        return users.size();
    }
    
    public synchronized boolean deleteUser(String username) {
        if (users.remove(username) != null) {
            save();
            return true;
        }
        return false;
    }
}