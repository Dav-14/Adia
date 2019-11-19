package ppc;

import representations.Variable;
import representations.Constraint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Backtracking {
    protected Set<Variable> variables;
    protected Set<Constraint>     constraints;

    public Backtracking(Set<Constraint> consts) {
        this.constraints = consts;
        this.variables = new HashSet<>();

        this.constraints.stream().forEach((set_c) -> {
            this.variables.addAll(set_c.getScope());
        });
    }

    /**
     * Initie la récursion
     * @return Liste des assignations valides
     */
    public Set<Map<Variable, String>> allSolutions() {
        // je commence avec une assignatiation partielle vide
        // je considère que toutes les variables sont non assignées pour l'instant 
        // je garde une trace de toute solution que je recontre durant ma recherche
        Set<Map<Variable, String>> res = new HashSet<>();
        this.solutions(new HashMap<Variable, String>(), new ArrayDeque<>(this.variables), res);
        return res;
    }

    /**
     * Assigne récursivement les variables , et ajoute l'assignation à acc, si elle satisfait les contraintes
     * @param partialAss
     * @param unassVars
     * @param acc
     */
    protected void solutions(Map<Variable, String> partialAss, Deque<Variable> unassVars, Set<Map<Variable, String>> acc) {
        boolean complete = true;
        for(Variable v : this.variables) {
            if(!partialAss.containsKey(v)) {
                complete = false;
                break;
            }
        }
        if(complete) { // est-ce l'assignation partielle est complète ? Je ferai quoi si oui ?
            boolean satisfies = true; 
            for(Constraint c : this.constraints) {
                if(!c.isSatisfiedBy(partialAss)) {
                    satisfies = false;
                    break;
                }
            }
            if(satisfies) { // si elle satisfait on met la copie dans la liste
                acc.add(new HashMap<>(partialAss)); // by-copy
            }
        } else { // Sinon, j'ai du travail à faire !
            for(Variable v : unassVars) { // faudrait construire pas à pas ma solution partielle
                ArrayDeque<Variable> clone_unassVars = new ArrayDeque<>(unassVars); // by-copy
                clone_unassVars.remove(v); // remove the variable from unnassigned variables

                for(String val : v.getDomain()) { // il faudrait vérifier la consistance (filtrage, vérifiabilité, satisfiabilité), 
                    // check les contraintes
                    // filtrage
                    
                    Map<Variable, String> clone_ass = new HashMap<>(partialAss);
                    clone_ass.put(v, val); // 
                    
                    this.solutions(clone_ass, clone_unassVars, acc); // je peux continuer en faisant les même instrcution
                } // d'autre valeurs à essayer 
            } // d'autre variables  à essayer 
        }
    }
}