/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Sambo extends MartialArt{
    public Sambo(){
name="Sambo";
moves=new Move[]{
new Move("Leg Lock",25),
new ExtraDamageMove("Throw",20,8),
new SelfDamageMove("Tackle",30,5)
};
imagePath="sambo.png";
}
}
