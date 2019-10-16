
package representations;

import java.util.HashSet;
import java.util.Set;

public class Disjunction extends Rule {
    public Disjunction(Set<RestrictedDomain> conclusion) {
        super(new HashSet<>(), conclusion);
    }
}