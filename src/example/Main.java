package example;

import planning.Action;
import planning.PlanningProblem;
import planning.State;
import representations.Rule;
import representations.Variable;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        List<Action> actionList = HealthCare.createSeveralMedecines(10);

        actionList.add(HealthCare.SYRUP_BUTTON_HIGH);
        actionList.add(HealthCare.SYRUP_BUTTON_LOW);
        actionList.add(HealthCare.SYRUP_FEVER_HIGH);
        actionList.add(HealthCare.SYRUP_FEVER_LOW);

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

        State in = plan.getStateInit();

        System.out.println(in);


        in.put(HealthCare.BUTTONS,"low");

        System.out.println(in);

        Action newAct = HealthCare.SYRUP_BUTTON_LOW;
        //HealthCare.HEALOMAX().getRulesList().forEach(d -> newAct.addRule(d));


        System.out.println(newAct);
        in.apply(newAct);
        in.apply(HealthCare.HEALOMAX());
        System.out.println(in);
        /**

        List<Action> list = plan.breadthSearch();

        System.out.println("Action list size = " + list.size());

        Collections.reverse(list);

        for (Action action: list) {
            System.out.println(action);
            in.apply(action);
            System.out.println(in);

        }
        System.out.println(plan.getStateGoal());**/



    }
}
