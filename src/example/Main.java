package example;

import planning.Action;
import planning.PlanningProblem;
import planning.State;
import representations.Variable;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Action> actionList = HealthCare.createSeveralMedecines(12);

        //actionList.forEach(d -> System.out.println(d));
        //actionList.forEach(d -> d.rules_list.forEach( obj ->System.out.println(obj)));
        PlanningProblem plan = HealthCare.generateRandomProblem(actionList);

        State state = plan.getState_init();

        System.out.println(state);

        HealthCare.getAllSymptoms().forEach(d -> state.put(d, "none"));


        state.apply(HealthCare.HEALOMAX());

        System.out.println(state);


        System.out.println(HealthCare.HEALOMAX());
    }
}
