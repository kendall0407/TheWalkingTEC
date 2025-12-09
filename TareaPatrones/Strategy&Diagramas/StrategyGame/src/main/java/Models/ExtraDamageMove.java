/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class ExtraDamageMove extends Move {
    int bonus;
    public ExtraDamageMove(String n,int d,int b){ 
        super(n,d); 
        bonus=b; 
    }
    
    @Override
    public int bonusDamage(){ 
        return bonus; 
    }
}
