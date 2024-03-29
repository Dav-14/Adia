package datamining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import representations.Variable;

public class Main {
    public static void main(String[] args) {
        test1();
        test2();

    }

    //Test à partir de l'exemple ABCDE du CM, preuve de fonctionnement du filtre des closed patterns
    public static void test1(){

        List<Variable> testVars = new ArrayList<>();
        Set<String> boolDom = Set.of("0", "1");
        testVars.add(new Variable("A", boolDom));
        testVars.add(new Variable("B", boolDom));
        testVars.add(new Variable("C", boolDom));
        testVars.add(new Variable("D", boolDom));
        testVars.add(new Variable("E", boolDom));
        List<Map<Variable, String>> trans = new ArrayList<>();
        Map<Variable, String> trans1 = new HashMap<>();
        trans1.put(testVars.get(0), "1");
        trans1.put(testVars.get(1), "1");
        trans1.put(testVars.get(2), "1");
        trans1.put(testVars.get(3), "1");
        trans1.put(testVars.get(4), "1");
        trans.add(trans1);
        Map<Variable, String> trans2 = new HashMap<>();
        trans2.put(testVars.get(0), "1");
        trans2.put(testVars.get(1), "0");
        trans2.put(testVars.get(2), "1");
        trans2.put(testVars.get(3), "0");
        trans2.put(testVars.get(4), "0");
        trans.add(trans2);
        Map<Variable, String> trans3 = new HashMap<>();
        trans3.put(testVars.get(0), "1");
        trans3.put(testVars.get(1), "1");
        trans3.put(testVars.get(2), "1");
        trans3.put(testVars.get(3), "1");
        trans3.put(testVars.get(4), "0");
        trans.add(trans3);
        Map<Variable, String> trans4 = new HashMap<>();
        trans4.put(testVars.get(0), "0");
        trans4.put(testVars.get(1), "1");
        trans4.put(testVars.get(2), "1");
        trans4.put(testVars.get(3), "0");
        trans4.put(testVars.get(4), "0");
        trans.add(trans4);
        Map<Variable, String> trans5 = new HashMap<>();
        trans5.put(testVars.get(0), "1");
        trans5.put(testVars.get(1), "1");
        trans5.put(testVars.get(2), "1");
        trans5.put(testVars.get(3), "0");
        trans5.put(testVars.get(4), "0");
        trans.add(trans5);
        Map<Variable, String> trans6 = new HashMap<>();
        trans6.put(testVars.get(0), "0");
        trans6.put(testVars.get(1), "0");
        trans6.put(testVars.get(2), "0");
        trans6.put(testVars.get(3), "0");
        trans6.put(testVars.get(4), "1");
        trans.add(trans6);
        System.out.println("---TEST EXEMPLE CM---");
        System.out.println("---MOTIFS ELIMINES---");
        FrequentItemsetMiner miner = new FrequentItemsetMiner(new BooleanDatabase(testVars, trans));
        Map<Set<Variable>, Integer> res = miner.frequentItemsets(1);

        //affichage des resultats
        
        System.out.println("---FRQ ITEMSETS----");
        for (Set<Variable> set : res.keySet()) {
            int freq = res.get(set);
            new HashSet<>(set).forEach((var) -> {
                System.out.print(var.getName());
            });
            System.out.println("(" + freq + ")");
        }
        System.out.println("------RULES-------");
        AssociationRuleMiner testAsso = new AssociationRuleMiner(res);
        testAsso.frequentAssociationRules(3, 0).forEach(System.out::print);
    }


    //Test complet avec temps d'execution à partir de l'exemple.csv
    public static void test2(){

        //début du timer
        long timerStart = System.currentTimeMillis();
        //on crée tous les noms de variable
        Set<String> varNames = Set.of("angine", "prise_sirop", "fievre", "oedeme", "fatigue(e)", "toux", "vaccine(e)",
                "hypothermie", "allergie_sucre", "boutons", "grippe", "virus");

        // on prepare les variables pour la lecture de la db
        Set<String> ref_dom_3 = Set.of("basse", "moyenne", "haute");
        Set<String> ref_dom_2 = Set.of("0", "1");
        //ajout des domaines aux variables
        Set<Variable> vars = new HashSet<>();
        for (String name : varNames) {
            if (name.equals("fievre") || name.equals("allergie_sucre")) {
                vars.add(new Variable(name, ref_dom_3));
            } else {
                vars.add(new Variable(name, ref_dom_2));
            }
        }

        //chargement de la db à partir du fichier
        Database db = null;
        try {
            db = Database.loadFromFile("./exemple_db.csv", vars);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //interpretation booleene de db vers boolDB
        BooleanDatabase boolDB = db.toBooleanDatabase();
        db = null;
        System.gc();
        //boolDB.printDB();

        //on charge la boolDB dans le miner
        FrequentItemsetMiner miner = new FrequentItemsetMiner(boolDB);
        //calcul des frequent itemsets
        System.out.println("---TEST EXEMPLE CSV---");
        System.out.println("---MOTIFS ELIMINES---");
        Map<Set<Variable>, Integer> res = miner.frequentItemsets(500);
        //calcul des regles d'association
        
        AssociationRuleMiner testAsso = new AssociationRuleMiner(miner.frequentItemsets(500));
        //affichage des regles générées
        
        List<Rule> frequentRules =  testAsso.frequentAssociationRules(500, 0.9f);
        //fin du timer(on ne compte pas l'affichage des resultats)
        long timerEnd = System.currentTimeMillis();

        //affichage des resultats
        System.out.println("---FRQ ITEMSETS----"); 
        for (Set<Variable> set : res.keySet()) {
            int freq = res.get(set);
            new HashSet<>(set).forEach((var) -> {
                System.out.print(var.getName() + " ");
            });
            System.out.print("(" + freq + ")");
            System.out.println();
        }

        System.out.println("------RULES-------");
        frequentRules.forEach(System.out::print);

        System.out.println( "\nTemps d'execution frqitemset+rule du csv: " + (int)(timerEnd-timerStart)/1000 + "."
                            + (timerEnd-timerStart)%1000 +" secondes");
    }
}