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

    public Boolean is_applicable(Action act){
        for(Action a: this.actions){
            if(this.satisfies()){
                return true;
            }
        }
        return false;
    }
}