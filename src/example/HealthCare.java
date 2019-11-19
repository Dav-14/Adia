package example;

import examples.ExampleFactory;
import examples.RuleBuilder;
import planning.Action;
import planning.PlanningProblem;
import planning.State;
import representations.RestrictedDomain;
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

    /**
     * créer un médicament qui met un symptome à none et les autres symptomes sont modifiés de façon aléatoire
     * @return une Action (un médicament)
     */
    private static Action createMedecine(){
        List<Variable> Symtoms = getAllSymptoms();
        Random rnd = new Random();
        int index = rnd.nextInt(Symtoms.size());


        Variable var = Symtoms.get(index);
        Symtoms.remove(var);

        RuleBuilder rule = factory.newRuleBuilder()
                .addPrem(new RestrictedDomain(var, var.getDomain()))
                .addConc(factory.createRestrictedDomain(var,"none"));

        Symtoms.stream().forEach(d -> rule.addConc(factory.createRestrictedDomain(d, (String) d.getDomain().toArray()[rnd.nextInt(d.getDomain().size())])));



        return new Action(rule.build());
    }

    /**
     * créer une liste de médicament avec la fonction createMedecine()
     * @param n
     * @return une liste d'Action (liste de médicaments)
     */
    public static List<Action> createSeveralMedecines(int n){
        List<Action> medecineList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            medecineList.add(createMedecine());
        }
        return medecineList;
    }

    /**
     * génère un PlanningProblem avec un état initial aléatoire (à la maladie et aux variables déterminées aléatoirement)
     * @param actionsPossibles
     * @return un PlanningProblem avec un état initial aléatoire
     */
    public static PlanningProblem generateRandomProblem(List<Action> actionsPossibles){
        State st_init = new State();
        State st_fin = new State();

        List<Variable> dis = getAllDiseases();
        Random r1 = new Random();
        int valeurD = r1.nextInt(dis.size());

        st_init.put(dis.get(valeurD), "true");
        st_fin.put(dis.get(valeurD), "false");

        List<Variable> sym = getAllSymptoms();
        Random r2 = new Random();
        int valeurSymp1 = r2.nextInt(sym.size());

        for(int i =0; i<valeurSymp1; i++){
            Random r3 = new Random();
            int valeurSymp2 = r3.nextInt(sym.size());
            Random r4 = new Random();
            int valeurSympNiv = r4.nextInt(4);
            st_init.put(dis.get(valeurSymp2),((String) sym.get(valeurSymp2).getDomain().toArray()[valeurSympNiv]));
            st_fin.put(dis.get(valeurSymp2),null);
            sym.remove(valeurSymp2);
        }

        PlanningProblem pb = new PlanningProblem(st_init, st_fin, actionsPossibles);
        return pb;
    }


}
