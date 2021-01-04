package com.mesut.bool.operators;

import com.mesut.bool.Config;
import com.mesut.bool.core.*;

import java.util.*;

public class xor extends func {

    public xor(func... array) {
        list = asList(array);
    }

    public xor(List<func> list) {
        this.list.addAll(list);
    }

    @Override
    protected boolean eq2(func other) {
        return isEq(list, other.list);
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
    public func simplify() {
        return this;
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
                sb.append(Config.xorMode.str());
            }
        }
        return sb.toString();
    }

    @Override
    public func alternate() {
        // a' and b or a and b'
        func a = list.get(0);
        func b = list.size() == 2 ? list.get(1) : new xor(wout(0));

        // System.out.println("a=" + a + " b=" + b);

        a = a.alternate();
        b = b.alternate();
        // System.out.println("a.alter=" + a + " b.alter=" + b);
        func left = a.and(b.not());
        func right = a.not().and(b);
        // System.out.println("l=" + left + " r=" + right + " or=" + left.or(right));
        return left.or(right);
    }

    @Override
    public func not() {
        return new xnor(list);
        // return alternate().not();
    }

    @Override
    public cons get(variable[] v, cons[] c) {
        return alternate().get(v, c);
    }

    @Override
    public List<variable> list() {
        Set<variable> result = new HashSet<>();
        for (func term : list) {
            result.addAll(term.list());
        }
        return asList(result);
    }
}
