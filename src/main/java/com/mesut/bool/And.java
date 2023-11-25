package com.mesut.bool;

import java.util.ArrayList;
import java.util.List;

public class And extends func{

    public And(func... list) {
        this.list = asList(list);
    }

    public And(List<func> list) {
        this.list.addAll(list);
    }

    public static func make(List<func> list) {
        if (list.size() == 1) return list.get(0);
        return new And(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (term.isCons() || term.isVar() || term.isAnd()) {
                sb.append(term);
            } else {
                sb.append(term.top());
            }

            if (i < list.size() - 1) {
                sb.append(Config.andMode.str());
            }
        }
        return sb.toString();
    }

    @Override
    public func simplify() {
        List<func> l = new ArrayList<>();
        // a(bc)=abc,a.1=a,a.0=0
        for (func v : list) {
            if (v.isAnd()) {
                l.addAll(v.list);
            } else if (v.isCons()) {
                if (v.isLow()) {
                    return Cons.LOW;
                } else {
                    // no need to add 1
                    continue;
                }
            } else {
                l.add(v);
            }
        }
        list.clear();
        list.addAll(l);
        if (list.isEmpty()) {
            return Cons.HIGH;
        } else if (list.size() == 1) {
            return list.get(0);
        }
        return this;
    }

    @Override
    public func not() {
        return new Nand(list);
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        for (func term : list) {
            if (!term.get(v, c).getValue()) {
                return Cons.LOW;
            }
        }
        return Cons.HIGH;
    }

    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : this.list) {
            list.add(term.alternate());
        }
        return new And(list);
    }
}
