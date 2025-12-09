/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class SelfDamageMove extends Move {
    int penalty;
    public SelfDamageMove(String n,int d,int p){ 
        super(n,d); 
        penalty=p; 
    }
    
    @Override
    public int selfDamage(){ 
        return penalty; 
    }
}
