/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Move {
    String name;
    int damage;
    
    public Move(String n, int d){ name=n; damage=d; }
    public String getName(){ 
        return name; 
    }
    public int getDamage(){ 
        return damage; 
    }
    public int heal(){ 
        return 0; 
    } // override in heal moves
    public int selfDamage(){ 
        return 0; 
    }// override in self-damage moves
    public int bonusDamage(){ 
        return 0; 
    } // override in extra-damage moves
}
