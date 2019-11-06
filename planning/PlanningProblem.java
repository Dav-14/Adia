package planning;

import java.util.List;

public class PlanningProblem {
    protected State initial;
    protected List<State> finals;

    protected List<Action> actions;

    public PlanningProblem(State ini, List<State> fin, List<Action> act){
        this.initial = ini;
        this.finals = fin;
        this.actions = act;
    }
}