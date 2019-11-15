package planning;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Queue;

public class PlanningProblemWithCost extends PlanningProblem{
    




    public Stack<Action> Dijkstra(PlanningProblem problem){
        Map<State,Integer> distance = new HashMap<State,Integer>();
        Map<State,State> father     = new HashMap<State,State>();
        Map<State,Action> plan      = new HashMap<State,Action>();
        State goals                 = null;
        Queue<State> open           = new ArrayDeque<State>();

        while( !open.isEmpty() ){
            State state = ;
            open = ;

            if( state.satisfies(problem.state_goal) ){
                goals = ;
            }
        }
    }
}