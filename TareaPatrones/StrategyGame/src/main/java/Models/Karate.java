/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Karate extends MartialArt{
    public Karate(){
        name="Karate";
        moves=new Move[]{
        new Move("Gyaku Zuki",20),
        new HealMove("Kizami Zuki",15,5),
        new ExtraDamageMove("Mawashi Geri",25,5)
        };
    imagePath="karate.png";
    }
}
