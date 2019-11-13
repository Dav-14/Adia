package datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.Variable;

public class Main{

	public static void main(String[] args){
        List<Variable> testVars = new ArrayList<>();
        Set<String> boolDom = Set.of("0","1");
        testVars.add( new Variable("A", boolDom));
        testVars.add( new Variable("B", boolDom));
        testVars.add( new Variable("C", boolDom));
        testVars.add( new Variable("D", boolDom));
        testVars.add( new Variable("E", boolDom));
        List<Map<Variable, String>> trans = new ArrayList<>();
        Map<Variable, String> trans1 = new HashMap<>();
        trans1.put(testVars.get(0),"1");
        trans1.put(testVars.get(1),"1");
        trans1.put(testVars.get(2),"1");
        trans1.put(testVars.get(3),"1");
        trans1.put(testVars.get(4),"1");
        trans.add(trans1);
        Map<Variable, String> trans2 = new HashMap<>();
        trans2.put(testVars.get(0),"1");
        trans2.put(testVars.get(1),"0");
        trans2.put(testVars.get(2),"1");
        trans2.put(testVars.get(3),"0");
        trans2.put(testVars.get(4),"0");
        trans.add(trans2);
        Map<Variable, String> trans3 = new HashMap<>();
        trans3.put(testVars.get(0),"1");
        trans3.put(testVars.get(1),"1");
        trans3.put(testVars.get(2),"1");
        trans3.put(testVars.get(3),"1");
        trans3.put(testVars.get(4),"0");
        trans.add(trans3);
        Map<Variable, String> trans4 = new HashMap<>();
        trans4.put(testVars.get(0),"0");
        trans4.put(testVars.get(1),"1");
        trans4.put(testVars.get(2),"1");
        trans4.put(testVars.get(3),"0");
        trans4.put(testVars.get(4),"0");
        trans.add(trans4);
        Map<Variable, String> trans5 = new HashMap<>();
        trans5.put(testVars.get(0),"1");
        trans5.put(testVars.get(1),"1");
        trans5.put(testVars.get(2),"1");
        trans5.put(testVars.get(3),"0");
        trans5.put(testVars.get(4),"0");
        trans.add(trans5);
        Map<Variable, String> trans6 = new HashMap<>();
        trans6.put(testVars.get(0),"0");
        trans6.put(testVars.get(1),"0");
        trans6.put(testVars.get(2),"0");
        trans6.put(testVars.get(3),"0");
        trans6.put(testVars.get(4),"1");
        trans.add(trans6);

        FrequentItemsetMiner miner = new FrequentItemsetMiner(new BooleanDatabase(testVars, trans));
        Map<Set<Variable>, Integer> res = miner.frequentItemsets(3);
        
        for(Set<Variable> set : res.keySet()) {
            int freq = res.get(set);
            new HashSet<>(set).forEach((var) -> {
                System.out.print(var.getName());
            });
            System.out.print("(" + freq + ")" );
            System.out.println();
        
        }

    }
}