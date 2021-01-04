package com.mesut.bool.operators;

import com.mesut.bool.core.*;

import java.util.*;

public class nor extends func {

    public nor(func... arg) {
        this.list = asList(arg);
    }

    public nor(List<func> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    protected String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (!term.isCons()  && !term.isVar()) {
                sb.append(term.top());
            } else {
                sb.append(term);
            }
            if (i < list.size() - 1) {
                sb.append(" nor ");
            }
        }
        return sb.toString();
    }


    @Override
    public func not() {
        return new or(list);
    }

    @Override
    public cons get(variable[] v, cons[] c) {
        return alternate().get(v,c);
    }

    @Override
    protected boolean eq2(func other) {
        return isEq(list,other.list);
    }

    @Override
    public int total() {
        int total = 0;
        for (func term : list) {
            total += term.total();
        }
        return total;
    }

    @Override
    public List<variable> list() {
        Set<variable> result = new HashSet<>();
        for (func term : list) {
            result.addAll(term.list());
        }
        return asList(result);
    }

    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func p : this.list) {
            list.add(p.not().alternate());
        }
        return new and(list);
    }

}
