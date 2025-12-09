/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Models;

import java.util.List;



/**
 *
 * @author josed
 */
public interface AttackStrategy {
    List<Move> generateAttack(List<MartialArt> arts);
}
