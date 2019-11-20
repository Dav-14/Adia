package planning;

import example.HealthCare;
import representations.Rule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Action{
    public Set<Rule> rules_list;

    public Action(Set<Rule> rules_list){
        this.rules_list = rules_list;
    }

    public Action(Rule... rules){
        this.rules_list = new HashSet<>();

        for (Rule rl : rules) {
            rules_list.add(rl);
        }
    }

    /**
     * accesseur de la liste de règle d'une Action
     * @return un Set<Rule>
     */
    public Set<Rule> getRulesList(){
        return this.rules_list;
    }

    public void addRule(Rule... rl){
        this.rules_list.addAll(Arrays.asList(rl));
    }

    @Override
    public String toString() {
        String str = "";
        for (Rule rl: this.rules_list) {
            str += rl.toString() + "\n";
        }
        return (str == "") ? super.toString() : str;
    }

    @Override
    public boolean equals(Object obj){
        return (obj instanceof Action) ? this.rules_list.equals(((Action) obj).rules_list) : false;
    }
}