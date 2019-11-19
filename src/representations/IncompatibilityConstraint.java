
package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IncompatibilityConstraint extends Rule {
    /**
     * représente une contrainte d'incompatibilité, donc une rule sans conclusion, 
     * dont la premisse est une conjonction de RestrictedDomain
     */
    public IncompatibilityConstraint(Set<RestrictedDomain> premisse) {
        super(premisse, new HashSet<RestrictedDomain>());
    }

    @Override
    /**
     * Override de la satisfaction pour verifier la conjonction de la premisse
     */
    public boolean isSatisfiedBy(Map<Variable, String> v) {
        boolean ok_p = true;
        for(RestrictedDomain rd : premisse) {
            String val = v.get(rd.getVariable());
            if(!rd.getDomain().contains(val)) {
                ok_p = false;
                break;
            }
        }
        
        return !ok_p;
    }
}