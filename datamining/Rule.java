package datamining;

import java.util.Set;

import representations.Variable;



public class Rule{
    Set<Variable> X;
    Set<Variable> Y;
    private float freq = 1.0f;
    private float conf = 0.0f;

    public Rule(Set<Variable> _X, Set<Variable> _Y){
        X=_X;
        Y=_Y;
    }
    
    @Override
    public String toString(){
        String xTxt = X.stream().map((var) -> var.getName() ).reduce("[", (a, b) -> {
            if(!a.equals("[")) {
                return String.format("%s, %s", a, b);
            } else {
                return String.format("%s%s", a, b);
            }
        });
        xTxt += "]";
        String yTxt = Y.stream().map((var) -> var.getName() ).reduce("[", (a, b) -> {
            if(!a.equals("[")) {
                return String.format("%s, %s", a, b);
            } else {
                return String.format("%s%s", a, b);
            }
        });
        yTxt += "]";
        return String.format("%s --> %s (f: %f, conf: %f)\n", xTxt, yTxt, freq, conf);
    }

    public Set<Variable> getX(){
        return X;
    }

    public Set<Variable> getY(){
        return Y;
    }
        
    public void incrementFreq(){
        freq++;
    }

    public void setFreq(float _freq){
        freq=_freq;
    }

    public void setConf(float _conf){
        conf=_conf;
    }

    public float getFreq(){
        return freq;
    }

    public float getConf(){
        return conf;
    }

    @Override
    public boolean equals(Object obj){

        if(obj != null && obj instanceof  Rule) {
            Rule o = (Rule)obj;

            // bijection X
            for(Variable o_x : o.getX()) {
                if(!this.getX().contains(o_x)) {
                    return false;
                }
            }
            for(Variable this_x : this.getX()) {
                if(!o.getX().contains(this_x)) {
                    return false;
                }
            }
            // bijection Y
            for(Variable o_y : o.getY()) {
                if(!this.getY().contains(o_y)) {
                    return false;
                }
            }
            for(Variable this_y : this.getY()) {
                if(!o.getY().contains(this_y)) {
                    return false;
                }
            }
            // bijection checked
            return true;
        }

        return false;
    }

}