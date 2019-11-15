package datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.Variable;


public class FrequentItemsetMiner{
    BooleanDatabase db;
 /*
 Écrire une classe FrequentItemsetMiner, 
 possédant un attribut de type BooleanDatabase 
 et une méthode frequentItemsets prenant en argument 
 une fréquence minimale, et retournant une Map 
 ayant pour clefs des itemsets (Set<Variable>) 
 et pour valeurs, leurs fréquences
 */
    public FrequentItemsetMiner(BooleanDatabase _db){
        db = _db;
    }

    public Map<Set<Variable>,Integer> frequentItemsets(int min_freq){
        Map<Set<Variable>,Integer> frequentItemsets = new HashMap<>();
        
        for(Map<Variable, String> transac : db.getTransactions() ) {
            List<Set<Variable>> patterns = new ArrayList<>();
            for (Variable var : db.getVars()){
                if(transac.get(var).equals("1")){   // 
                    addVarStep(patterns, var);
                }
            }
            for(Set<Variable> ite : patterns){
                int val = frequentItemsets.getOrDefault(ite, 0)+1;
                frequentItemsets.put(ite,val);
            }
        }
        
        final List<Set<Variable>> final_keys = new ArrayList<>(frequentItemsets.keySet());
        List<Set<Variable>> to_delete = new ArrayList<>();
        for(Set<Variable> key : frequentItemsets.keySet()) {
            //int index = final_keys.indexOf(key);
            if(frequentItemsets.get(key) < min_freq) {
                /*System.out.print("Removing ");
                for(Variable v : key) {
                    System.out.print(v.getName());
                }
                System.out.println();*/
                //frequentItemsets.remove(key);
                to_delete.add(key);
            }
        }
        to_delete.forEach((k) -> {
            frequentItemsets.remove(k);
        });
        return frequentItemsets;
    }
    // ajoute au pattern les combinaisons de Pattern avec la nouvelle var donnée
    // A
    // A, | AB, B
    // A, AB, B |, AC, ABC, BC, C
    // ...
    public static void addVarStep(List<Set<Variable>> pattern, Variable var){
    
        for(Set<Variable> subSet : new ArrayList<>(pattern)){
            Set<Variable> newSet = new HashSet<>(subSet);
            newSet.add(var);
            pattern.add(newSet);
        }
        pattern.add(Set.of(var));
    }
}