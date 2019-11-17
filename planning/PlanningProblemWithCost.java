package planning;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class PlanningProblemWithCost extends PlanningProblem{
    private Map<Action,Integer> cost;

    public PlanningProblemWithCost(State ini, State goal, List<Action> act, Map<Action,Integer> cost){
        super(ini, goal, act);
        this.cost = cost;
    }


    protected static State searchStateDistanceMin(Map<State,Integer> distance, Set<State> open){
        State state_smallest_distance = null;
        int distanceMin = Integer.MAX_VALUE;
        
        for( State state_tested: open ){
            if( distance.get(state_tested) < distanceMin ){
                distanceMin = distance.get(state_tested);
                state_smallest_distance = state_tested;
            }
        }

        return state_smallest_distance;
    }

    public Stack<Action> Dijkstra(){
        Map<State,Integer> distance = new HashMap<State,Integer>();
        Map<State,State> father     = new HashMap<State,State>();
        Map<State,Action> plan      = new HashMap<State,Action>();
        Set<State> goals            = new HashSet<State>();
        Set<State> open             = new HashSet<State>();
        
        open.add(this.state_init);
        distance.put(this.state_init,0);
        father.put(this.state_init, null);

        while( !open.isEmpty() ){
            State state = searchStateDistanceMin(distance, open);
            open.remove(state);

            if( state.satisfies(this.state_goal) ){
                goals.add(state);
            }

            for(Action act : this.possible_actions){
                if( state.is_applicable(act) ){
                    State next = state.apply(act);

                    if( !distance.containsKey(next) ){
                        distance.put(next,Integer.MAX_VALUE);
                    }

                    if( distance.get(next) > (distance.get(state) + cost.get(act)) ){
                        distance.put(next,(distance.get(state) + cost.get(act)));
                        father.put(next,state);
                        plan.put(next,act);
                        open.add(next);
                    }
                }
            }
        }
        return getDijkstraPlan(father, plan, goals, distance);
    }

    protected static Stack<Action> getDijkstraPlan(Map<State,State> father, Map<State,Action> actions, Set<State> goals, Map<State,Integer> distance){
        Stack<Action> plan_d_action = new Stack<>();
        State goal = searchStateDistanceMin(distance, goals);

        while( !goals.isEmpty() ){
            plan_d_action.push(actions.get(goal) );
            goals.add(goal);
        }

        return plan_d_action;
    }

}