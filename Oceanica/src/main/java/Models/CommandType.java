/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public enum CommandType {
    ATTACK (5), 
    RECEIVEATTACK (5),
    MESSAGE (1),
    PRIVATEMESSAGE (3),
    GIVEUP (1),
    DEFEND (1),      
    HEAL (2),        
    STATUS (1),
    STATUSRESPONSE(1),
    CIVILIZATION(1),
    ASSIGN_TARGET(1);
    
    private int requiredParameters;
    
    private CommandType(int requiredParameters){
        this.requiredParameters = requiredParameters;
    }

    public int getRequiredParameters() {
        return requiredParameters;
    }
    
    
}
