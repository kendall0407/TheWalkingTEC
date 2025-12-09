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
public class Player2Strategy implements AttackStrategy{
    @Override
    public List<Move> generateAttack(List<MartialArt> arts){
        List<Move> all = new ArrayList<>();
        for(MartialArt a: arts) all.addAll(Arrays.asList(a.moves));
        int hits = 3 + new Random().nextInt(4);
        List<Move> out = new ArrayList<>();
        for(int i=0;i<hits;i++) out.add(all.get(new Random().nextInt(all.size())));
        return out;
        }
}
