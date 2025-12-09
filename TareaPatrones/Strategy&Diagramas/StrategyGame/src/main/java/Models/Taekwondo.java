/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Taekwondo extends MartialArt{
    public Taekwondo(){
name="Taekwondo";
moves=new Move[]{
new Move("Ap Chagi",20),
new Move("Dollyo Chagi",25),
new ExtraDamageMove("Neryo Chagi",15,10)
};
imagePath="tkd.png";
}
}
