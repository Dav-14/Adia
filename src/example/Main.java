package example;

import planning.Action;
import planning.PlanningProblem;
import planning.State;
import representations.Variable;

import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        List<Action> actionList = HealthCare.createSeveralMedecines(12);
        actionList.add(HealthCare.SYRUP_BUTTON_HIGH);
        actionList.add(HealthCare.SYRUP_BUTTON_LOW);
        actionList.add(HealthCare.SYRUP_BUTTON_MEDIUM);

        //actionList.forEach(d -> System.out.println(d));
        //actionList.forEach(d -> d.rules_list.forEach( obj ->System.out.println(obj)));
        PlanningProblem plan = HealthCare.generateRandomProblem(actionList);
/**
        State state = plan.getState_init();

        System.out.println(state);

        HealthCare.getAllSymptoms().forEach(d -> state.put(d, "none"));


        state.apply(HealthCare.HEALOMAX());

        System.out.println(state);


        System.out.println(HealthCare.HEALOMAX());
 **/
        State in = plan.getState_init();


        Stack<Action> list = plan.breadthSearch();
        for (Action action: list) {
            in.apply(action);
        }




    }
}
