package com.mesut.bool;

import java.util.ArrayList;
import java.util.List;

public class Xnor extends func {

    public Xnor(func... array) {
        list = asList(array);
    }

    public Xnor(List<func> list) {
        this.list.addAll(list);
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
                sb.append(" xnor ");
            }
        }
        return sb.toString();
    }

    @Override
    public func not() {
        return new Xor(list);
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        return alternate().get(v, c);
    }


    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : this.list) {
            list.add(term.alternate());
        }
        return new Xor(list).alternate().not().alternate();
    }
}
