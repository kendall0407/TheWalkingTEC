/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class KravMaga extends MartialArt{
    public KravMaga(){
name="Krav Maga";
moves=new Move[]{
new Move("Groin Kick",30),
new ExtraDamageMove("Elbow Smash",25,10),
new SelfDamageMove("Aggressive Rush",40,15)
};
imagePath="kravmaga.png";
}
}
