package representations;

import java.util.Set;

public class RestrictedDomain {
    protected Variable      variable;
    protected Set<String>   domain;

    public RestrictedDomain(Variable var, Set<String> subdom) {
        this.variable = var;
        this.domain = subdom;
    }

    public Variable     getVariable()     { return this.variable; }
    public Set<String>  getDomain()  { return this.domain;   }

    @Override
    public boolean equals(Object o) {
        if(o != null) {
            if(o instanceof RestrictedDomain) {
                RestrictedDomain rd = (RestrictedDomain)o;
                return this.variable.equals(rd.getVariable()) && 
                        this.domain.equals(rd.getDomain());
            } else if(o instanceof Variable) {
                return this.variable.equals((Variable)o);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "RD{" +
                "var=" + variable +
                ", dom=" + domain +
                '}';
    }
}