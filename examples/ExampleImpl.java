package examples;

import java.util.List;

import representations.Rule;
import representations.Variable;

// 1 L'angine provoque une fièvre haute ou moyenne
// 2 L'angine provoque la toux
// 3 Une grippe, en l'absence de vaccination, provoque une fièvre haute
// 4 Une grippe, en l'absence de vaccination, provoque la fatigue
// 5 L'angine peut ou non être provoquée par un virus
// 6 Une grippe est toujours provoquée par un virus
// 7 La prise de sirop avec une allergie moyenne au sucre provoque des boutons
// 8 La prise de sirop avec une allergie haute au sucre provoque un œdème
// 9 On ne peut pas à la fois avoir une fièvre haute ou moyenne et être en hypothermie

public class ExampleImpl {
    private  static ExampleFactory factory = ExampleFactory.newInstance();

    public static final Variable angine      = factory.createVariable("angine",      "Oui", "Non");
    public static final Variable fievre      = factory.createVariable("fievre",      "Basse", "Moyenne", "Haute");
    public static final Variable grippe      = factory.createVariable("grippe",      "Oui", "Non");
    public static final Variable toux        = factory.createVariable("toux",        "Oui", "Non");
    public static final Variable fatigue     = factory.createVariable("fatigue",     "Oui", "Non");
    public static final Variable vaccin      = factory.createVariable("vaccin",      "Oui", "Non");
    public static final Variable virus       = factory.createVariable("virus",       "Oui", "Non");
    public static final Variable sirop       = factory.createVariable("sirop",       "Oui", "Non");
    public static final Variable allergie    = factory.createVariable("allergie",    "Basse", "Moyenne", "Haute");
    public static final Variable boutons     = factory.createVariable("boutons",     "Oui", "Non");
    public static final Variable oedeme      = factory.createVariable("oedeme",      "Oui", "Non");
    public static final Variable hypothermie = factory.createVariable("hypothermie", "Oui", "Non");

    private ExampleImpl() {}

    public static List<Variable> getAllVars() {
        return List.of(angine     ,
                        fievre     ,
                        grippe     ,
                        toux       ,
                        fatigue    ,
                        vaccin     ,
                        virus      ,
                        sirop      ,
                        allergie   ,
                        boutons    ,
                        oedeme     ,
                        hypothermie);
    }

    public static List<Rule> getAllRules() {
        return List.of(
            getRule1(),
            getRule2(),
            getRule3(),
            getRule4(),
            getRule5(),
            getRule6(),
            getRule7(),
            getRule8(),
            getRule9()
        );
    }

    public static Rule getRule1() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(angine, "Oui"))
                .withConclusion(factory.createRestrictedDomain(fievre, "Moyenne", "Haute"))
                .build();
    }
    public static Rule getRule2() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(angine, "Oui"))
                .withConclusion(factory.createRestrictedDomain(toux, "Oui"))
                .build();

    }
    public static Rule getRule3() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(grippe, "Oui"),
                                factory.createRestrictedDomain(vaccin, "Non"))
                .withConclusion(factory.createRestrictedDomain(fievre, "Haute"))
                .build();
    }
    public static Rule getRule4() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(grippe, "Oui"),
                                factory.createRestrictedDomain(vaccin, "Non"))
                .withConclusion(factory.createRestrictedDomain(fatigue, "Oui"))
                .build();
    }
    public static Rule getRule5() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(angine, "Oui"))
                .withConclusion(factory.createRestrictedDomain(virus, "Oui", "Non"))
                .build();

    }
    public static Rule getRule6() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(grippe, "Oui"))
                .withConclusion(factory.createRestrictedDomain(virus, "Oui"))
                .build();
    }
    public static Rule getRule7() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(sirop, "Oui"),
                                factory.createRestrictedDomain(allergie, "Moyenne"))
                .withConclusion(factory.createRestrictedDomain(boutons, "Oui"))
                .build();

    }
    public static Rule getRule8() {
        return factory.newRuleBuilder()
                .withPremisse(factory.createRestrictedDomain(sirop, "Oui"),
                                factory.createRestrictedDomain(allergie, "Haute"))
                .withConclusion(factory.createRestrictedDomain(oedeme, "Oui"))
                .build();
    }
    public static Rule getRule9() {
        return factory.createIncompatibilityConstraint(
                        factory.createRestrictedDomain(fievre, "Moyenne", "Haute"),
                        factory.createRestrictedDomain(hypothermie, "Oui"));
    }
    
}