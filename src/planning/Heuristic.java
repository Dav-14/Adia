package planning;

import representations.Variable;

public class Heuristic{

    public static int evalDomain(String str){
        switch(str){
            case "high":
                return 3;
            case "medium":
                return  2;
            case "low":
                return  1;
            default:
                return  0;
        }
    }


    public static int simpleHeuristic(State state){
        int eval = 0;

        for (Variable var: state.keySet()) {
            eval += evalDomain(state.get(var));
        }
        return eval;
    }

    public static int informedHeuristic(PlanningProblem problem, State currentState){
        int eval = 0;

        eval = simpleHeuristic(currentState);

        //for()
        
        return eval;
    }
}