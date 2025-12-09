/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.iterator;

/**
 *
 * @author kendall-sanabria
 */
public class Matriz<T> implements IContainer<T> {
    //datos de matriz
    private T[][] matriz;

    public Matriz(T[][] matrix) {
        this.matriz = matrix;
    }

    @Override
    public IIterator<T> createIterator() {
        return new MatrizIterator();
    }

    private class MatrizIterator implements IIterator<T> {
        //fila y columnas para recorrer
        private int fila = 0;
        private int columna = 0;
        
        @Override
        public boolean hasNext() {
            //se verifica tanto en fila como columna que tienen siguiente elemento
            return fila < matriz.length && columna < matriz[0].length;
        }

        @Override
        public T next() {
            T value = matriz[fila][columna];
            columna++;
            
            //se verifica si llegaron al limite para reiniciar el recorrido en
            // la siguiente fila
            if (columna == matriz[0].length) {
                columna = 0;
                fila++;
            }
            return value;
        }
    }
}