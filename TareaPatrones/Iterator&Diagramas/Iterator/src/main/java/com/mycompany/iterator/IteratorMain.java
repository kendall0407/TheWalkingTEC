/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.iterator;

/**
 *
 * @author kendall-sanabria
 */
public class IteratorMain {

    public static void main(String[] args) {
        System.out.println("---------Arreglo---------");
        // Arreglo de strings
        StaticArray<Integer> arr = new StaticArray<>(new Integer[]{2,5,1,23});
        recorrer(arr);
        
        // Matriz de strings
        System.out.println("\n---------Matriz---------");
        Matriz<String> mat = new Matriz<>(new String[][]{
            {"Kratos", "Poseidon"},
            {"Rambo", "Pennywise"}
        });
        recorrer(mat);
        
        //Arbol binario
        System.out.println("\n---------Arbol Binario---------");
        BinaryTree<String> tree = new BinaryTree<>("America");
        BinaryTree<String>.Node<String> root = tree.getRoot();

        root.left = tree.new Node<>("Rusia");
        root.right = tree.new Node<>("Estados Unidos");
        recorrer(tree);
    }
    
    
    public static <T> void recorrer(IContainer<T> estructura) {
        IIterator<T> empIterator = estructura.createIterator();
        while (empIterator.hasNext()) {
            T emp = empIterator.next();
            System.out.println(" -> " + emp.toString());
        }
    }
}
