package representations;

import java.util.Set;
import java.util.Map;
import java.util.List;

/**
 * Interface pour la representation de contraintes
 */
public interface Constraint {
    public Set<Variable>    getScope();
    public Set<RestrictedDomain> getRDoms();
    public boolean          isSatisfiedBy(Map<Variable, String> v);
    //public boolean          filter(List<RestrictedDomain> rd, Map<Variable, Set<String>> vars);
}