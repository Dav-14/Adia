package examples;

import java.util.HashMap;
import java.util.Map;

import representations.Variable;

public class MapBuilder {
    protected Map<Variable, String> map;

    public MapBuilder() {
        this.map = new HashMap<>();
    }

    public MapBuilder add(Variable var, String str) {
        this.map.put(var, str);
        return this;
    }

    public Map<Variable, String> build() {
        return new HashMap<Variable, String>(this.map);
    }
}