package planning;

import representations.RestrictedDomain;
import representations.Rule;

import java.util.Set;

public class Action {
    protected Rule rule_impl;

    public Action(Set<RestrictedDomain> prem, Set<RestrictedDomain> conc) {

        rule_impl = new Rule(prem, conc);
    }

    public Boolean is_applicable(State state) {
        return null;
    }

    public Boolean satisfies(State state) {
        return null;
    }

    public State apply(State state) {
        return null;
    }
}