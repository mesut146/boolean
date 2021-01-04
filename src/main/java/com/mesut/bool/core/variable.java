package com.mesut.bool.core;

import java.util.ArrayList;
import java.util.List;

public class variable extends func {
    String name;

    public variable(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }

    @Override
    protected String toString2() {
        return name;
    }

    @Override
    public int total() {
        return 1;
    }

    @Override
    public cons get(variable[] v, cons[] c) {
        for (int i = 0; i < v.length; i++) {
            if (v[i].name.equals(name)) {
                return c[i];
            }
        }
        return null;
    }

    @Override
    protected boolean eq2(func other) {
        variable var = (variable) other;
        return name.equals(var.name);// list func may fail
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + name.hashCode();
        return hash;

    }

    @Override
    public List<variable> list() {
        List<variable> list = new ArrayList<>();
        list.add(this);
        return list;

    }
}
