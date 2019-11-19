package representations;

import java.util.Objects;
import java.util.Set;
/**
 * Représentation d'une variable avec un nom et un domaine
 */
public class Variable {
    protected String        name;
    protected Set<String>   domain;
    
    public Variable(String name, Set<String> domain) {
        this.name = name;
        this.domain = domain;
    }

    public String       getName()   { return this.name;     }
    public Set<String>  getDomain() { return this.domain;   }
    
    @Override
    /** 
     * Utilsé pour la recherche de variables similaire dans un Set ou une List
     */
    public int hashCode() {
        return Objects.hash(name, domain);
    }

    @Override
    /**
     * Override d'equals pour la comparaison de deux variables
     */
    public boolean equals(Object o) {
        if(o != null) {
            if(o instanceof Variable) {
                Variable v = (Variable)o;
                return this.name.equals(v.getName()); 
            } else if(o instanceof RestrictedDomain) {
                RestrictedDomain rd = (RestrictedDomain)o;
                return rd.equals(this);
            }
        }
        return false;
    }
}