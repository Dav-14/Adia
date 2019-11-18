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

    /** 
     * Fonction destinée au debug
     */
    public void printDB(){
        for(Map<Variable, String> transac : this.transactions){
            for(Variable var : transac.keySet()){
                System.out.print("["+var.getName()+"="+transac.get(var)+"]");
            }
            System.out.println();
        }
    }
    /**
     * Transforme une base de données non booleene en base de données booléene
     * @return Une instance de base de données booleene
     */
    public BooleanDatabase toBooleanDatabase() {
        List<Map<Variable, String>> bool_transacs = new ArrayList<>();
        List<Variable> vars = new ArrayList<>();
        Set<String> ref_dom = Set.of("0", "1"); 

        for(Map<Variable, String> transac : this.transactions) {
            Map<Variable, String> temp = new HashMap<>();
            for(Variable var : transac.keySet()) {
                String val = transac.get(var);
                if(val.equals("0") || val.equals("1")) {
                    // aucune transformation, déjà booléen
                    temp.put(var, val);
                } else {
                    // ajoute les valeurs du domaine en fin de nom de variable, puis on met à 1 la variable trouvée dans la transaction
                    // ex: temperature => basse, moyenne, haute devient
                    // si temperature=basse en bdd
                    // temperature=basse => 1, temperature=moyenne => 0, etc...
                    for(String other_val : var.getDomain()) {
                        if(!other_val.equals(val)) {
                            Variable other_var = new Variable(var.getName()+"="+other_val, ref_dom);
                            temp.put(other_var, "0");
                        }
                    }
                    String newName = var.getName()+"="+val; 
                    Variable bool_var = new Variable(newName, ref_dom);
                    temp.put(bool_var, "1");
                }
            }
            bool_transacs.add(temp);
        }
        vars = new ArrayList<>(bool_transacs.get(0).keySet());
        return new BooleanDatabase(vars, bool_transacs);
    }

}