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
    ATTACK (4), 
    MESSAGE (2),
    PRIVATEMESSAGE (3),
    GIVEUP (1);
    
    private int requiredParameters;
    
    private CommandType(int requiredParameters){
        this.requiredParameters = requiredParameters;
    }

    public int getRequiredParameters() {
        return requiredParameters;
    }
    
    
}
