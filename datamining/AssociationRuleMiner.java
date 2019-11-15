package datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.Variable;
    //Extraction de règles d’association
public class AssociationRuleMiner{
    Map<Set<Variable>, Integer> frequentItemsets;

    public AssociationRuleMiner(Map<Set<Variable>, Integer> _fi){
        frequentItemsets = _fi;
    }

    public List<Rule> frequentAssociationRules(int min_freq, float min_conf){
        List<Rule> frequentRules = new ArrayList<>();

        frequentItemsets.keySet().forEach((itemSet) -> { 
            List<Set<Variable>> patterns = generatePatterns(itemSet);
            for(Set<Variable> patternL : patterns){
                for(Set<Variable> patternR : patterns){
                    if(isValid(patternL, patternR, itemSet)){
                        Rule rule = new Rule(patternL, patternR);
                        float f_xy = frequentItemsets.get(itemSet);
                        float f_x = frequentItemsets.get(patternL);
                        float conf = f_xy / f_x;
                        
                        rule.setConf(conf);
                        rule.setFreq(f_xy);
                        frequentRules.add(rule);
                    }
                }
            }
        });

        for(Rule rule : new ArrayList<>(frequentRules)){
            if(rule.getFreq()<min_freq && rule.getConf() < min_conf){
                frequentRules.remove(rule);
            }
        }


        return frequentRules;
    }

    public List<Set<Variable>> generatePatterns(Set<Variable> sourcePattern){
        List<Set<Variable>> patterns = new ArrayList<>();
        for(Variable var : sourcePattern){
            FrequentItemsetMiner.addVarStep(patterns, var);
        }
        return patterns;
    }

    public boolean isValid(Set<Variable> patternL ,Set<Variable> patternR, Set<Variable> sourcePattern){
        ///elimination des cas absurdes
        if( sourcePattern.size() < 2 || 
            patternL.size() == 0 ||
            patternR.size() == 0 ){ 
            return false; 
        }

        ////verification de l'union
        //donc on fait la bijection entre la concatenation patternL+patternR et sourcePattern
        Set<Variable> concat = new HashSet<>(patternL);
        concat.addAll(patternR);
        
        //si toutes les variables de sourcePattern sont dans concat
        for(Variable sourceVar : sourcePattern){ 
            if(!concat.contains(sourceVar)){
                return false;
            }
        }
        //et si toutes les variables de concat sont dans sourcePattern
        for(Variable concatVar : concat){
            if(!sourcePattern.contains(concatVar)){
                return false;
            }
        }
        //verification de l'intersection
        //on clone le sourcePattern et on supprime ses valeurs contenues dans patternL
        Set<Variable> sourcePatternClone = new HashSet<>(sourcePattern);  
        for(Variable lVar : patternL) { 
            if(sourcePatternClone.contains(lVar)) {
                sourcePatternClone.remove(lVar);
            }
        } 
        //on verifie bien que sourcePattern-patternL=patternR (donc intersection)
        for(Variable rVar : patternR){ 
            if(!sourcePatternClone.contains(rVar)){ 
                return false; 
            }
        }
        return true;
    }


}


