
package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IncompatibilityConstraint extends Rule {
    public IncompatibilityConstraint(Set<RestrictedDomain> premisse) {
        super(premisse, new HashSet<RestrictedDomain>());
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, String> v) {
        boolean ok_p = true;
        for(RestrictedDomain rd : premisse) {
            String val = v.get(rd.getVariable());
            if(!rd.getDomain().contains(val)) {
                ok_p = false; // premisse = 0
                break;
            }
        }
        
        return !ok_p;
    }
}