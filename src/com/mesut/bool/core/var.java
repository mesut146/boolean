package com.mesut.bool.core;

import java.util.ArrayList;
import java.util.List;

//todo var name may cause confliction in java 9
public class var extends func {
    String value;
    boolean not = false;// is inverted?

    public var(char chr) {
        this.value = String.valueOf(chr);
    }

    public var(String str) {
        this.value = str;
    }

    public String getValue() {
        return value;
    }

    @Override
    protected String toString2() {
        if (not) {
            if (notDel.equals("'") || notDel.equals("!")) {
                return value + notDel;
            } else {
                return "~" + value;
            }
        }
        return value;
    }

    @Override
    public func simplify() {
        return this;
    }

    @Override
    public var not() {
        var v = new var(value);
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
            if (v[i].value.equals(value)) {
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
        return value.equals(var.value);// list func may fail
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + value.hashCode();
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
