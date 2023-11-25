package com.mesut.bool;

import java.util.List;

public class Xor extends func {

    public Xor(func... array) {
        list = asList(array);
    }

    public Xor(List<func> list) {
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
                sb.append(Config.xorMode.str());
            }
        }
        return sb.toString();
    }

    @Override
    public func alternate() {
        // a' And b or a And b'
        func a = list.get(0);
        func b = list.size() == 2 ? list.get(1) : new Xor(wout(0));

        a = a.alternate();
        b = b.alternate();

        func left = a.and(b.not());
        func right = a.not().and(b);
        return left.or(right);
    }

    @Override
    public func not() {
        return new Xnor(list);
        // return alternate().not();
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        return alternate().get(v, c);
    }
}
