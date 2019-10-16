package ppc;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import examples.ExampleFactory;
import examples.ExampleImpl;
import representations.Variable;
import ppc.GeneralizedArcConsistency;
import representations.Constraint;
import representations.RestrictedDomain;

public class Main {
    public static void main(String[] args) {
        /*
        Tree<String> root = new Tree<>(ExampleImpl.angine.getName() + " " + "Oui");
        Tree<String> grippe = new Tree<>(ExampleImpl.grippe.getName() + " " + "Oui");
        Tree<String> allergie = new Tree<>(ExampleImpl.allergie.getName() + " " + "Moyenne");
        allergie.addChild(Tree.class.cast(allergie.clone()));
        grippe.addChild(Tree.class.cast(allergie.clone()));
        root.addChild(grippe);
        root.addChild(allergie);
        
        System.out.println(root);

        System.out.println("\n-----");
        allergie.getBros().forEach((b) -> {
            System.out.println(b);
        });

        System.out.println("\n-----");
        
        Tree<Character> A = new Tree<>('A');
        Tree<Character> B = new Tree<>('B');
        Tree<Character> C = new Tree<>('C');
        Tree<Character> D = new Tree<>('D');
        Tree<Character> E = new Tree<>('E');
        Tree<Character> F = new Tree<>('F');

        Tree<Character> rootchar = new Tree<>('R');
        D.addChild(E);
        A.addChild(D);
        C.addChild(F);
        rootchar.addChild(A);
        rootchar.addChild(B);
        rootchar.addChild(C);
    
        System.out.println("\n" + rootchar);
        System.out.println("\nE lvl: " + E.getLevel());

        rootchar.getChildsOfLevel(2).forEach(System.out::println);

        rootchar.removeChild(B);
        System.out.println("after B deletion\n" + rootchar);
        System.out.println("A next = " + A.getNextBro());
        System.out.println("C prev = " + C.getPrevBro());
        System.out.println("Full Path:\n");
        rootchar.visit((tree, lvl, path) -> {
            if(tree.hasChilds()) { return; }
            path.forEach((t) -> {
                System.out.print(t.getValue() + "/");
            });
            System.out.println(tree.getValue());
        });
        
        
        /*
        Backtracking backtrack = new Backtracking(new HashSet<>(List.of(ExampleImpl.angine,
                                                                ExampleImpl.grippe, ExampleImpl.oedeme, ExampleImpl.allergie)),
                                                    new HashSet<>(List.of(ExampleImpl.getRule1(),
                                                                ExampleImpl.getRule2()))
                                                                );*/
        //Backtracking backtrack = new Backtracking(new HashSet<>(ExampleImpl.getAllVars()), new HashSet<>());                                          
        //System.out.println("\n\n" + backtrack.getTree());
        /*ExampleImpl.getAllRules().forEach((rule) -> {
            System.out.println("--------RULE--------");
            Backtracking backtrack = new Backtracking(Set.of(rule));
            backtrack.allSolutions().forEach((map) -> {
                map.forEach((k, v) -> {
                    System.out.print(k.getName() + " = " + v + " ");
                });
                System.out.println();
            });
            System.out.println("\n");
        });*
        
        /*Map<Variable, String> sol = null;
        while((sol = backtrack.solution()) != null) {
            sol.forEach((k,v) -> {
                System.out.format("%s=%s/", k.getName(), v);
            });
            System.out.println();
        }*/

        Backtracking backtrack = new Backtracking(new HashSet(ExampleImpl.getAllRules().subList(0, 5)));
        //Backtracking backtrack = new Backtracking(new HashSet<>(ExampleImpl.getAllRules()));
        /*backtrack.allSolutions().forEach((map) -> {
                map.forEach((k, v) -> {
                    System.out.print(k.getName() + " = " + v + " ");
                });
                System.out.println();
            });
            */
        System.out.println(backtrack.allSolutions().size());


        
        List<Constraint> constraints = new ArrayList<>(ExampleImpl.getAllRules().subList(0, 9));
        for(Constraint c : constraints) {
            List<RestrictedDomain> rdoms = new ArrayList<>(c.getRDoms());
            int vals = rdoms.stream().map((rdom) -> {
                return rdom.getDomain().size();
            }).reduce(0, (x, y) -> {
                return x + y;
            });
            System.out.println(vals);
            System.out.println(GeneralizedArcConsistency.enforceArcConsistency(c, rdoms));

        }
    }
}