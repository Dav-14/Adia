package ppc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Tree<T> /*implements */{
    protected T value;

    protected Tree<T>       parent = null;
    protected List<Tree<T>>    childs;

    protected Tree<T> next = null;
    protected Tree<T> prev = null;

    public Tree(Tree<T> parent, T val) {
        this.parent     = parent;
        this.value      = val;
        this.childs     = new ArrayList<>();
    }

    public Tree(T val) {
        this(null, val);
    }

    public void addChild(Tree<T> child) {
        // update parent
        child.setParent(this);

        // update bros
        if(this.childs.size() > 0) {
            Tree<T> last = this.childs.get(this.childs.size()-1);
            child.setPrevBro(last);
            last.setNextBro(child);
        }

        this.childs.add(child);
    }

    public void removeChild(Tree<T> child) {
        // update bros
        if((child.getPrevBro() != null) && (child.getNextBro() != null)) {
            Tree<T> prev = child.getPrevBro();
            Tree<T> next = child.getNextBro();
            prev.setNextBro(next);
            next.setPrevBro(prev);
        } else if((child.getPrevBro() != null)) {
            child.getPrevBro().setNextBro(null);
        } else if((child.getNextBro() != null)) {
            child.getNextBro().setPrevBro(null);
        }

        child.setNextBro(null);
        child.setPrevBro(null);

        // update parent
        child.setParent(null);

        this.childs.remove(child);
    }

    public void     setValue(T val)             { this.value = val;     }
    public void     setParent(Tree<T> parent)   { this.parent = parent; }
    public void     setNextBro(Tree<T> bro)     { this.next = bro;      }
    public void     setPrevBro(Tree<T> bro)     { this.prev = bro;      }

    public Tree<T>          getChild(int index) { return this.childs.get(index);}
    public boolean          hasChilds()         { return this.childs.size() > 0;}
    public List<Tree<T>>    getChilds()         { return this.childs;           }
    
    public T        getValue()      { return this.value;    }
    public Tree<T>  getParent()     { return this.parent;   }
    public Tree<T>  getNextBro()    { return this.next;     }
    public Tree<T>  getPrevBro()    { return this.prev;     }

    public boolean  isFirstBro()    { return (this.prev == null);}
    public boolean  isLastBro()     { return (this.next == null);}

    public Tree<T>     getRoot() {
        Tree<T> last = this;
        while(last.getParent() != null) {
            last = last.getParent();
        }
        return last;
    }

    public int getLevel() {
        Tree<T> last = this;
        int lvl = 0;
        while(last.getParent() != null) {
            last = last.getParent();
            lvl++;
        }
        return lvl;
    }

    public List<Tree<T>> getChildsOfLevel(int lvl) {
        List<Tree<T>> ret = new ArrayList<>();

        Stack<Tree<T>> stack = new Stack<>();
        stack.push(this);
        while(!stack.isEmpty()) {
            Tree<T> tmp = stack.pop();

            if(tmp.getLevel() == lvl) {
                ret.add(tmp);
                continue;
            }

            for(Tree<T> t: tmp.getChilds()) {
                stack.push(t);
            }
        }

        return ret;
    }

    public int getMaxLevel() {
        AtomicInteger max = new AtomicInteger(0);
        this.visit((t, l, p) -> {
            if(l > max.get()) {
                max.set(l);
            }
        });
        return max.get();
    }


    public List<Tree<T>>   getBros() {
        Tree<T> last = this;
        while(last.getPrevBro() != null) {
            last = last.getPrevBro();
        }
        List<Tree<T>> ret = new ArrayList<>();
        while(last.getNextBro() != null) {
            ret.add(last);
            last = last.getNextBro();
        }
        ret.add(last);
        return ret;
    }

    @FunctionalInterface
    interface TreeVisitor<T> {
        void apply(Tree<T> tree, int lvl, List<Tree<T>> path);
    }
    public void helper_visit(TreeVisitor<T> visitor, int lvl, List<Tree<T>> path) {
        
        List<Tree<T>> p = new ArrayList<>(path);
        if(lvl != 0) { // without root
            visitor.apply(this, lvl, path);
            
            p.add(this);
        }
        for(Tree<T> tree : this.getChilds()) {
            tree.helper_visit(visitor, lvl+1, p);
        }
    }

    public void visit(TreeVisitor<T> visitor) {
        this.helper_visit(visitor, 0, new ArrayList<>());
    }

    public String helper_tostr(String indent, boolean last)
    {
        // modified from: https://stackoverflow.com/questions/1649027/how-do-i-print-out-a-tree-structure
        String ret = "";
        ret += indent;
        ret += "+";
        if (last) {
           //ret += "+";
           indent += "|---";
        }
        else
        {
            //ret+= "+";
            indent += "|   ";
        }
        ret += String.format("%s", this.value);

        for (int i = 0; i < this.childs.size(); i++) {
            ret += "\n";
            ret += this.childs.get(i).helper_tostr(indent, this.childs.get(i).isLastBro());
        }
        return ret;
    }

    @Override
    public String toString() {
        return this.helper_tostr("", false);
    }

    @Override
    public Object clone() {
        Tree<T> clone = new Tree<T>(this.parent, this.value);
        clone.childs.addAll(this.childs);
        return clone;
    }
}