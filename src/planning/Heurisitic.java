package planning;

import java.util.List;
import java.util.HashMap;
import java.util.Stack;

import representations.Variable;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Heuristic{

    public static int simpleHeuristic(State currentState){
        int heuristic = 0;
        
        for(Variable key : currentState.keySet()){
            String str = currentState.get(key);

            if(str == "high"){
                heuristic += 3;
            }
            if(str == "medium"){
                heuristic += 2;
            }
            if(str == "low"){
                heuristic += 1;
            }
            if(str == "null"){
                heuristic += 0;
            }
        }

        return heuristic;
    }

    public static int informedHeuristic(State currentState){
        int heuristic = 0;

        
        return heuristic;
    }
}