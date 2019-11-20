package example;

import examples.ExampleFactory;
import examples.RuleBuilder;
import planning.Action;
import planning.PlanningProblem;
import planning.PlanningProblemWithCost;
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
    public static Variable BUTTONS = factory.createVariable("BUTTONS","high","medium","low","none");

    public final static List<Variable> getAllDiseases(){
        return new ArrayList<>(Arrays.asList(ANGINA,FLU,POX,PLAGUE));
    }

    public final static List<Variable> getAllSymptoms(){
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

    private static Rule SYRUP_COUGH_HIGH_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(COUGH,"high"))
            .withConclusion(factory.createRestrictedDomain(COUGH,"medium"))
            .build();
    private static Rule SYRUP_COUGH_MEDIUM_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(COUGH,"medium"))
            .withConclusion(factory.createRestrictedDomain(COUGH,"low"))
            .build();

    private static Rule SYRUP_COUGH_LOW_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(COUGH,"low"))
            .withConclusion(factory.createRestrictedDomain(COUGH,"none"))
            .build();

    public static Action SYRUP_COUGH_HIGH = new Action(new HashSet(Arrays.asList(SYRUP_COUGH_HIGH_r)));
    public static Action SYRUP_COUGH_MEDIUM = new Action(new HashSet(Arrays.asList(SYRUP_COUGH_MEDIUM_r)));
    public static Action SYRUP_COUGH_LOW = new Action(new HashSet(Arrays.asList(SYRUP_COUGH_LOW_r)));

    private static Rule SYRUP_FEVER_HIGH_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(FEVER,"high"))
            .withConclusion(factory.createRestrictedDomain(FEVER,"medium"))
            .build();
    private static Rule SYRUP_FEVER_MEDIUM_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(FEVER,"medium"))
            .withConclusion(factory.createRestrictedDomain(FEVER,"low"))
            .build();
    
    private static Rule SYRUP_FEVER_LOW_r = factory.newRuleBuilder()
            .withPremisse(factory.createRestrictedDomain(FEVER,"low"))
            .withConclusion(factory.createRestrictedDomain(FEVER,"none"))
            .build();

    //public static Action SYRUP_FEVER_HIGH = new Action(new HashSet(Arrays.asList(SYRUP_FEVER_HIGH_r)));
    public static Action SYRUP_FEVER_MEDIUM = new Action(new HashSet(Arrays.asList(SYRUP_FEVER_MEDIUM_r)));
    //public static Action SYRUP_FEVER_LOW = new Action(new HashSet(Arrays.asList(SYRUP_FEVER_LOW_r)));

    public static Action HEALOMAX(){
        RuleBuilder fact = factory.newRuleBuilder();
        getAllSymptoms().forEach(d -> fact.addPrem(factory.createRestrictedDomain(d,"none")));
        getAllDiseases().forEach(obj -> fact.addConc(factory.createRestrictedDomain(obj,"false")));
        return new Action(fact.build());
    }

    public static Action SYRUP_BUTTON_HIGH = new Action(new HashSet(Arrays.asList(SYRUP_BUTTON_HIGH_r)));
    public static Action SYRUP_BUTTON_MEDIUM = new Action(new HashSet(Arrays.asList(SYRUP_BUTTON_MEDIUM_r)));
    public static Action SYRUP_BUTTON_LOW = new Action(new HashSet(Arrays.asList(SYRUP_BUTTON_LOW_r)));


    public static Action SYRUP_FEVER_HIGH = new Action(new HashSet(Arrays.asList(SYRUP_FEVER_HIGH_r)));
    public static Action SYRUP_FEVER_LOW = new Action(new HashSet(Arrays.asList(SYRUP_FEVER_LOW_r)));




    /**
     * créer un médicament qui met un symptome à none et les autres symptomes sont modifiés de façon aléatoire
     * @return une Action (un médicament)
     *
     */
    private static Action createMedecine(){
        List<Variable> Symtoms = new ArrayList<>(getAllSymptoms());
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
        List<Variable> dis = new ArrayList<>(getAllDiseases());
        List<Variable> sym = new ArrayList<>(getAllSymptoms());

        dis.forEach(d -> {st_init.put(d,"false");st_fin.put(d,"false");});
        sym.forEach(obj -> {st_init.put(obj,"none");st_fin.put(obj,"none");});


        Random r1 = new Random();
        Random r2 = new Random();

        int valeurD = r1.nextInt(dis.size());

        st_init.put(dis.get(valeurD), "true");
        st_fin.put(dis.get(valeurD), "false");

        int nbSymp = r2.nextInt(sym.size()-1)+ 1;
        int symDomSize = sym.get(0).getDomain().size();//Size of domain


        for(int i =0; i<nbSymp; i++){

            //Variable
            Random r3 = new Random();
            int indexSymp = r3.nextInt(sym.size());

            //Domaine
            Random r4 = new Random();

            Variable var = sym.get(indexSymp);
            Set<String> domain = new HashSet<>(var.getDomain());
            domain.remove("none");

            int valeurSympNiv = r4.nextInt(domain.size());

            //System.out.println(var + ":" +(String)var.getDomain().toArray()[valeurSympNiv] + " | " + valeurSympNiv);

            st_init.put(var,(String) domain.toArray()[valeurSympNiv]);
            st_fin.put(var,"none");

            sym.remove(var);
        }

        PlanningProblem pb = new PlanningProblem(st_init, st_fin, actionsPossibles);
        return pb;
    }
    /**
     * génère un PlanningProblem avec un état initial aléatoire (à la maladie et aux variables déterminées aléatoirement)
     * @param actionsPossibles
     * @return un PlanningProblem avec un état initial aléatoire
     */
    public static PlanningProblemWithCost generateRandomProblemWithCost(List<Action> actionsPossibles){
        State st_init = new State();
        State st_fin = new State();
        List<Variable> dis = new ArrayList<>(getAllDiseases());
        List<Variable> sym = new ArrayList<>(getAllSymptoms());

        dis.forEach(d -> {st_init.put(d,"false");st_fin.put(d,"false");});
        sym.forEach(obj -> {st_init.put(obj,"none");st_fin.put(obj,"none");});


        Random r1 = new Random();
        Random r2 = new Random();

        int valeurD = r1.nextInt(dis.size());

        st_init.put(dis.get(valeurD), "true");
        st_fin.put(dis.get(valeurD), "false");

        int nbSymp = r2.nextInt(sym.size()-1)+ 1;
        int symDomSize = sym.get(0).getDomain().size();//Size of domain


        for(int i =0; i<nbSymp; i++){

            //Variable
            Random r3 = new Random();
            int indexSymp = r3.nextInt(sym.size());

            //Domaine
            Random r4 = new Random();

            Variable var = sym.get(indexSymp);
            Set<String> domain = new HashSet<>(var.getDomain());
            domain.remove("none");

            int valeurSympNiv = r4.nextInt(domain.size());

            //System.out.println(var + ":" +(String)var.getDomain().toArray()[valeurSympNiv] + " | " + valeurSympNiv);

            st_init.put(var,(String) domain.toArray()[valeurSympNiv]);
            st_fin.put(var,"none");

            sym.remove(var);
        }

        PlanningProblemWithCost pb = new PlanningProblemWithCost(st_init, st_fin, actionsPossibles);
        return pb;
    }
}
