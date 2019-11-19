package planning;

import representations.Variable;

public class Heuristic{

    public static int simpleHeuristic(State state){
        int eval = 0;
        
        switch(str){
            case "high":
                heuristic += 3;
            case "medium":
                heuristic += 2;
            case "low":
                heuristic += 1;
            case "null":
                heuristic += 0;
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