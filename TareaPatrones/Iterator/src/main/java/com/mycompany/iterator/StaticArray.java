/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.iterator;

/**
 *
 * @author kendall-sanabria
 */
public class StaticArray<T> implements IContainer<T> {
    //Datos del arreglo
    private T[] arregloDatos;
    
    public StaticArray(T[] arregloDatos) {
        this.arregloDatos = arregloDatos;
    }
    
    @Override
    public IIterator<T> createIterator() {
        //se crea el iterator
        return new StaticArrayIterator();
    }
    
    private class StaticArrayIterator implements IIterator<T> {
        //se define la logica del iterator
        private int indice = 0;
        @Override
        public boolean hasNext() {
            //ver si tiene siguiente
            return indice < arregloDatos.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {  
                throw new RuntimeException("No hay más elementos"); 
            }
            //retornar el siguiente valor si es que tiene
            return arregloDatos[indice++];
        }
        
    }
    
}
