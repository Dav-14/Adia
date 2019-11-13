package planning;

import java.util.List;

public class PlanningProblem {
    protected State state_init;
    protected State state_goal;

    protected List<Action> possible_actions;

    public PlanningProblem(State ini, State fin, List<Action> act){
        this.state_init = ini;
        this.state_goal = fin;
        this.possible_actions = act;
    }

    public List<Action> depthSearch(State actual_state, List<Action> plan_d_action, List<State> closed){
        if( actual_state.satisfies(this.state_goal) ){
            return plan_d_action;
        }
        else{
            for(Action act: this.possible_actions){
                if( actual_state.is_applicable(act) ){
                    State nextState = actual_state.apply(act);
                    if( !closed.contains(nextState) ){
                        plan_d_action.add(act);
                        closed.add(nextState);
                        List<Action> sousPlan = depthSearch(actual_state, plan_d_action, closed);
                        if( !sousPlan.isEmpty() ){
                            return sousPlan;
                        }
                        else{
                            
                        }
                    }
                }
            }
            return null;
        }
    }
}