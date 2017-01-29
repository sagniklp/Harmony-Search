/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harmony.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Sagnik Das
 */
public class HarmonySearch {

    public static int HMS = 0;
    public static int IMP = 0;
    public static int nOBJ = 0;
    public static int nDEC_VARS = 0;
    public static int nCONS = 0;
    public static float HMCR;
    public static float PAR;
    public static float FW;
    public static List<Double> Max_dv = new ArrayList<Double>();
    public static List<Double> Min_dv = new ArrayList<Double>();
    public static ArrayList<Double> improvised_harmony = new ArrayList<Double>();
    public static Map<Integer, ArrayList<Double>> H_Mem = new HashMap<Integer, ArrayList<Double>>();
    public static ArrayList<Double> objective = new ArrayList<Double>();
    //public static final Set setmem = H_Mem.entrySet();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int index = -1;
        double max = 999999;
        File init_harmony = new File("C:\\Users\\Sagnik Das\\Desktop\\Harmony Search\\initial_harmony.out");
        File fin_harmony = new File("C:\\Users\\Sagnik Das\\Desktop\\Harmony Search\\final_harmony.out");
        File bst_harmony = new File("C:\\Users\\Sagnik Das\\Desktop\\Harmony Search\\best_harmony.out");
        File params = new File("C:\\Users\\Sagnik Das\\Desktop\\Harmony Search\\parameters.out");

        BufferedWriter i_h = new BufferedWriter(new FileWriter(init_harmony));
        i_h.write("# This File Contains The Input Harmonies # \n");
        BufferedWriter f_h = new BufferedWriter(new FileWriter(fin_harmony));
        f_h.write("# This File Contains The Final Harmonies # \n");
        BufferedWriter b_h = new BufferedWriter(new FileWriter(bst_harmony));
        b_h.write("# This File Contains The Best Harmony # \n");
        BufferedWriter param = new BufferedWriter(new FileWriter(params));
        param.write("# This File Contains Pre-Set Parameters # \n");

        Scanner user_input = new Scanner(System.in);

        System.out.println("\nEnter the size of the harmony memory(a multiple of 4):");
        HMS = user_input.nextInt();
        while (HMS < 4 || (HMS % 4) != 0) {
            System.out.println("\nWrong size of population entered=" + HMS + "..re-enter..");
            HMS = user_input.nextInt();
        }

        System.out.println("\nEnter No. of improvisations:");
        IMP = user_input.nextInt();
        while (IMP < 1) {
            System.out.println("\nWrong Number improvisations entered=" + IMP + "..re-enter..");
            IMP = user_input.nextInt();
        }

        System.out.println("\nEnter Harmony Memory Considering Rate(0<=HMCR<=1):");
        HMCR = user_input.nextFloat();
        while (HMCR > 1 || HMCR < 0) {
            System.out.println("\nWrong HMCR value entered=" + HMCR + "..re-enter..");
            HMCR = user_input.nextFloat();
        }

        System.out.println("\nEnter Pitch Adjusting Rate(0<=PAR<=1):");
        PAR = user_input.nextFloat();
        while (PAR > 1 || PAR < 0) {
            System.out.println("\nWrong PAR value entered=" + PAR + "..re-enter..");
            PAR = user_input.nextFloat();
        }

        System.out.println("\nEnter Fret Width(.01<=FW<=.1):");
        FW = user_input.nextFloat();
        while (FW > 1 || FW < 0) {
            System.out.println("\nWrong FW value entered=" + FW + "..re-enter..");
            FW = user_input.nextFloat();
        }

        System.out.println("\nEnter no. of Objective Functions:");
        nOBJ = user_input.nextInt();
        while (nOBJ < 1) {
            System.out.println("\nWrong no. of objectives entered=" + nOBJ + "..re-enter..");
            nOBJ = user_input.nextInt();
        }

        System.out.println("\nEnter no. of Decision Variables:");
        nDEC_VARS = user_input.nextInt();
        while (nDEC_VARS < 1) {
            System.out.println("\nWrong no. of Decision variables entered=" + nDEC_VARS + "..re-enter..");
            nDEC_VARS = user_input.nextInt();
        }

        System.out.println("\nEnter no. of Contraints:");
        nCONS = user_input.nextInt();
        while (nCONS < 0) {
            System.out.println("Wrong no. of Constraints=" + nCONS + "..re-enter..\n");
            nCONS = user_input.nextInt();
        }

        if (nDEC_VARS != 0) {
            for (int i = 0; i < nDEC_VARS; i++) {
                System.out.println("Enter max value for decision variable " + (i + 1));
                Max_dv.add(user_input.nextDouble());
                System.out.println("Enter min value for decision variable " + (i + 1));
                Min_dv.add(user_input.nextDouble());
                if (Max_dv.get(i) <= Min_dv.get(i)) {
                    System.out.println("Wrong limits entered..exiting..\n");
                    System.exit(0);
                }
            }
        }

        System.out.println("\n ##### Input data successfully entered, now performing initialization ##### \n");
        param.write("\n##Size of the Harmony Memory= " + HMS);
        param.write("\n##Maximum no. of improvisations= " + IMP);
        param.write("\n##Harmony Memory Considering Rate= " + HMCR);
        param.write("\n##Pitch Adjusting Rate= " + PAR);
        param.write("\n##Fret Width= " + FW);
        param.write("\n##Number Of Objectives= " + nOBJ);
        param.write("\n##Number Of Constraints= " + nCONS);
        param.write("\n##Number Of Decision Variables= " + nDEC_VARS);

        for (int i = 0; i < nDEC_VARS; i++) {
            param.write("\n__Max value for decision variable " + (i + 1) + "=" + Max_dv.get(i));
            param.write("\n__Min value for decision variable " + (i + 1) + "=" + Min_dv.get(i));
        }
        harmony_memory_initialization();

        for (int i = 0; i < HMS; i++) {
            double obj = objective.get(i);
            i_h.write("\n  " + i + ": " + obj + ": " + H_Mem.get(i).toString());
        }
        System.out.println("\n ##### Successfully initialized Harmony Memory, now performing improvisation & Updation ##### \n");
        for (int i = 0; i < IMP; i++) {
            harmony_improvisation();
            harmony_updation();
        }
        for (int i = 0; i < HMS; i++) {
            double obj = objective.get(i);
            f_h.write("\n  " + i + ": " + obj + ": " + H_Mem.get(i).toString());
        }

        for (int i = 0; i < HMS; i++) {
            double obj = objective.get(i);
            if (obj < max) {
                max = obj;
                index = i;
            }
        }
        b_h.write("\n "+objective.get(index)+": "+H_Mem.get(index).toString());
System.out.println("\n ##### Successfully finished Searching & outputs are written ##### \n");
        i_h.flush();
        i_h.close();
        f_h.flush();
        f_h.close();
        b_h.flush();
        b_h.close();
        param.flush();
        param.close();
    }

    public static void harmony_memory_initialization() {
        int nRandom_H = 4 * HMS;
        double obj = 0;
        Map<Double, ArrayList<Double>> random_hms = new HashMap<Double, ArrayList<Double>>();

        for (int i = 0; i < nRandom_H; i++) {
            ArrayList<Double> rand_values = new ArrayList<Double>();
            for (int j = 0; j < nDEC_VARS; j++) {
                rand_values.add(Randomx.randomInRange(Min_dv.get(j), Max_dv.get(j)));
            }
            obj = Problemdef.test_problem(rand_values);
            //rand_values.add(obj);
            random_hms.put(obj, rand_values);
            //System.out.println("\n___"+obj+": "+ random_hms.get(obj).toString());
        }
        //System.out.println("\n AAA" + random_hms.get(obj).toString());
        //System.out.println("\n AAA" + random_hms.values().toString());
        Map<Double, ArrayList<Double>> sorted_random_hms = new TreeMap<>(random_hms);
        //System.out.println("\n VVV" + sorted_random_hms.get(obj).toString());
        Set set = sorted_random_hms.entrySet();
        Iterator it = set.iterator();
        for (int i = 0; i < HMS; i++) {
            ArrayList<Double> rand_values = new ArrayList<Double>();
            Map.Entry me = (Map.Entry) it.next();
            obj = Double.parseDouble(me.getKey().toString());
            objective.add(obj);
            rand_values = random_hms.get(obj);
            //rand_values.add(obj);
            H_Mem.put(i, rand_values);
            //System.out.println("MMMMMM"+obj+": "+H_Mem.get(obj).toString());
        }

    }

    public static void harmony_improvisation() {
        double prob = 0;
        ArrayList<Double> improvised_harmony_local = new ArrayList<Double>();
        prob = Randomx.randomprob();
        if (prob > HMCR) {
            for (int j = 0; j < nDEC_VARS; j++) {
                improvised_harmony_local.add(Randomx.randomInRange(Min_dv.get(j), Max_dv.get(j)));
            }
            //System.out.println("1-HMCR"+improvised_harmony_copy.toString());
        } else if (prob <= HMCR) {
            double prob1 = Randomx.randomprob();
            int index = Randomx.randomindex();
            if (index == 0) {
                improvised_harmony_local = H_Mem.get(index);
            } else {
                improvised_harmony_local = H_Mem.get(index - 1);
            }
            //System.out.println("XXXXXX" + improvised_harmony.toString());
            if (prob1 <= PAR) {
                double pitch_adj;
                double uni_random;
                double total_value_range;

                for (int i = 0; i < nDEC_VARS; i++) {
                    uni_random = Randomx.randomprob();
                    total_value_range = Max_dv.get(i) - Min_dv.get(i);
                    pitch_adj = uni_random * total_value_range * FW;
                    if ((improvised_harmony_local.get(i) + pitch_adj) > Max_dv.get(i) && improvised_harmony_local.get(i) - pitch_adj >= Min_dv.get(i)) {
                        improvised_harmony_local.set(i, improvised_harmony_local.get(i) - pitch_adj);

                    } else if ((improvised_harmony_local.get(i) + pitch_adj) <= Max_dv.get(i)) {
                        improvised_harmony_local.set(i, improvised_harmony_local.get(i) + pitch_adj);
                    }

                }
                //System.out.println("HMCRPAR"+improvised_harmony_copy.toString());
                //System.out.println("");
            }
        }

        //System.out.println("XXXX"+ improvised_harmony_local.toString());
        improvised_harmony = improvised_harmony_local;
        //System.out.println("ii"+improvised_harmony.toString());
    }

    public static void harmony_updation() {
        double obj;
        double max = 0;
        ArrayList<Double> improvised_harmony_local = new ArrayList<Double>();
        int index_to_remove = -1;
        boolean accedentaling_flag = true;
        //System.out.println("IH___"+improvised_harmony.toString());
        obj = Problemdef.test_problem(improvised_harmony);
        improvised_harmony_local = improvised_harmony;
        //System.out.println("OBJ" + objective.toString());
        for (int i = 0; i < HMS; i++) {
            double key = objective.get(i);
            if (key >= max) {
                max = key;
                index_to_remove = i;
            }
            if (obj > key) {
                accedentaling_flag = false;
            }
        }
        //System.out.println("INDEX" + index_to_remove);
        //System.out.println("RRRRRR"+H_Mem.size());
        if (accedentaling_flag) {
            double pitch_adj;
            double uni_random;
            double total_value_range;
            for (int i = 0; i < nDEC_VARS; i++) {
                uni_random = Randomx.randomprob();
                total_value_range = Max_dv.get(i) - Min_dv.get(i);
                pitch_adj = uni_random * total_value_range * FW;
                if ((improvised_harmony_local.get(i) + pitch_adj) > Max_dv.get(i) && improvised_harmony_local.get(i) - pitch_adj >= Min_dv.get(i)) {
                    improvised_harmony_local.set(i, improvised_harmony_local.get(i) - pitch_adj);

                } else if ((improvised_harmony_local.get(i) + pitch_adj) <= Max_dv.get(i)) {
                    improvised_harmony_local.set(i, improvised_harmony_local.get(i) + pitch_adj);

                }
            }
            obj = Problemdef.test_problem(improvised_harmony_local);
        }
        if (index_to_remove != -1) {
            H_Mem.remove(index_to_remove);
            H_Mem.put(index_to_remove, improvised_harmony_local);
            for (int i = 0; i < HMS; i++) {
                obj = Problemdef.test_problem(H_Mem.get(i));
                objective.set(i, obj);
            }
        }
    }
}
