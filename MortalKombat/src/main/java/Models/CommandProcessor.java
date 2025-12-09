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
    public Command process(String line, int id) {
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
        
        String[] newParams = new String[params.length + 1];
        newParams[0] = String.valueOf(id);

        for (int i = 0; i < params.length; i++) {
            newParams[i + 1] = params[i];
        }

        params = newParams;
        // 5. Crear el Command correcto
        return type.create(params);
    }
    
    public Command processAtack(String line, String danos, String id, String poder) {
        
        // 1. Separar por guiones
        String[] params = line.split("-");
        
        // 2. Crear un arreglo más grande para meter los extras
        String[] finalParams = new String[params.length + 2];

        // 3. Copiar los parámetros originales
        for (int i = 0; i < params.length-1; i++) {
            finalParams[i] = params[i+1];
        }

        // 4. Agregar las nuevas cosas al final
        finalParams[params.length-1] = danos;
        finalParams[params.length] = id;
        finalParams[params.length+1] = poder;
        
        return new AttackCommand(finalParams);
    }
    
    public Command processJoker(String line, String danos1, String danos2, String id, String poder1, String poder2) {
        
        // 1. Separar por guiones
        String[] params = line.split("-");
        
        // 2. Crear un arreglo más grande para meter los extras
        String[] finalParams = new String[params.length + 4];

        // 3. Copiar los parámetros originales
        for (int i = 0; i < params.length-1; i++) {
            finalParams[i] = params[i+1];
        }

        // 4. Agregar las nuevas cosas al final
        finalParams[params.length+1] = danos1;
        finalParams[params.length] = danos2;
        finalParams[params.length+1] = id;
        finalParams[params.length+2] = poder1;
        finalParams[params.length+3] = poder2;

        return new AttackCommand(finalParams);
    }
}
