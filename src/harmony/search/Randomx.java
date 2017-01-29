/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harmony.search;

import java.util.Random;
/**
 *
 * @author Sagnik Das
 */
public class Randomx {

    protected static Random random= new Random();

    public static double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble()* range;
        double value = scaled + min;
        return value;
    }
    
    public static double randomprob() {
        double max=1,min=0;
        double range = max - min;
        double scaled = random.nextDouble()* range;
        double value = scaled + min;
        return value;
    }
    
    public static int randomindex(){
        int s=Math.toIntExact(Math.round(random.nextDouble()*HarmonySearch.HMS));
        return s;
    }
}
