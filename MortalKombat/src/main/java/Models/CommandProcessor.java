/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author kendall-sanabria
 */
public class CommandProcessor {
    public Command process(String line) {
        // 1. Buscar el guion
        int dash = line.indexOf('-');

        // 2. Obtener el comando (la palabra antes del guion)
        String cmd;
        if (dash != -1) {
            cmd = line.substring(0, dash).trim();
        } else {
            cmd = line.trim();
        }

        // 3. Convertir el comando a enum
        CommandType type = CommandType.from(cmd);
        if (type == null) 
            return null;

        // 4. Obtener los parámetros
        String[] params;

        if (dash != -1) {
            String args = line.substring(dash + 1);
            params = args.split("-");
        } else {
            params = new String[0];
        }

        // 5. Crear el Command correcto
        return type.create(params);
    }
}
