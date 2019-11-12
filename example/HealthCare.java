package example;

import examples.ExampleFactory;
import examples.RuleBuilder;
import planning.Action;
import representations.Rule;
import representations.Variable;

import java.util.*;

public class HealthCare {

    private static ExampleFactory factory = ExampleFactory.newInstance();

    private static Variable ANGINA = factory.createVariable("Angina","true","false");
    private static Variable FLU = factory.createVariable("FLU","true","false");
    private static Variable POX = factory.createVariable("POX","true","false");
    private static Variable PLAGUE = factory.createVariable("PLAGUE","true","false");
    private static Variable FEVER = factory.createVariable("FEVER","high","medium","low","none");
    private static Variable COUGH = factory.createVariable("COUGH","high","medium","low","none");
    private static Variable BUTTONS = factory.createVariable("BUTTONS","high","medium","low","none");

    private static List<Variable> getAllDiseases(){
        return new ArrayList<>(Arrays.asList(ANGINA,FLU,POX,PLAGUE));
    }

    private static List<Variable> getAllSymptoms(){
        return new ArrayList<>(Arrays.asList(FEVER,COUGH,BUTTONS));
    }



    private static Rule SYRUP_BUTTON_HIGH_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(BUTTONS,"high"))
            .withConclusion(factory.createRestrictedDomain(BUTTONS,"medium"))
            .build();
    private static Rule SYRUP_BUTTON_MEDIUM_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(BUTTONS,"medium"))
            .withConclusion(factory.createRestrictedDomain(BUTTONS,"low"))
            .build();

    private static Rule SYRUP_BUTTON_LOW_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(BUTTONS,"low"))
            .withConclusion(factory.createRestrictedDomain(BUTTONS,"none"))
            .build();



    private static Action SYRUP_BUTTON_HIGH = new Action(new HashSet(Arrays.asList(SYRUP_BUTTON_HIGH_r)));
    private static Action SYRUP_BUTTON_MEDIUM = new Action(new HashSet(Arrays.asList(SYRUP_BUTTON_MEDIUM_r)));
    private static Action SYRUP_BUTTON_LOW = new Action(new HashSet(Arrays.asList(SYRUP_BUTTON_LOW_r)));

    private static Action createMedecine(){
        List<Variable> Symtoms = getAllSymptoms();
        Random rnd = new Random();
        int index = rnd.nextInt(Symtoms.size());


        Variable var = Symtoms.get(index);
        Symtoms.remove(var);

        RuleBuilder rule = factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(var,(String[]) var.getDomain().toArray()))
                .withConclusion(factory.createRestrictedDomain(var,"none"));

        Symtoms.stream().forEach(d -> rule.withConclusion(factory.createRestrictedDomain(d, (String) d.getDomain().toArray()[rnd.nextInt(d.getDomain().size())])));



        return null;
    }

    private static List<Action> createMedecine(int n){
        List<Action> medecineList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            medecineList.add(createMedecine());
        }
        return medecineList;
    }


}
