package datamining;

import java.util.List;
import java.util.Map;

import representations.Variable;

public class BooleanDatabase{
    List<Variable> variables; //La liste des variables de la database
    List<Map<Variable, String>> transactions; //La liste des transaction,
    //une transaction = une ligne de la db donc map de variable->valeur booleenne 
    public BooleanDatabase(List<Variable> vars, List<Map<Variable, String>> trans){
        this.variables = vars;
        this.transactions = trans;
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

    public List<Variable> getVars(){
        return variables;
    }

    public List<Map<Variable, String>> getTransactions(){
        return transactions;
    }




}