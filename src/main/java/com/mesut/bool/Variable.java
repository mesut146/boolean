package com.mesut.bool;

public class Variable extends func  implements Comparable<Variable>{
    String name;

    public Variable(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        for (int i = 0; i < v.length; i++) {
            if (v[i].name.equals(name)) {
                return c[i];
            }
        }
        return null;
    }

    @Override
    protected boolean eq2(func other) {
        Variable var = (Variable) other;
        return name.equals(var.name);// list func may fail
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + name.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Variable other) {
        return name.compareTo(other.name);
    }
}
