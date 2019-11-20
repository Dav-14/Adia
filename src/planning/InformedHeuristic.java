package planning;

import representations.Variable;

public class InformedHeuristic implements IHeuristic {
    
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
    
    /* On considère que la fièvre est plus grave que les autres symptomes et que les boutons sont plus grave que la toux */
    /* Donc on choisit de mettre des poids de 4 pour la fièvre, de 2 pour les boutons et de 1 pour la toux */
    public int compute(State state){
        int eval = 0;
        int poids;

        for (Variable var: state.keySet()) {
            switch(var.getName()){
                case "FEVER":
                    poids = 4;
                case "BUTTONS":
                    poids =  2;
                case "COUGH":
                    poids =  1;
                default:
                    poids =  0;
            }
            eval += evalDomain(state.get(var)) * poids;
        }
        
        return eval;
    }

    
}