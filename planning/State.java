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

    public Boolean is_applicable(Action action){
        for(Rule rule: action){
            State rule_precondition;
            for(RestrictedDomain rd: rule.getPremisse()){
                Variable var = rd.getVariable();
                for(String valeur: rd.getDomain()){
                    rule_precondition.put(var,valeur);
                }
            }
            if(this.satisfies(rule_precondition)){
                return true;
            }
        }
        return false;
    }
}