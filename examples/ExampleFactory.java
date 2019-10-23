package examples;

import representations.IncompatibilityConstraint;
import representations.RestrictedDomain;
import representations.Variable;

import java.util.Arrays;
import java.util.HashSet;

public class ExampleFactory {
    private ExampleFactory() {

    }

    public static ExampleFactory newInstance() {
        return new ExampleFactory();
    }

    public RuleBuilder      newRuleBuilder() {
        return new RuleBuilder();
    }

    public MapBuilder       newMapBuilder() {
        return new MapBuilder();
    }

    public IncompatibilityConstraint createIncompatibilityConstraint(RestrictedDomain... rds) {
        if(rds.length == 0) { { System.out.println("WARNING EMPTY SET");} }
        
        return new IncompatibilityConstraint(new HashSet<>(Arrays.asList(rds)));
    }

    public Variable         createVariable(String name, String... vals) {
        if(vals.length == 0) { System.out.println("WARNING EMPTY DOMAIN");}

        return new Variable(name, new HashSet<>(Arrays.asList(vals)));
    }

    public RestrictedDomain createRestrictedDomain(Variable var, String... vals) {
        if(vals.length == 0) { System.out.println("WARNING EMPTY DOMAIN"); }

        return new RestrictedDomain(var, new HashSet<>(Arrays.asList(vals)));
    }
}