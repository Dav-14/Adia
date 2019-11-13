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

    public List<Action> depthSearch(State actual_state, List<Action> plan){
        if( actual_state.satisfies(this.state_goal) ){
            return plan;
        }
        else{
            for(Action act: this.possible_actions){
                if( actual_state.is_applicable(act) ){
                    State nextState = actual_state.apply(act);
                }
            }
        }
    }
}