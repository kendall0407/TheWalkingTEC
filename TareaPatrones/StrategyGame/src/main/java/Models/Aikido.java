/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Aikido extends MartialArt{
    public Aikido(){
name="Aikido";
moves=new Move[]{
new Move("Irimi Nage",12),
new HealMove("Kokyu Ho",5,20),
new Move("Tenkan",10)
};
imagePath="aikido.png";
}
}

