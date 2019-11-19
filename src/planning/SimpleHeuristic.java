package planning;

import representations.Variable;

public class SimpleHeuristic implements IHeuristic {
    
    private static int evalDomain(String str){
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
    
    public int compute(State state){
        int eval = 0;

        for (Variable var: state.keySet()) {
            eval += evalDomain(state.get(var));
        }
        
        return eval;
    }

    
}