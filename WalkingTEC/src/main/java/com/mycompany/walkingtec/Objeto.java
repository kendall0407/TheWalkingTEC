/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

/**
 *
 * @author josed
 */
abstract class Objeto {
    private String nombre;
    private int vida;
    private int dano;
    private int espacio[];
    
    public abstract void morir();
    public abstract void recibidDano(int cantidadDano, Entidad atacante);
}
