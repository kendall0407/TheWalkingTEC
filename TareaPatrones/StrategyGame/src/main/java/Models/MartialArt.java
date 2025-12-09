/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class MartialArt {
    String name;
    Move[] moves;
    String imagePath;


    public String getName() { 
        return name; 
    }
    public Move[] getMoves() { 
        return moves; 
    }
    public String getImagePath() { 
        return imagePath; 
    }

    public Move randomMove() {
        return moves[(int)(Math.random() * 3)];
    }
}
