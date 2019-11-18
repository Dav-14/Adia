package datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import representations.Variable;

public class FrequentItemsetMiner {
    //cache de addvarstep: stocke une association input->resultat de addvarstep
    //accélère addvarstep en évitant le recalculer un resultat si il est déjà en cache.
    static Map<
        Map.Entry<  Set<Set<Variable>>,
                    Variable>,
        List<Set<Variable>>> 
        cache = new ConcurrentHashMap<>();

    BooleanDatabase db;

    public FrequentItemsetMiner(BooleanDatabase _db) {
        db = _db;
    }

    /**
     * Calcule des itemsets qui apparaisent avec une fréquence superieure à min_freq
     * @param min_freq Fréquence minimale
     * @return Des itemsets avec leur fréquence respective
     */
    public Map<Set<Variable>, Integer> frequentItemsets(int min_freq) {
        Map<Set<Variable>, Integer> frequentItemsets = new HashMap<>();
        //Pour chaque transaction(donc ligne du csv)
        for (Map<Variable, String> transac : db.getTransactions()) {
            Set<Set<Variable>> patterns = new HashSet<>();
            for (Variable var : db.getVars()) {
                if (transac.get(var).equals("1")) {
                        //Si la variable vaut 1 dans la transaction on genere les patterns possibles
                        addVarStep(patterns, var);
                }
            }
            for (Set<Variable> ite : patterns) {
                //on increment la frequence du pattern ou on initialise à 1
                int freq = frequentItemsets.getOrDefault(ite, 0) + 1;
                //Si le pattern est nouveau on l'ajoute
                //sinon on update la frequence
                frequentItemsets.put(ite, freq);
            }
        }
        //supression des itemset avec une frequence < min_freq
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
    /**
     * Ajoute au pattern les combinaisons de Pattern avec la nouvelle var donnée
     * @param patterns Liste de patterns source à enrichir
     * @param var la variable à ajouter 
     */
    public static void addVarStep(Set<Set<Variable>> patterns, Variable var) {
        // exemple avec  ABC:
        //  pattern  |  var |   output
        //           |  A   |  A
        //  A        |  B   | A, AB, B
        //  A, AB, B |  C   | AC, ABC, BC, C
        //  ...
        List<Set<Variable>> tmp_sets = new ArrayList<>();
        Map.Entry<Set<Set<Variable>>, Variable> cache_key = Map.entry(patterns, var);
        //si on trouve l'input dans le cache, pas besoin de calculer,
        //on retourne le resultat du cache
        if(cache.keySet().contains(cache_key)) {
            tmp_sets = cache.get(cache_key);
            patterns.addAll(tmp_sets);
        } else {
            //sinon pour chaque pattern on genere un pattern+var
            for (Set<Variable> subSet : patterns) {
                Set<Variable> newSet = new HashSet<>(subSet);
                newSet.add(var);
                tmp_sets.add(newSet);
            }
            //et on ajoute la var seule
            tmp_sets.add(Set.of(var));
            //on ajoute le resulat au pattern et au cache
            patterns.addAll(tmp_sets);
            cache.put(cache_key, tmp_sets);
        }
    }
    /**
     * Filtrage par pattern fermé
     * @param frequentItemsets les itemsets à filtrer
     */
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
 /**
  * Vérifie si le pattern setX est inclus dans setY
  */
    public static boolean patternIsIncluded(Set<Variable> setX, Set<Variable> SetY) {
        for (Variable varX : setX) {
            if (!SetY.contains(varX)) {
                return false;
            }
        }
        return true;
    }

}