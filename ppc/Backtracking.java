package ppc;

import representations.Variable;
import representations.Constraint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Backtracking {
    protected Set<Variable> variables;
    protected Set<Constraint>     constraints;

    //protected List<Map<Variable, String>>   solutions;

    protected int sol_index = 0;

    public Backtracking(Set<Constraint> consts) {
        this.constraints = consts;
        this.variables = new HashSet<>();

        this.constraints.stream().forEach((set_c) -> {
            this.variables.addAll(set_c.getScope());
        });

        // debug
        /*
        System.out.println("scope: ");
        this.variables.forEach((v) -> {
            System.out.print(v.getName() + " ");
        });
        System.out.println();
        */
        //this.variables.stream().distinct().collect(Collectors.toSet());
    }

    public Set<Map<Variable, String>> allSolutions() {
        // je commence avec une assignatiation partielle vide
        // je considère que toutes les variables sont non assignées pour l'instant 
        // je garde une trace de toute solution que je recontre durant ma recherche
        Set<Map<Variable, String>> res = new HashSet<>();
        this.solutions(new HashMap<Variable, String>(), new ArrayDeque<>(this.variables), res);
        return res;
    }

    protected void solutions(Map<Variable, String> partialAss, Deque<Variable> unassVars, Set<Map<Variable, String>> acc) {

        //if(unassVars.isEmpty()) { return; }
        // est-ce l'assignation partielle est complète ? Je ferai quoi si oui ?
        boolean complete = true;
        for(Variable v : this.variables) {
            if(!partialAss.containsKey(v)) {
                complete = false;
                break;
            }
        }
        if(complete) {
            // debug
            /*
            System.out.println("found complete assignement:");
            partialAss.forEach((k, v) -> {
                System.out.print(k.getName() + " = " + v);
            });
            System.out.println("\n");
            */
            boolean satisfies = true; 
            for(Constraint c : this.constraints) {
                if(!c.isSatisfiedBy(partialAss)) {
                    satisfies = false;
                    break;
                }
            }
            if(satisfies /*&& !acc.contains(new HashMap<>(partialAss))*/) {
                acc.add(new HashMap<>(partialAss)); // by-copy
            }
        } else { // Sinon, j'ai du travail à faire !
            for(Variable v : unassVars) { // faudrait construire pas à pas ma solution partielle
                ArrayDeque<Variable> clone_unassVars = new ArrayDeque<>(unassVars); // by-copy
                clone_unassVars.remove(v); // remove the variable from unnassigned variables

                for(String val : v.getDomain()) { // il faudrait vérifier la consistance (filtrage, vérifiabilité, satisfiabilité), 
                    // check les contraintes
                    // filtrage
                    // et let's go
                    
                    Map<Variable, String> clone_ass = new HashMap<>(partialAss);
                    clone_ass.put(v, val); // 
                    
                    
                    this.solutions(clone_ass, clone_unassVars, acc); // je peux continuer en faisant les même instrcution
                
                    // d'autre valeurs à essayer 
                }
                // d'autre variables  à essayer 
            }
        }
    }
}