package com.mesut.bool;

import java.util.ArrayList;
import java.util.List;

public class Or extends func {
    public Or(func... f) {
        this.list = asList(f);
    }

    public Or(List<func> f) {
        this.list.addAll(f);
    }

    public static func make(List<func> list) {
        if (list.size() == 1) return list.get(0);
        return new Or(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (term.isCons() || term.isVar() || term.isOr() || term.isAnd()) {
                sb.append(term);
            } else {
                sb.append(term.top());
            }
            if (i < list.size() - 1) {
                sb.append(Config.orMode.str());
            }
        }
        return sb.toString();
    }

    @Override
    public func simplify() {
        List<func> l = new ArrayList<>();
        // a+(b+c)=a+b+c
        // a+1=1,a+0=a
        for (func v : list) {
            if (v.isOr()) {
                l.addAll(v.list);
            } else if (v.isCons()) {
                if (v.isHigh()) {
                    return Cons.HIGH;
                }
            } else {
                l.add(v);
            }
        }
        list.clear();
        list.addAll(l);
        if (list.isEmpty()) {
            return Cons.LOW;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        return this;
    }


    @Override
    public func not() {
        // return new nor(f);
        func a = list.get(0);
        func b = list.size() == 2 ? list.get(1) : new Or(wout(0));
        return a.not().and(b.not());
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        for (func term : list) {
            if (term.get(v, c).getValue()) {
                return Cons.HIGH;
            }
        }
        return Cons.LOW;
    }

    @Override
    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : this.list) {
            list.add(term.alternate());
        }
        return new Or(list);
    }
}
