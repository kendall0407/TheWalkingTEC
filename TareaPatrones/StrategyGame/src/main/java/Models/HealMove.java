/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class HealMove extends Move {
    int heal;
    public HealMove(String n,int d,int h){ 
        super(n,d); 
        heal=h; 
    }
    
    @Override
    public int heal(){ 
        return heal; 
    }
}
