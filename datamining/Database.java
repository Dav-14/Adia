package datamining;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import representations.Variable;

public class Database {
    
    protected List<Variable>    variables;
    protected List<Map<Variable, String>>   transactions;

    public Database(List<Variable> _vars, List<Map<Variable, String>> instances) {
        this.variables = _vars;
        this.transactions = instances;
    }

    public static Database loadFromFile(String filename, Set<Variable> _vars) throws IOException {
        DBReader reader = new DBReader(_vars);
        return reader.importDB(filename);
    }

    //pour debug
    public void printDB(){
        for(Map<Variable, String> transac : this.transactions){
            for(Variable var : transac.keySet()){
                System.out.print("["+var.getName()+"="+transac.get(var)+"]");
            }
            System.out.println();
        }
    }

    public BooleanDatabase toBooleanDatabase() {
        List<Map<Variable, String>> bool_transacs = new ArrayList<>();
        List<Variable> vars = new ArrayList<>();
        Set<String> ref_dom = Set.of("0", "1"); 

        // Si la valeur n'est pas 0 ou 1
        // on crée une variable avec un nom "name=value" qu'on met à 1
        // et on change le domaine
        for(Map<Variable, String> transac : this.transactions) {
            Map<Variable, String> temp = new HashMap<>();
            for(Variable var : transac.keySet()) {
                String val = transac.get(var);
                if(val.equals("0") || val.equals("1")) {
                    // correct, aucune transformation
                    vars.add(var);
                    temp.put(var, val);
                } else {
                    // add non-set values to 0
                    for(String other_val : var.getDomain()) {
                        if(!other_val.equals(val)) {
                            Variable other_var = new Variable(var.getName()+"="+other_val, ref_dom);
                            vars.add(other_var);
                            temp.put(other_var, "0");

                            //System.out.println("set " + other_var.getName() + "to zero");
                        }
                    }
                    String newName = var.getName()+"="+val; // name=value
                    Variable bool_var = new Variable(newName, ref_dom);
                    vars.add(bool_var);
                    temp.put(bool_var, "1");
                    //System.out.println("set " + bool_var.getName() + "to one");
                        
                }
            }
            
            bool_transacs.add(temp);
        }
        return new BooleanDatabase(vars, bool_transacs);
    }

}