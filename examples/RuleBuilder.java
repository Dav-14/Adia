package examples;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import representations.RestrictedDomain;
import representations.Rule;

public class RuleBuilder {
    private Set<RestrictedDomain> prem;
    private Set<RestrictedDomain> conc;
    
    public RuleBuilder() {
        this.prem = new HashSet<>();
        this.conc = new HashSet<>();
    }

    public RuleBuilder withPremisse(Set<RestrictedDomain> p) {
        this.prem = p;
        return this;
    }

    public RuleBuilder withPremisse(RestrictedDomain... p) {
        this.prem = new HashSet<>(Arrays.asList(p));
        return this;
    }

    public RuleBuilder withConclusion(Set<RestrictedDomain> c) {
        this.conc = c;
        return this;
    }

    public RuleBuilder withConclusion(RestrictedDomain... c) {
        this.conc = new HashSet<>(Arrays.asList(c));
        return this;
    }

    public Rule build() {
        return new Rule(this.prem, this.conc);
    }
}