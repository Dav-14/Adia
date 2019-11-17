package datamining;

import java.util.List;
import java.util.Map;

import representations.Variable;

public class BooleanDatabase{
    List<Variable> variables;
    // liste des vars, donc des colonnes du tableau
    // grippe, angine, toux ...
    List<Map<Variable, String>> transactions;
    // Map<Variable, String> = une transaction
    // donc une ligne de db 0;1;0;0;1;0;0;0;1;0;1;1;
    public BooleanDatabase(List<Variable> vars, List<Map<Variable, String>> trans){
        this.variables = vars;
        this.transactions = trans;
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

    public List<Variable> getVars(){
        return variables;
    }

    public List<Map<Variable, String>> getTransactions(){
        return transactions;
    }




}