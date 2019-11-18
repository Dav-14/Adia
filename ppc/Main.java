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
            Backtracking bt = new Backtracking(Set.of(c));
            bt.allSolutions().forEach((map) -> {
                map.forEach((k, v) -> {
                    System.out.print(k.getName() + " = " + v + " ");
                });
                System.out.println();
            });
            //System.out.println(GeneralizedArcConsistency.enforceArcConsistency(c, rdoms));

        }
    }
}