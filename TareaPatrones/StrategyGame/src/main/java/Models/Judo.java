/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Judo extends MartialArt{
    public Judo(){
name="Judo";
moves=new Move[]{
new Move("O Soto Gari",15),
new SelfDamageMove("Tomoe Nage",30,10),
new HealMove("Kuzushi",5,10)
};
imagePath="judo.png";
}
}
