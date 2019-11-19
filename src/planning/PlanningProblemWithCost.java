package planning;

import java.util.List;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class PlanningProblemWithCost extends PlanningProblem{
    protected Map<Action,Integer> cost;
    protected int countDijkstra;//compteur pour compter le nombre de noeuds explorés dans le Dijkstra
    protected int countAStar;//compteur pour compter le nombre de noeuds explorés dans le AStar

    public PlanningProblemWithCost(State ini, State goal, List<Action> act, Map<Action,Integer> cost){
        super(ini, goal, act);
        this.cost = cost;
        this.countDijkstra = 0;
        this.countAStar = 0;
    }

    public int getCountDijkstra(){
        return countDijkstra;
    }

    public int getCountAStar(){
        return countAStar;
    }

    /**
     * Trouve le State ayant la plus petite distance parmis les States du paramètres open
     * @param distance (Map indiquant la distance de chaque State)
     * @param open (Set contenant les States à évaluer)
     * @return un State
     */
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

    /**
     * Trouve un Stack (une pile) d'action permettant de passer de l'état initial à l'état final d'un problème en utilisant l'algorithme de Dijkstra
     * @return un Stack (une pile) d'action
     */
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
                    this.countDijkstra = this.countDijkstra + 1;
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
        return reverseStackDijkstra(father, plan, goals, distance);
    }

    /**
     * Inverse l'ordre d'un Stack (d'une pile)
     * @param father (Map indiquant le State père de chaque State)
     * @param actions (Map indiquant l'Action qui permet d'arriver à tel State)
     * @param goals
     * @param distance (Map indiquant la distance de chaque State)
     * @return un Stack (une pile)
     */
    protected static Stack<Action> reverseStackDijkstra(Map<State,State> father, Map<State,Action> actions, Set<State> goals, Map<State,Integer> distance){
        Stack<Action> plan_d_action = new Stack<>();
        State goal = searchStateDistanceMin(distance, goals);

        while( !goals.isEmpty() ){
            plan_d_action.push(actions.get(goal) );
            goals.add(goal);
        }

        return plan_d_action;
    }


    public Stack<Action> aStar(){
        Map<State,Integer> distance     = new HashMap<State,Integer>();
        Map<State,State> father         = new HashMap<State,State>();
        Set<State> open                 = new HashSet<State>();
        Map<State,Integer> value        = new HashMap<State,Integer>();
        Map<State,Action> plan_d_action = new HashMap<State,Action>();
        
        open.add(this.state_init);
        father.put(this.state_init,null);
        distance.put(this.state_init,0);
        //value.put( this.state_init, heuristic(this.state_init) );

        while( !open.isEmpty() ){
            State state = searchStateDistanceMin(distance, open);

            if( state.satisfies(this.state_goal) ){
                return getBreadthSearchPlan(father, plan_d_action, state_goal);
            }
            else{
                open.remove(state);
                for(Action act : this.possible_actions){
                    State next = null;
                    if( state.is_applicable(act) ){
                        next = state.apply(act);
                    }
                    if( !distance.containsKey(next) ){
                        distance.put(next,Integer.MAX_VALUE);
                    }
    
                    if( distance.get(next) > (distance.get(state) + cost.get(act)) ){
                        
                        distance.put( next, (distance.get(state) + cost.get(act)) );
                        value.put( next, (distance.get(next) /**+ heuristic(next)**/) );
                        father.put(next,state);
                        plan_d_action.put(next,act);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

}