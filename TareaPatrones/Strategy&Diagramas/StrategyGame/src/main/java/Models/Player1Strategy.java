/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.*;

/**
 *
 * @author josed
 */
public class Player1Strategy implements AttackStrategy{
    
    @Override
    public List<Move> generateAttack(List<MartialArt> arts){
        MartialArt chosen = arts.get(new Random().nextInt(arts.size()));
        int hits = 3 + new Random().nextInt(4);
        List<Move> out = new ArrayList<>();
        for(int i=0;i<hits;i++){
            Move m = chosen.moves[new Random().nextInt(3)];
            out.add(m);
        }
    return out;
    }
}
