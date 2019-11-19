package representations;

import java.util.Objects;
import java.util.Set;

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
    public int hashCode() {
        return Objects.hash(name, domain);
    }

    @Override
    public boolean equals(Object o) {
        if(o != null) {
            if(o instanceof Variable) {
                Variable v = (Variable)o;
                return this.name.equals(v.getName()); //&&
                        //this.domain.equals(v.getDomain());
            } else if(o instanceof RestrictedDomain) {
                RestrictedDomain rd = (RestrictedDomain)o;
                return rd.equals(this);
            }
        }
        return false;
    }
}