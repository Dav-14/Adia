package datamining;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.Variable;

/** Extraction de règles d’association */
public class AssociationRuleMiner {
    Map<Set<Variable>, Integer> frequentItemsets;

    public AssociationRuleMiner(Map<Set<Variable>, Integer> _fi) {
        frequentItemsets = _fi;
    }
    /**
     * Calcule les AssociationRules fréquentes
     * @param min_freq fréquence minimal du filtrage
     * @param min_conf confiance minimal du filtrage
     * @return Une liste de rules valides qui satifient min_freq et min_conf
     */
    public List<Rule> frequentAssociationRules(int min_freq, float min_conf) {
        List<Rule> frequentRules = new ArrayList<>();
        // Pour chaque itemset fréquent
        frequentItemsets.keySet().forEach((itemSet) -> {
            //generation des patterns de l'itemset
            Set<Set<Variable>> patterns = generatePatterns(itemSet);
            //on genere chaque combinaison de pattern
            for (Set<Variable> patternL : patterns) {
                for (Set<Variable> patternR : patterns) {
                    //si la combinaison est valide
                    if (isValid(patternL, patternR, itemSet)) {
                        //on crée une rule à partir de la combinaison
                        Rule rule = new Rule(patternL, patternR);
                        float f_xy = frequentItemsets.get(itemSet);
                        float f_x = frequentItemsets.get(patternL)==null?f_xy:frequentItemsets.get(patternL);
                        float conf = f_xy / f_x;

                        //on set la frequence, conf etc
                        rule.setConf(conf);
                        rule.setFreq(f_xy);
                        frequentRules.add(rule);
                    }
                }
            }

        });

        // filtrage des rules générées par min_freq et min_conf
        for (Rule rule : new ArrayList<>(frequentRules)) {
            if (rule.getFreq() < min_freq || rule.getConf() < min_conf) {
                frequentRules.remove(rule);
            }
        }

        return frequentRules;
    }
    /**
     * Génère toutes les combinaisons possibles de patterns à partir d'un pattern
     * @param sourcePattern Le pattern à partir duquel on veut générer des combinaisons
     * @return retourne tous les patterns générés
     */
    //exemple: ABC -> A, AB, B, AC, ABC, BC, C
    public Set<Set<Variable>> generatePatterns(Set<Variable> sourcePattern) {
        Set<Set<Variable>> patterns = new HashSet<>();
        for (Variable var : sourcePattern) {
            FrequentItemsetMiner.addVarStep(patterns, var);
        }
        return patterns;
    }
    /**
     * Vérifie la validité de la pseudo-rule (union et intersection)
     * @param patternL pattern de gauche de la pseudo-rule
     * @param patternR pattern de droite de la pseudo-rule
     * @param sourcePattern pattern à l'origine  de la generation de patternL/R
     * @return true si la pseudo-rule est valide, false si elle ne l'est pas
     * on parle de pseudo rule car pas un objet Rule(pas encore de confiance/frequence)
     */
    public boolean isValid(Set<Variable> patternL, Set<Variable> patternR, Set<Variable> sourcePattern) {
        /// elimination des cas absurdes
        if (sourcePattern.size() < 2 || patternL.size() == 0 || patternR.size() == 0) {
            return false;
        }
        //// verification de l'union
        // donc on fait la bijection entre la concatenation patternL+patternR et
        //// sourcePattern
        Set<Variable> concat = new HashSet<>(patternL);
        concat.addAll(patternR);

        // si toutes les variables de sourcePattern sont dans concat
        for (Variable sourceVar : sourcePattern) {
            if (!concat.contains(sourceVar)) {
                return false;
            }
        }
        // et si toutes les variables de concat sont dans sourcePattern
        for (Variable concatVar : concat) {
            if (!sourcePattern.contains(concatVar)) {
                return false;
            }
        }
        // verification de l'intersection
        // on clone le sourcePattern et on supprime ses valeurs contenues dans patternL
        Set<Variable> sourcePatternClone = new HashSet<>(sourcePattern);
        for (Variable lVar : patternL) {
            if (sourcePatternClone.contains(lVar)) {
                sourcePatternClone.remove(lVar);
            }
        }
        // on verifie bien que sourcePattern-patternL=patternR (donc intersection)
        for (Variable rVar : patternR) {
            if (!sourcePatternClone.contains(rVar)) {
                return false;
            }
        }
        return true;
    }

}
