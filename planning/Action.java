package planning;

import representations.RestrictedDomain;
import representations.Rule;

import java.util.Set;

public class Action{
    protected Rule rule_impl;

    public Action(Set<RestrictedDomain> prem, Set<RestrictedDomain> conc){
        rule_impl = new Rule(prem, conc);
    }

    //Accesseurs
    public Rule getRuleImpl(){
        return this.rule_impl;
    }

    //Méthodes

    public State apply(Action act, State state){
        State state2 = state;
        if()
    }
}