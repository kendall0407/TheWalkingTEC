/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author josed
 */
public class Capoeira extends MartialArt{
    public Capoeira(){
name="Capoeira";
moves=new Move[]{
new Move("Meia Lua",20),
new ExtraDamageMove("Armada",22,6),
new Move("Rabo de Arraia",25)
};
imagePath="capoeira.png";
}
}
