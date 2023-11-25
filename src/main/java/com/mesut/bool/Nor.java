package com.mesut.bool;

import java.util.ArrayList;
import java.util.List;

public class Nor extends func {

    public Nor(func... arg) {
        this.list = asList(arg);
    }

    public Nor(List<func> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (term.isCons() || term.isVar()) {
                sb.append(term);
            } else {
                sb.append(term.top());
            }
            if (i < list.size() - 1) {
                sb.append(" nor ");
            }
        }
        return sb.toString();
    }

    @Override
    public func not() {
        return new Or(list);
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        return alternate().get(v, c);
    }


    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func p : this.list) {
            list.add(p.not().alternate());
        }
        return new And(list);
    }

}
