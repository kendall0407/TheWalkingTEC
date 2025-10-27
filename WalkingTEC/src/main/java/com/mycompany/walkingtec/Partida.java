/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.walkingtec;

import java.io.Serializable;

/**
 *
 * @author josed
 */
public class Partida implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int nivel;
    
    public Partida(int nivel){
        this.nivel = nivel;
        
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
}
