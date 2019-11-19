package example;

import planning.Action;
import planning.PlanningProblem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Action> actionList = HealthCare.createSeveralMedecines(12);

        //actionList.forEach(d -> System.out.println(d));
        actionList.forEach(d -> d.rules_list.forEach( obj ->System.out.println(obj)));
        //PlanningProblem plan = HealthCare.generateRandomProblem(actionList);
    }
}
