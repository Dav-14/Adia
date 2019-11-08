package planning;

import representations.*;

import java.util.HashMap;
import java.util.Map;

public class State extends HashMap<Variable,String>{

    public Boolean satisfies(State partial_state){
        for(Variable var: partial_state.keySet()){
            if( !( this.containsKey(var) ) || partial_state.get(var)!=this.get(var) ){
                return false;
            }
        }
        return true;
    }

    public static State premisseRule_to_State(Rule rule){
        State rule_precondition = new State();
        for(RestrictedDomain rd: rule.getPremisse()){
            Variable var = rd.getVariable();
            for(String valeur: rd.getDomain()){
                rule_precondition.put(var,valeur);
            }
        }
        return rule_precondition;
    }

    public Boolean is_applicable(Action action){
        for(Rule rule: action){
            State rule_precondition = premisseRule_to_State(rule);
            if(this.satisfies(rule_precondition)){
                return true;
            }
        }
        return false;
    }

    public static State conclusionRule_to_State(Rule rule){
        State rule_conclusion = new State();
        for(RestrictedDomain rd: rule.getConclusion()){
            Variable var = rd.getVariable();
            for(String valeur: rd.getDomain()){
                rule_conclusion.put(var,valeur);
            }
        }
        return rule_conclusion;
    }
    
    public State apply(Action action){
        State state2 = this;
        if(this.is_applicable(action)){
            for(Rule rule: action){
                State rule_precondition = premisseRule_to_State(rule);
                if(this.satisfies(rule_precondition)){
                    for(RestrictedDomain rd: rule.getConclusion()){
                        Variable var = rd.getVariable();
                        for(String valeur: rd.getDomain()){
                            state2.put(var,valeur);
                        }
                    }
                }
            }
        }
        return state2;
    }
}