/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author kendall-sanabria
 */
public class BinaryTree<T> implements IContainer<T> {
    private ArrayList<T> values = new ArrayList<>(); //valores recorridos
    private int index = 0; //pos actual
    
    //nodo del arbol: tiene un valor y dos hijos (izq y der)
    class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T v) { 
            value = v; 
        }
    }

    private Node<T> root; //raiz del arbol

    //Crea el arbol con un valor raiz
    public BinaryTree(T rootValue) {
        this.root = new Node<T>(rootValue);
    }

    //Retorna un iterador para recorrer el árbol
    @Override
    public IIterator<T> createIterator() {
        return new TreeIterator();
    }

    //Iterador que recorre el árbol In-Order
    private class TreeIterator implements IIterator<T> {

        //Cuando se crea el iterador,recorre todo el arbol
        public TreeIterator() {
            enOrden(root);
        }

        //recorrido izquierda,nodo,derecha
        private void enOrden(Node<T> node) {
            if (node == null) return;

            enOrden(node.left);
            values.add(node.value); //se guarda el valor del nodo
            enOrden(node.right);
        }

        //revisa si quedan elementos por recorrer
        @Override
        public boolean hasNext() {
            return index < values.size();
        }

        //Devuelve el siguiente valor
        @Override
        public T next() {
            return values.get(index++);
        }
    }

    //Devuelve la raiz para que puedas construir el arbol
    public Node<T> getRoot() {
        return root;
    }

    //Texto simple del arbol
    @Override                
    public String toString() {                    
        return "Valor: " + values.get(index);
    } 
}