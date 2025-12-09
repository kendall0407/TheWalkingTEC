/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class MuayThai extends MartialArt{
    public MuayThai(){
name="Muay Thai";
moves=new Move[]{
new Move("Elbow Strike",30),
new HealMove("Teep Kick",10,5),
new ExtraDamageMove("Flying Knee",20,10)
};
imagePath="muaythai.png";
}
}
