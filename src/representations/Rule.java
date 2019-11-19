package representations;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Représentation d'une rule avec premisse et conclusion
 */
public class Rule implements Constraint {
    protected Set<RestrictedDomain>    premisse,
                                        conclusion;

    public Rule(Set<RestrictedDomain> premisse, Set<RestrictedDomain> conclusion) {
        this.premisse = premisse;
        this.conclusion = conclusion;
    }

    public Set<RestrictedDomain> getPremisse(){
        return this.premisse;
    }

    public Set<RestrictedDomain> getConclusion(){
        return this.conclusion;
    }

    @Override
    /**
     * Renvoie un Set avec toutes les variable de la rule
     */
    public Set<Variable> getScope() {
        // add premisse vars
        Set<Variable> ret = this.premisse.stream().map(rd -> rd.getVariable()).collect(Collectors.toSet());
        // add conclusion vars
        ret.addAll(this.conclusion.stream().map(rd -> rd.getVariable()).collect(Collectors.toSet()));

        return ret.stream().distinct().collect(Collectors.toSet());
    }

    @Override
    /**
     * Renvoie tous les Rdom de la rule
     */
    public Set<RestrictedDomain> getRDoms() {
        // add premisse vars
        Set<RestrictedDomain> ret = new HashSet<>(this.premisse);
        // add conclusion vars
        ret.addAll(this.conclusion);

        return ret.stream().distinct().collect(Collectors.toSet());
    }

    @Override
    /**
     * On verifie si l'input v satisfait bien la rule
     */
    public boolean isSatisfiedBy(Map<Variable, String> v) {
        // 0 0 = 1
        // 0 1 = 1
        // 1 0 = 0 // on test ça
        // 1 1 = 1
        boolean ok_p = true;
        for(RestrictedDomain rd : premisse) {
            String val = v.get(rd.getVariable());
            if(!rd.getDomain().contains(val)) {
                ok_p = false; // premisse = 0
                break;
            }
        }
        boolean ok_c = false;
        for(RestrictedDomain rd : conclusion) {
            String val = v.get(rd.getVariable());
            if(rd.getDomain().contains(val)) {
                ok_c = true; // conclusion = 1
                break;
            }
        }

        if(ok_p == false) {
            return true;
        }
        return ok_c;
    }

    @Override
    public String toString() {
        String prem = "";
        String conc = "";

        for (RestrictedDomain rd: this.premisse) {
            prem += rd.toString();
        }

        for (RestrictedDomain rd: this.conclusion) {
            conc += rd.toString();
        }

        return "Rule{" +
                "p=" + prem +
                ", c=" + conc +
                '}';
    }
}