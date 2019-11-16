package planning;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class PlanningProblemWithCost extends PlanningProblem{
    



    protected State searchActionCostMin(Map<State,Integer> distance){

    }

    public Stack<Action> Dijkstra(PlanningProblem problem){
        Map<State,Integer> distance = new HashMap<State,Integer>();
        Map<State,State> father     = new HashMap<State,State>();
        Map<State,Action> plan      = new HashMap<State,Action>();
        Set<State> goals            = new HashSet<State>();
        Set<State> open             = new HashSet<State>();
        
        open.add(problem.state_init);
        distance.put(problem.state_init,0);
        father.put(problem.state_init, null);

        while( !open.isEmpty() ){
            State state = searchActionCostMin(open);
            open.remove(state);

            if( state.satisfies(problem.state_goal) ){
                goals.add(state);
            }

            for(Action act : problem.possible_actions){
                if( state.is_applicable(act) ){
                    State next = state.apply(act);

                    if( !distance.containsKey(next) ){
                        distance.put(next,Integer.MAX_VALUE);
                    }

                    if( distance.get(next) > (distance.get(state) + act.cost) ){
                        distance.get(next) = (distance.get(state) + act.cost);
                        father.put(next,state);
                        plan.put(next,act);
                        open.add(next);
                    }
                }
            }
        }
        return getDijkstraPlan(father, plan, goals, distance);
    }

    protected Stack<Action> getDijkstraPlan(Map<State,State> father, Map<State,Action> actions, Set<State> goals, Map<State,Integer> distance){
        Stack<Action> plan_d_action = new Stack<>();
        goal = searchActionCostMin(open);

        while( !goals.isEmpty() ){
            plan_d_action.push(actions.get(goal) );
            goals.add(goal);
        }

        return plan_d_action;
    }

}