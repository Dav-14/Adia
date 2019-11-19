
package representations;

import java.util.HashSet;
import java.util.Set;
/**
 * Représente une disjonction, donc une rule sans premisse, 
 * dont la conclusion est une disjonction de RestrictedDomain
 */
public class Disjunction extends Rule {
    public Disjunction(Set<RestrictedDomain> conclusion) {
        super(new HashSet<>(), conclusion);
    }
}