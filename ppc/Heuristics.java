package ppc;

import representations.*;
import java.util.List;
import java.util.Set;

public class Heuristics {

    /**
     * retourne la variable contenue dans la liste de règles rules ayant la plus grande ou petite grandeur de domaine
     * @param rules
     * @param lePlusGrand
     * @return varMinMax
     */
    private static Variable cvDom(List<Rule> rules, boolean lePlusGrand){
        int maxMin;
        Variable varMinMax = null;
        if(lePlusGrand = true){
            maxMin = 0;
        }
        else{ maxMin = Integer.MAX_VALUE; }
        for(Rule r : rules){
            Set<Variable> listVar = r.getScope();
            for(Variable v : listVar){
                if(lePlusGrand = true){
                    if(v.getDomain().size() > maxMin){
                        maxMin = v.getDomain().size();
                        varMinMax = v;
                    }
                }
                else{
                    if(v.getDomain().size() < maxMin){
                        maxMin = v.getDomain().size();
                        varMinMax = v;
                    }
                }
            }
        }
        return varMinMax;
    }

    /**
     * retourne la variable contenue dans la liste de règles rules ayant la plus grande grandeur de domaine
     * @param rules
     * @param lePlusGrand
     */
    public static Variable cvDomPlusGrand(List<Rule> rules){
        return cvDom(rules, true);
    }

    /**
     * retourne la variable contenue dans la liste de règles rules ayant la plus petite grandeur de domaine
     * @param rules
     * @param lePlusGrand
     */
    public static Variable cvDomPlusPetit(List<Rule> rules){
        return cvDom(rules, false);
    }

    /**
     * retourne la variable contenue dans la liste de variables ayant la plus grande ou petite grandeur de domaine
     * @param rules
     * @param lePlusGrand
     * @return varMinMax
     */
    public static Variable cvDomVar(List<Variable> variables, boolean lePlusGrand){
        int maxMin;
        Variable varMinMax = null;
        if(lePlusGrand = true){
            maxMin = 0;
        }
        else{ maxMin = Integer.MAX_VALUE; }
        for(Variable v : variables){
            if(lePlusGrand = true){
                if(v.getDomain().size() > maxMin){
                    maxMin = v.getDomain().size();
                    varMinMax = v;
                }
            }
            else{
                if(v.getDomain().size() < maxMin){
                    maxMin = v.getDomain().size();
                    varMinMax = v;
                }
            }
        }
        return varMinMax;
    }

    /**
     * retourne la variable contenue dans la liste de règles rules ayant la plus grande grandeur de domaine
     * @param rules
     * @param lePlusGrand
     */
    public static Variable cvDomVarPlusGrand(List<Variable> variables){
        return cvDomVar(variables, true);
    }

    /**
     * retourne la variable contenue dans la liste de règles rules ayant la plus petite grandeur de domaine
     * @param rules
     * @param lePlusGrand
     */
    public static Variable cvDomVarPlusPetit(List<Variable> variables){
        return cvDomVar(variables, false);
    }


    private static Variable chooseVarsLevel(List<Rule> rules, boolean level){
        HashMap<Variable,Integer> map = new HashMap<>();

        for (Rule obj: rules) {
            Set<Variable> scope = obj.getScope();
            for (Variable var : scope){
                if (map.containsKey(var)){
                    map.put(var,map.get(var)+1);
                }
                map.putIfAbsent(var,1);
            }
        }
        Variable state = null;
        Integer comparable;
        if (level){
            comparable = Integer.MIN_VALUE;
            for (Variable var: map.keySet())  {
                if (map.get(var) >= comparable){
                    state = var;
                    comparable = map.get(var);
                }
            }
        }else {
            comparable = Integer.MAX_VALUE;
            for (Variable var: map.keySet())  {
                if (map.get(var) <= comparable){
                    state = var;
                    comparable = map.get(var);
                }
            }
        }


        return state;
    }

    public static Variable cHighVar(List<Rule> rules){
        return chooseVarsLevel(rules,true);
    }

    public static Variable cLowerVar(List<Rule> rules){
        return chooseVarsLevel(rules,false);
    }


    private static Variable chooseValue(List<Rule> rules, int index){
        List<Variable> scope = ExampleImpl.getAllScope(rules);
        if (index >= scope.size()) throw new IndexOutOfBoundsException();
        else {
            return scope.get(index);
        }
    }

    public static String randomValueDomain(Variable var){
        Set<String> domain = var.getDomain();
        Random rand = new Random();
        return (String) domain.toArray()[rand.nextInt(domain.size())];
    }
}
