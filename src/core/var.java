package core;

import java.util.*;

public class var extends func {
    char c;// single char for now
    boolean not = false;// is inverted?

    public var(char c) {
        this.c = c;
    }

    public var(String s) {
        c = s.charAt(0);
    }

    @Override
    String toString2() {
        return c + (not ? "'" : "");
    }

    @Override
    public func simplify() {
        return this;
    }

    @Override
    public var not() {
        var v = new var(c);
        v.not = !not;
        return v;
    }

    @Override
    public int total() {
        return 1;
    }

    @Override
    public cons get(var[] v, cons[] c) {
        for (int i = 0; i < v.length; i++) {
            if (v[i].c == this.c) {
                cons result = c[i];
                return not ? (cons) result.not() : result;
            }
        }
        return null;
    }

    @Override
    protected boolean eq2(func other) {
        // System.out.println("var.eq2 " + this + " other=" + other);
        var var = (var) other;
        //return c == var.c && not == var.not;
         return c == var.c;// list func may fail
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + c;
        // hash = 31 * hash + (not ? 1 : 0);
        return hash;

    }

    @Override
    public List<var> list() {

        List<var> list = new ArrayList<var>();
        list.add(this);
        return list;

    }
}
