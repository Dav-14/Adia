package planning;

import representations.Variable;

import java.util.HashMap;

public class State extends HashMap<Variable, String> {
    public State(){
    }

    public Boolean satisfies(State s){//partial_state == variable
        for(Variable v: s){
            if(!(v||)){
                return true;
            }
        }
        return false;
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