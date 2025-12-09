/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class KungFu extends MartialArt{
    public KungFu(){
name="Kung Fu";
moves=new Move[]{
new Move("Palm Strike",18),
new ExtraDamageMove("Tiger Claw",22,8),
new HealMove("Qi Touch",5,15)
};
imagePath="kungfu.png";
}
}
