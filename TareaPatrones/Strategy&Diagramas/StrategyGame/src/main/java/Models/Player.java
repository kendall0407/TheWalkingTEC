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
public class Player{
    public String name;
    public int hp=200;
    public List<MartialArt> arts = MartialArtFactory.randomThree();
    public AttackStrategy strategy;
    public List<String> log = new ArrayList<>();
    
    public boolean sent = false;
    public List<Move> sentCombo = null;

    public Player(String n,AttackStrategy s){ 
        name=n; 
        strategy=s; 
    }

    public List<Move> generateAttack(){ 
        return strategy.generateAttack(arts); 
    }

    public void receive(List<Move> moves){
        for(Move m: moves){
            hp -= m.getDamage();
            hp -= m.bonusDamage();
            hp += m.heal();
            hp -= m.selfDamage();
            log.add(name+" recibió "+m.getName()+" ("+m.getDamage()+")");
        }
    }

    public boolean dead(){ 
        return hp<=0; 
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setSentCombo(List<Move> sentCombo) {
        this.sentCombo = sentCombo;
    }
    
    

    public boolean isSent() {
        return sent;
    }

    public List<Move> getSentCombo() {
        return sentCombo;
    }
    
    
}
