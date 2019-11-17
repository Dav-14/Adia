package datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import representations.Variable;

public class FrequentItemsetMiner {

    static Map<
        Map.Entry<  Set<Set<Variable>>,
                    Variable>,
        List<Set<Variable>>
        > cache = new ConcurrentHashMap<>();

    BooleanDatabase db;

    public FrequentItemsetMiner(BooleanDatabase _db) {
        db = _db;
    }

    public Map<Set<Variable>, Integer> frequentItemsets(int min_freq) {
        Map<Set<Variable>, Integer> frequentItemsets = new HashMap<>();

        int i = 0;
        int total = db.getTransactions().size();
        for (Map<Variable, String> transac : db.getTransactions()) { // pour chaque transaction (ligne)
        //db.getTransactions().parallelStream().forEach((transac) -> {
            Set<Set<Variable>> patterns = new HashSet<>();
            for (Variable var : db.getVars()) { // pour chaque variable (colonne)
                if (transac.get(var).equals("1")) { // 
                        addVarStep(patterns, var);
                }
            }
            for (Set<Variable> ite : patterns) {
                int val = frequentItemsets.getOrDefault(ite, 0) + 1;
                /*ite.forEach((var) -> {
                    System.out.print(var.getName() + ",");
                });
                System.out.println();*/
                frequentItemsets.put(ite, val);
            }
            //System.out.println(" transac " + i + " / " + total);
            i++;
            
        }

        Set<Set<Variable>> to_delete = new HashSet<>();
        for (Set<Variable> key : frequentItemsets.keySet()) {
            if (frequentItemsets.get(key) < min_freq) {
                to_delete.add(key);
            }
        }

        to_delete.forEach((k) -> {
            frequentItemsets.remove(k);
        });
        to_delete = null;
        System.gc();

        // post-traitement des motifs fermés
        filterClosedPatterns(frequentItemsets);
        return frequentItemsets;
    }

    public static void addVarStep(Set<Set<Variable>> pattern, Variable var) {
        // ajoute au pattern les combinaisons de Pattern avec la nouvelle var donnée
        // A
        // A, | AB, B
        // A, AB, B |, AC, ABC, BC, C
        // ...

        // associer (pattern, var) -> computed

        List<Set<Variable>> tmp_sets = new ArrayList<>();
        Map.Entry<Set<Set<Variable>>, Variable> cache_key = Map.entry(pattern, var);
        if(cache.keySet().contains(cache_key)) {
            tmp_sets = cache.get(cache_key);
            pattern.addAll(tmp_sets);
        } else {
            
            for (Set<Variable> subSet : pattern) {
                Set<Variable> newSet = new HashSet<>(subSet);
                newSet.add(var);

                tmp_sets.add(newSet);
                //pattern.add(newSet);
            }
            tmp_sets.add(Set.of(var));
            pattern.addAll(tmp_sets);
            cache.put(cache_key, tmp_sets);
        }
    }

    public static void filterClosedPatterns(Map<Set<Variable>, Integer> frequentItemsets) {
        Set<Set<Variable>> toRemove = new HashSet<>();
        for (Set<Variable> setX : frequentItemsets.keySet()) {
            for (Set<Variable> setY : frequentItemsets.keySet()) {
                // on exclue les comparaion avec soi-meme
                if (!setX.equals(setY)) {
                    //on compare les fréquences
                    if (frequentItemsets.get(setX).equals(frequentItemsets.get(setY))) {
                        //on verifie l'inclusion de setX dans setY
                        if (patternIsIncluded(setX, setY)) {
                            toRemove.add(setX);
                        }
                    }
                }
            }
        }
        System.out.println(toRemove.size()+" motifs sur "+ frequentItemsets.size() +" on ete elimines");
        //elimination des motifs
        toRemove.forEach((itemSet) -> {
            frequentItemsets.remove(itemSet);
        });
    }

    // verifie si le pattern setX est inclus dans setY
    public static boolean patternIsIncluded(Set<Variable> setX, Set<Variable> SetY) {
        for (Variable varX : setX) {
            if (!SetY.contains(varX)) {
                return false;
            }
        }
        return true;
    }

}