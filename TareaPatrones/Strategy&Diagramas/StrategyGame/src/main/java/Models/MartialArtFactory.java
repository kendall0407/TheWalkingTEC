/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;



import java.util.*;
import java.util.function.Supplier;

/**
 *
 * @author josed
 */
public class MartialArtFactory{
    private static final List<Supplier<MartialArt>> arts = Arrays.asList(
        Karate::new, Taekwondo::new, MuayThai::new, KungFu::new,
        Boxing::new, Judo::new, Capoeira::new, KravMaga::new,
        Aikido::new, Sambo::new
    );


    public static MartialArt random(){
        return arts.get(new Random().nextInt(arts.size())).get();
    }


    public static List<MartialArt> randomThree(){
        List<Supplier<MartialArt>> copy = new ArrayList<>(arts);
        Collections.shuffle(copy);
        return Arrays.asList(
        copy.get(0).get(),
        copy.get(1).get(),
        copy.get(2).get()
        );
    }
}
