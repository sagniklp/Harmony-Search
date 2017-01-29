/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harmony.search;

import java.util.ArrayList;

/**
 *
 * @author Sagnik Das
 */
public class Problemdef {

    public static double test_problem(ArrayList<Double> var_values) {
        Double dec_var_values[] = new Double[var_values.size()];
        var_values.toArray(dec_var_values);
        /*problem definition*/
        double obj=Math.pow((dec_var_values[0]-2),2)+Math.pow((dec_var_values[1]-4),2);
        return obj;
    }

}
