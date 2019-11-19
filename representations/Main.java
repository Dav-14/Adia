package representations;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // TODO: tests
        Variable angine     = new Variable("Angine",        new HashSet<>(List.of("Oui", "Non")));
        Variable fievre     = new Variable("Fievre",        new HashSet<>(List.of("Basse", "Moyenne", "Haute")));
        Variable hypothermie= new Variable("Hypothermie",   new HashSet<>(List.of("Oui", "Non")));

        Set<RestrictedDomain> premisse      = new HashSet<>(List.of(new RestrictedDomain(angine, new HashSet<>(List.of("Oui")))));
        Set<RestrictedDomain> conclusion    = new HashSet<>(List.of(new RestrictedDomain(fievre, new HashSet<>(List.of("Moyenne","Haute")))));
        Rule angine_fievre = new Rule(premisse, conclusion);
        IncompatibilityConstraint ic = new IncompatibilityConstraint(new HashSet<>(List.of(
                                                                            new RestrictedDomain(fievre, new HashSet<>(List.of("Moyenne", "Haute"))),
                                                                            new RestrictedDomain(hypothermie, new HashSet<>(List.of("Oui")))
        )));
        
        Map<Variable, String> patient = new HashMap<Variable, String>();
        patient.put(angine, "Oui");
        patient.put(fievre, "Moyenne");

        Map<Variable, String> patient2 = new HashMap<Variable, String>();
        patient2.put(angine, "Oui");
        patient2.put(fievre, "Haute");

        Map<Variable, String> patient3 = new HashMap<Variable, String>();
        patient3.put(angine, "Oui");
        patient3.put(fievre, "Basse");

        Map<Variable, String> patient4 = new HashMap<Variable, String>();
        patient4.put(hypothermie, "Oui");
        patient4.put(fievre, "Moyenne");

        Map<Variable, String> patient5 = new HashMap<Variable, String>();
        patient5.put(hypothermie, "Oui");
        patient5.put(fievre, "Basse");

        if(angine_fievre.isSatisfiedBy(patient)) {
            System.out.println("satisfied");
        }
        if(angine_fievre.isSatisfiedBy(patient2)) {
            System.out.println("satisfied");
        }
        if(!angine_fievre.isSatisfiedBy(patient3)) {
            System.out.println("pas satisfied");
        }

        if(!ic.isSatisfiedBy(patient4)) {
            System.out.println("pas satisfied");
        }
        if(ic.isSatisfiedBy(patient5)) {
            System.out.println("satisfied");
        }
    }
}