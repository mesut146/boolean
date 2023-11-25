package com.mesut.bool;

import java.util.ArrayList;
import java.util.List;

public class Nand extends func {

    public Nand(func... array) {
        this.list = asList(array);
    }

    public Nand(List<func> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (term.isCons() || term.isVar() || term.isNand()) {
                sb.append(term);
            } else {
                sb.append(term.top());
            }
            if (i < list.size() - 1) {
                sb.append(" nand ");
            }
        }
        return sb.toString();
    }

    @Override
    public func not() {
        return new And(list);
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        return alternate().get(v, c);
    }


    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : this.list) {
            list.add(term.not().alternate());
        }
        return new Or(list);
    }
}
