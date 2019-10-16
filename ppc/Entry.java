package ppc;

import java.util.Map;

import representations.Variable;

public class Entry<K, V> implements Map.Entry<K, V> {

    protected K key;
    protected V value;
    public Entry(K k, V v) {
        this.key = k;
        this.value = v;
    }

    @Override
    public K getKey() { return this.key; }

    @Override
    public V getValue() { return this.value; }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
    static class VarEntry extends Entry<Variable, String> {

        public VarEntry(Variable k, String v) {
            super(k, v);
        }

        @Override
        public String toString() {
            return String.format("%s (%s)", this.key.getName(), this.value);
        }
        
    }
}

