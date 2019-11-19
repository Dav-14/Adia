package examples;

import java.util.List;
import java.util.Map;

import representations.Rule;
import representations.Variable;

public class Main {
    public static void main(String[] args) {
        Rule r1 = ExampleImpl.getRule1();
        Rule r9 = ExampleImpl.getRule9();

        ExampleFactory factory = ExampleFactory.newInstance();
        Map<Variable, String>   patient1 = factory.newMapBuilder()
                                            .add(ExampleImpl.angine, "Oui")
                                            .add(ExampleImpl.fievre, "Moyenne")
                                            .build(),
                                patient2 = factory.newMapBuilder()
                                            .add(ExampleImpl.angine, "Oui")
                                            .add(ExampleImpl.fievre, "Haute")
                                            .build(),
                                patient3 = factory.newMapBuilder()
                                            .add(ExampleImpl.angine, "Oui")
                                            .add(ExampleImpl.fievre, "Basse")
                                            .build(),
                                patient4 = factory.newMapBuilder()
                                            .add(ExampleImpl.hypothermie, "Oui")
                                            .add(ExampleImpl.fievre, "Moyenne")
                                            .build(),
                                patient5 = factory.newMapBuilder()
                                            .add(ExampleImpl.hypothermie, "Oui")
                                            .add(ExampleImpl.fievre, "Basse")
                                            .build();

        List.of(patient1, patient2, patient3).stream().forEach((patient) -> {
            System.out.println(r1.isSatisfiedBy(patient));
        });
        List.of(patient4, patient5).stream().forEach((patient) -> {
            System.out.println(r9.isSatisfiedBy(patient));
        });
    }
}