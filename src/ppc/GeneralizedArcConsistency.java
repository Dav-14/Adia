 
package ppc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representations.Constraint;
import representations.Variable;
import representations.RestrictedDomain;

/**
 * A class for enforcing generalized arc consistency to domains wrt to variables.
 * The algorithms are brute-force: for deciding whether a value is GAC wrt a constraint, at
 * worst all tuples in the Cartesian product of the variables in the scope of the
 * constraint are tested. 
 */
public class GeneralizedArcConsistency {

	protected Set<Constraint> constraints;
	
	public GeneralizedArcConsistency(Set<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * Filters given domains by removing values which are not arc consistent
	 * with respect to a given set of constraints.
	 * @param constraints A set of constraints
	 * @param domains A map from (at least) the variables occurring in the scope of
	 * the constraint to domains
	 * @throws IllegalArgumentException if a variable occurring in the scope of some
	 * constraint is mapped to no domain
	 */
	public void enforceArcConsistency (List<RestrictedDomain> domains)
	throws IllegalArgumentException {
		boolean hasChanged = true;
		while (hasChanged) {
			for (Constraint constraint: this.constraints) {
				hasChanged = GeneralizedArcConsistency.enforceArcConsistency(constraint, domains);
			}
		}
	}
	
    /**
	 * Filters given domains by removing values which are not arc consistent
	 * with respect to a given constraint.
	 * @param constraint A constraint
	 * @param domains A map from (at least) the variables occurring in the scope of
	 * the constraint to domains
	 * @return true if at least one domain has changed, false otherwise
	 * @throws IllegalArgumentException if a variable occurring in the scope of the
	 * constraint is mapped to no domain
	 */
	public static boolean enforceArcConsistency (Constraint constraint, List<RestrictedDomain> domains)
	throws IllegalArgumentException {
		boolean hasChanged = false;
		// on boucle sur les variables de la contraintes (var)
		for(Variable var : constraint.getScope()) {
			if ( ! domains.contains(var) ) { // ajout containsVar dans RestrictedDomain
				throw new IllegalArgumentException("Variable " + var + " occurs in " + constraint + " but has no domain in " + domains);
			} else {

				RestrictedDomain rdom = domains.get(domains.indexOf(var));
				final String[] domains_values = rdom.getDomain().toArray(new String[]{});
				for(String value : domains_values) {
					if(!GeneralizedArcConsistency.isConsistent(var, value, constraint, domains)) {
						domains.get(domains.indexOf(var)).getDomain().remove(value);
						rdom.getDomain().remove(value);
						System.out.print("Value to delete: " + value + " (" + var.getName() + ")");
						hasChanged = true;
					}
				}
			}
		}
		return hasChanged;
	}
	/**
	 * Decides whether a given value is arc consistent for a given variable wrt a given constraint
	 * and given domains.
	 * @param variable A variable
	 * @param value A value
	 * @param constraint A constraint
	 * @param domains A map from (at least) the variables occurring in the scope of
	 * the constraint to domains (except possibly the given variable)
	 * @return true if the given value is arc consistent for the given variable
	 * @throws IllegalArgumentException if a variable occurring in the scope of the
	 * constraint (except possibly the given variable) is mapped to no domain
	 */
	public static boolean isConsistent (Variable variable, String value, Constraint constraint, List<RestrictedDomain> domains)
	throws IllegalArgumentException {
		Deque<Variable> unassignedVariables = new LinkedList<>(constraint.getScope());
		unassignedVariables.remove(variable);
		Map<Variable, String> partialAssignment = new HashMap<>();
		partialAssignment.put(variable, value);
		return GeneralizedArcConsistency.isConsistent(partialAssignment, unassignedVariables, constraint, domains);
	}
	
	// Helper method ===================================================
	
	protected static boolean isConsistent (Map<Variable, String> partialAssignment, Deque<Variable> unassignedVariables, Constraint constraint, List<RestrictedDomain> domains) {
		if (unassignedVariables.isEmpty()) {
			partialAssignment.forEach((k, v) -> {
				System.out.print(k.getName() + " = " + v + " ");
			});
			System.out.println();
			return constraint.isSatisfiedBy(partialAssignment);
		} else {
			Variable var = unassignedVariables.pop();
			if ( ! domains.contains(var) ) { // ajout containsVar dans RestrictedDomain
				throw new IllegalArgumentException("Variable " + var + " occurs in " + constraint + " but has no domain in " + domains);
			} else {
				// ----------

				Deque<Variable> clone_unass = new ArrayDeque<>(unassignedVariables); // copy

				int index = domains.indexOf(var);
				RestrictedDomain rd = domains.get(index);
				boolean ok = true;
				for(String value : rd.getDomain()) {
					Map<Variable, String> clone_ass = new HashMap<>(partialAssignment);
					clone_ass.put(var, value);
					if(!isConsistent(clone_ass, clone_unass, constraint, domains)) {
						ok = false;
						break;
					}
				}
                // vérifier la consistance (satisfiabilité) par un appel récursif partialAssignment sur les variables du domains, sachant que le test d'arrêt est déjà fait
                // et que si tout est vrai on retour vrai
                
				// sinon on retourne false
				return ok;
			}
		}
	}
}
