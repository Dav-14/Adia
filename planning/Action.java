package planning;

import representations.RestrictedDomain;
import representations.Rule;

public class Action {
    protected Rule rule_impl;

    public Action(Set<RestrictedDomain> prem, Set<RestrictedDomain> conc) {
        rule_impl = new Rule(prem, conc);
    }

    public boolean is_applicable(State state) {
        
    }

    public boolean satisfies(State state) {
        
    }

    public State apply(State state) {

    }
}