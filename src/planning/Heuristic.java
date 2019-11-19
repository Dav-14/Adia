package planning;

import representations.Variable;

public class Heuristic{

    public static int simpleHeuristic(State state){
        int eval = 0;
        
        for(Variable var : state.keySet()){
            String str = state.get(var);

            if(str == "high"){
                eval += 3;
            }
            if(str == "medium"){
                eval += 2;
            }
            if(str == "low"){
                eval += 1;
            }
            if(str == "null"){
                eval += 0;
            }
        }

        return eval;
    }

    public static int informedHeuristic(PlanningProblem problem, State currentState){
        int eval = 0;

        eval = simpleHeuristic(currentState);

        for()
        
        return eval;
    }
}