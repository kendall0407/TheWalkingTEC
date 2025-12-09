/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Boxing extends MartialArt{
    public Boxing(){
name="Boxing";
moves=new Move[]{
new Move("Jab",10),
new Move("Cross",20),
new ExtraDamageMove("Uppercut",25,5)
};
imagePath="boxing.png";
}
}
