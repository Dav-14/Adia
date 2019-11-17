package datamining;

import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;
import representations.Variable;

public class Interpretation {
    protected Map<Variable, String>  conversions;
                
    public Interpretation() {
        this.conversions = new HashMap<>();
    }
    
    public void put(Variable _var,String _boolEq) {
        this.conversions.put(_var, _boolEq);
    }
    //pour debug
    public void printInter(){
        for(Variable var : this.conversions.keySet()) {
            String value = var.getDomain().iterator().next();
            String name = var.getName();
            System.out.println("entry:"+name+"="+value+"=>"+this.conversions.get(var));
        }
    }
    
    //retourne l'interpretation booleene de la variable 
    public String getInter(String _name,String _value) {
        for(Variable var : this.conversions.keySet()) {
            if( var.getDomain().iterator().next().equals(_value) &&
                var.getName().equals(_name)){
                return this.conversions.get(var);
            }
        }
        
        return "0";
    }

}