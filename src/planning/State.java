package planning;

import representations.*;

import java.util.HashMap;
import java.util.Objects;

public class State extends HashMap<Variable,String>{

    private static final long serialVersionUID = -1839145518454650115L;

    /**
     *  vérifie si un état donné satisfait les états finaux
     * @param partial_state
     * @return un booléen
     */
    public Boolean satisfies(State partial_state){
        for(Variable var: partial_state.keySet()){
            if( !( this.containsKey(var) ) && partial_state.get(var).equals(this.get(var)) ){
                return false;
            }
        }
        return true;
    }

    /**
     * transforme une Rule en State
     * @param rule
     * @return la précondition d'une règle sous-forme d'Etat
     */
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

    /**
     * vérifie si une action est applicable dans un état donné
     * @param action
     * @return un booléen
     */
    public Boolean is_applicable(Action action){
        for(Rule rule: action.getRulesList()){
            State rule_precondition = premisseRule_to_State(rule);
            //System.out.println("pre : " + rule_precondition);
            if(!this.satisfies(rule_precondition)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * applique une action donnée dans un état donné
     * @param action
     * @return un nouvel état
     */
    public State apply(Action action){
        if(this.is_applicable(action)){
            for(Rule rule: action.getRulesList()){

                State rule_precondition = premisseRule_to_State(rule);
                if(this.satisfies(rule_precondition)){
                    for(RestrictedDomain rd: rule.getConclusion()){
                        Variable var = rd.getVariable();

                        for(String valeur: rd.getDomain()){

                            this.put(var,valeur);
                        }
                    }
                }
            }
        }
        return this;
    }

    @Override
    public String toString() {
        String str = "";

        for (Variable var:  this.keySet()) {
            str += var.toString() + " | " + this.get(var) + "\n";
        }
        return (str == "") ? "State null" : str;
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }


    @Override
    public int hashCode(){
        return Objects.hashCode(this.keySet());
    }
}