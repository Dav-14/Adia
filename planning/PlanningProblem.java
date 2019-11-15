package planning;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Queue;

public class PlanningProblem {
    protected State state_init;
    protected State state_goal;

    protected List<Action> possible_actions;

    public PlanningProblem(State ini, State goal, List<Action> act){
        this.state_init = ini;
        this.state_goal = goal;
        this.possible_actions = act;
    }

    /**
     * Trouve un Stack d'Action permettant de d'atteindre le State final du PlanningProblem
     * @param actual_state
     * @param plan_d_action
     * @param closed
     * @return un Stack d'Action
     */
    public Stack<Action> depthSearch(State actual_state, Stack<Action> plan_d_action, List<State> closed){
        if( actual_state.satisfies(this.state_goal) ){
            return plan_d_action;
        }
        else{
            for(Action act: this.possible_actions){
                if( actual_state.is_applicable(act) ){
                    State nextState = actual_state.apply(act);
                    if( !closed.contains(nextState) ){
                        plan_d_action.push(act);
                        closed.add(nextState);
                        Stack<Action> sousPlan = depthSearch(actual_state, plan_d_action, closed);
                        if( !sousPlan.empty() ){
                            return sousPlan;
                        }
                        else{
                            sousPlan.pop();
                        }
                    }
                }
            }
            return null;
        }
    }

    public Stack<Action> bSearch(PlanningProblem problem){
        Map<State,State> father = new HashMap<State,State>();
        Map<State,Action> plan = new HashMap<State,Action>();
        List<State> closed = new ArrayList<State>();
        Queue<State> open = new ArrayDeque<State>();
        
        open.add(problem.state_init);
        father.put(problem.state_init, null);

        while( !open.isEmpty() ){
            State state = open.remove();
            closed.add(state);
            for(Action act : problem.possible_actions){
                if( state.is_applicable(act) ){
                    State next = state.apply(act);
                    if( !closed.contains(next) && !open.contains(next) ){
                        father.put(next, state);
                        plan.put(next, act);
                        if( next.satisfies(problem.state_goal) ){
                            return get-bfs-plan(father, plan, next);
                        }
                        else{
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }
}