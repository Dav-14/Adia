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
        // Pour chaque rule, execute le backtrack et affiche les assignations valides
        List<Constraint> constraints = new ArrayList<>(ExampleImpl.getAllRules().subList(0, 9));
        for(Constraint c : constraints) {
            Backtracking bt = new Backtracking(Set.of(c));
            bt.allSolutions().forEach((map) -> {
                map.forEach((k, v) -> {
                    System.out.print(k.getName() + " = " + v + " ");
                });
                System.out.println();
            });
            System.out.println("-------");
        }
    }
}