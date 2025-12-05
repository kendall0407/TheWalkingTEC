/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mvc.calculadora;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author kendall-sanabria
 */
public class Model {
    private ArrayList<Double> memoria = new ArrayList<>();
    private boolean flotante = false;

    public ArrayList<Double> getMemoria() {
        return memoria;
    }

    public void agregarMemoriaNum(double num) {
        if (memoria.size() == 10) {
            memoria.remove(0);
        }
        this.memoria.add(num);
        try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
            writer.write(memoria.size() + 1 + "." + num + " guardado\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public double calcularPromedio() {
        if (memoria.size() == 0) {
            return -1;
        }
        double promedio = 0;
        for (Double num : memoria) {
            promedio += num;
        }
        promedio = promedio / memoria.size();
        try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
            writer.write("Promedio = "+ promedio +"\n"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promedio;
    }
    
    public void setFlotante(boolean flotante) {
        this.flotante = flotante;
    }
    
    
    
    public double calcular(String expresion) {
        ArrayList<String> listaExpresion = listar(expresion);
        ArrayList<Double> pilaNumeros = new ArrayList<>();
        ArrayList<String> pilaOperadores = new ArrayList<>();
        try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
            writer.write(expresion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pilaNumeros.add(Double.parseDouble(listaExpresion.get(0)));
        for (int i = 1; i < listaExpresion.size(); i+=2) {
            String numero = listaExpresion.get(i+1);
            String op = listaExpresion.get(i);
            
            if (op.equals("+") || op.equals("-")) {
                pilaOperadores.add(op);
                pilaNumeros.add(Double.parseDouble(numero));
                
            } else if (op.equals("*")) {
                double num1 = pilaNumeros.remove(pilaNumeros.size() - 1);
                double num2 = Integer.parseInt(numero);
                double r = num1*num2;
                pilaNumeros.add(r);
            } else if (op.equals("÷")){
                double num1 = pilaNumeros.remove(pilaNumeros.size() - 1);
                double num2 = Integer.parseInt(numero);
                double r = num1/num2;
                pilaNumeros.add(r);
            }
        }
        
        double resultado = pilaNumeros.get(0);
        int j = 0;
        for (int i = 1; i < pilaNumeros.size(); i++) {
            double num2 = pilaNumeros.get(i);
            switch (pilaOperadores.get(j)) {
                case "+":
                    resultado += num2;
                    break;
                case "-":
                    resultado -= num2;
                    break;
            }
            j++;
        }
        
        if (resultado %1 == 0) {
            int resultadoE = (int)resultado;
            try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(" = " + resultadoE + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(" = " + resultado + "\n");
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return resultado;
    }
    
    public ArrayList<String> listar(String expresion) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Si es dígito o punto va al número
            if (Character.isDigit(c) || c == '.') {
                numero.append(c);
            } 
            // Si es operador
            else if (c == '+' || c == '-' || c == '*' || c == '÷') {
                // Si había un número acumulado, lo agrego
                if (numero.length() > 0) {
                    tokens.add(numero.toString());
                    numero.setLength(0);
                }
                tokens.add(String.valueOf(c)); // operador como token
            }
        }

        // Agregar el último número si quedó alguno
        if (numero.length() > 0) {
            tokens.add(numero.toString());
        }
        
        
        return tokens;
    }
    
    public double convertirBinario(double num) {  
        double decimales = 0;
        if (num == 0) {
            return 0;
        }
        if (num %1 != 0) {
            flotante = true;
            decimales = num%1;
            num = (int) num;
            decimales = convertirDecimalesBinario(decimales);
        }
        
        int entero = (int)num;
        double numConvertido = binarioDiv(entero, 0, 0);
        
        if (flotante) {
            numConvertido += decimales;
        }
        if (numConvertido %1 == 0) {
            int numBinario = (int)numConvertido;
            try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(num+" -> " +numBinario + " en binario\n");
            } catch (IOException e) {
            }
        } else {
            try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(num+" -> " +numConvertido + " en binario\n");
            } catch (IOException e) {
            }
        }

        
        return numConvertido;
    }
    
    private double binarioDiv(int num, double ceros, double binario) {
        if (num == 0) {
            return binario;
        }
        else if (num %2 == 0) {
            return binarioDiv(num/2, ceros+1, binario);
        } else {
            binario = binario+(Math.pow(10.0, ceros));
            return binarioDiv(num/2, ceros+1, binario);
        }
    }
    
    private double convertirDecimalesBinario(double decimales) {
        int limite = 8;
        ArrayList<Double> operacionesRealizadas = new ArrayList<>();
        boolean patron = true;
        int i = 0;
        int parteEntera = 0;
        double nuevo = decimales;
        double resultado = 0;
        int ceros = 0;
        while (patron && i!=limite) {
            nuevo = nuevo * 2;
            nuevo = Math.round(nuevo * 100.0) / 100.0;
            parteEntera = (int)nuevo;
            if (parteEntera == 1) {
                nuevo -= 1;
                resultado = (resultado+1)*Math.pow(10, -(i+1));
            }

            if (!operacionesRealizadas.contains(nuevo)) {
                operacionesRealizadas.add(nuevo);
                i++;
            } else {
                patron = false;
            }
        }

        return resultado;        
    }
    
    public boolean esPrimo(double num) {
        if (num % 1 != 0) {
            try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(num + " no es PRIMO, FALSE\n");
            } catch (IOException e) {
            }
            return false;
        }
        int numero = (int)num;
        if (numero <= 1) {
            try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(num + " no es PRIMO, FALSE\n");
            } catch (IOException e) {
            }
            return false;
            
        }
        for (int i = 2; i <= Math.sqrt(numero)-1; i++) {
            if (numero % i == 0) {
                try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                    writer.write(num + " no es PRIMO, FALSE\n");
                } catch (IOException e) {
                }
                return false;
            } 
        }
        try (FileWriter writer = new FileWriter("Bitacora.txt", true)) {
                writer.write(num + " es PRIMO, TRUE\n");
            } catch (IOException e) {
            }
        return true;
    }
}
