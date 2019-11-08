package planning;

import representations.Rule;

import java.util.Set;

public class Action{
    protected Set<Rule> rules_list;

    public Action(Set<Rule> rules_list){
        this.rules_list = rules_list; 

    }

    public Set<Rule> getRulesList(){
        return this.rules_list;
    }

    
}