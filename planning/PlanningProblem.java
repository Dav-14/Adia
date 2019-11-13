package planning;

import java.util.List;

public class PlanningProblem {
    protected State etat_initial;
    protected List<State> etats_finaux;

    protected List<Action> actions_possibles;

    public PlanningProblem(State ini, List<State> fin, List<Action> act){
        this.etat_initial = ini;
        this.etats_finaux = fin;
        this.actions_possibles = act;
    }
}