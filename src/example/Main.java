package example;

import planning.Action;
import planning.InformedHeuristic;
import planning.PlanningProblem;
import planning.PlanningProblemWithCost;
import planning.SimpleHeuristic;
import planning.State;
import representations.Rule;
import representations.Variable;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        List<Action> actionList = HealthCare.createSeveralMedecines(10);


        actionList.add(HealthCare.HEALOMAX());
        actionList.add(HealthCare.SYRUP_BUTTON_HIGH);
        actionList.add(HealthCare.SYRUP_BUTTON_LOW);
        actionList.add(HealthCare.SYRUP_FEVER_HIGH);
        actionList.add(HealthCare.SYRUP_FEVER_LOW);

        //actionList.forEach(d -> System.out.println(d));
        //actionList.forEach(d -> d.rules_list.forEach( obj ->System.out.println(obj)));
        PlanningProblemWithCost plan = HealthCare.generateRandomProblemWithCost(actionList);
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

        //List<Action> list = plan.Dijkstra();
        List<Action> list = plan.aStar(new SimpleHeuristic());

        System.out.println("Action list size = " + list.size());

        Collections.reverse(list);

        for (Action action: list) {
            System.out.println(action);
            in.apply(action);
            System.out.println(in);

        }
        System.out.println(plan.getStateGoal());



    }
}
