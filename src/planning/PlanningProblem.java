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
        
    protected int countDepth;
    protected int countDepthIterative;
    protected int countBreadth;

    public PlanningProblem(State ini, State goal, List<Action> act){
        this.state_init = ini;
        this.state_goal = goal;
        this.possible_actions = act;
        this.countDepth = 0;
        this.countDepthIterative = 0;
        this.countBreadth = 0;
    }

    public int getCountDepth(){
        return countDepth;
    }

    public int getCountDepthIterative(){
        return countDepthIterative;
    }

    public int getCountBreadth(){
        return countBreadth;
    }

    /**
     * Trouve un Stack (une pile) d'Action permettant d'atteindre le State final du PlanningProblem en utilisant la recherche en profondeur
     * @param actual_state
     * @param plan_d_action
     * @param closed
     * @return un Stack (une pile) d'Action
     */
    public Stack<Action> depthSearch(State actual_state, Stack<Action> plan_d_action, List<State> closed){
        this.countDepth = 0;
        if( actual_state.satisfies(this.state_goal) ){
            return plan_d_action;
        }
        else{
            for(Action act: this.possible_actions){
                if( actual_state.is_applicable(act) ){
                    this.countDepth = this.countDepth + 1;
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

    /**
     * Trouve un Stack (une pile) d'Actions permettant d'atteindre le State final du PlanningProblem en utilisant la recherche en largeur
     * @return un Stack (une pile) d'Actions
     */
    public Stack<Action> breadthSearch(){
        Map<State,State> father = new HashMap<State,State>();
        Map<State,Action> plan  = new HashMap<State,Action>();
        List<State> closed      = new ArrayList<State>();
        Queue<State> open       = new ArrayDeque<State>();
        
        open.add(this.state_init);
        father.put(this.state_init, null);

        while( !open.isEmpty() ){
            State state = open.remove();
            closed.add(state);

            for(Action act : this.possible_actions){
                if( state.is_applicable(act) ){
                    this.countBreadth = this.countBreadth +1;
                    State next = state.apply(act);
                    
                    if( !closed.contains(next) && !open.contains(next) ){
                        father.put(next, state);
                        plan.put(next, act);
                        
                        if( next.satisfies(this.state_goal) ){
                            return getBreadthSearchPlan(father, plan, next);
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

    /**
     * Permet de reconstruire le Stack (la pile) d'action dans le bon ordre
     * @param father (Map liant un State à son State père)
     * @param actions (Map liant un State à son Action permettant de l'atteindre)
     * @param goal (State )
     * @return un Stack (une pile) d'Action
     */
    protected static Stack<Action> getBreadthSearchPlan(Map<State,State> father, Map<State,Action> actions, State goal){
        Stack<Action> plan_d_action = new Stack<>();
        
        while( goal != null ){
            plan_d_action.push(actions.get(goal));
            goal = father.get(goal);  
        }
        
        return plan_d_action;
    }
}