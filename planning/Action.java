package planning;

import representations.RestrictedDomain;
import representations.Rule;

import java.util.Set;

public class Action{
    protected Rule rule_impl;

    public Action(Set<RestrictedDomain> precondition, Set<RestrictedDomain> effets){
        rule_impl = new Rule(precondition, effets);
    }

    //Accesseurs
    public Rule getRuleImpl(){
        return this.rule_impl;
    }

    //Méthodes
    
}