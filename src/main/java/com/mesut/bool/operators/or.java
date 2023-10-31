package com.mesut.bool.operators;

import com.mesut.bool.Config;
import com.mesut.bool.core.cons;
import com.mesut.bool.core.func;
import com.mesut.bool.core.variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class or extends func {
    public or(func... f) {
        this.list = asList(f);
    }

    public or(List<func> f) {
        this.list.addAll(f);
    }

    @Override
    protected String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (!term.isCons() && !term.isOr() && !term.isVar() && !term.isAnd()) {
                sb.append(term.top());
            }
            else {
                sb.append(term);
            }
            if (i < list.size() - 1) {
                sb.append(Config.orMode.str());
            }
        }
        return sb.toString();
    }

    @Override
    public func simplify() {
        List<func> l = free();
        // a+(b+c)=a+b+c
        // a+1=1,a+0=a
        for (func v : list) {
            if (v.isOr()) {
                l.addAll(v.list);
            }
            else if (v.isCons()) {
                if (v.isHigh()) {
                    return cons.HIGH;
                }
                continue;
            }
            else {
                l.add(v);
            }
        }
        list.clear();
        list.addAll(l);
        if (list.size() == 0) {
            return cons.LOW;
        }
        if (list.size() == 1) {
            return list.get(0);
        }

        if (true) {
            return this;
        }
        // todo
        var();
        if (list.size() == 1) {
            return list.get(0);
        }
        s();
        return this;
    }

    List<variable> toVar(func l) {
        List<variable> l1 = new ArrayList<>();
        if (l.isVar()) {
            l1.add((variable) l);
            return l1;
        }
        l1.addAll((List<variable>) (List<?>) l.list);
        variable.sort2(l1);

        return l1;
    }

    private void s() {
        // a+ab=a
        for (int i = 0; i < list.size(); i++) {
            func v = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                func p = list.get(j);
                List<variable> l1 = toVar(v);
                List<variable> l2 = toVar(p);
                if (Math.abs(l1.size() - l2.size()) == 1) {
                    if (l1.size() < l2.size() && l2.containsAll(l1)) {
                        list.remove(j);
                        s();
                        return;
                    }
                    else if (l2.size() < l1.size() && l1.containsAll(l2)) {
                        list.remove(i);
                        s();
                        return;
                    }
                }
            }
        }
    }

    private void var() {
        boolean b[] = new boolean[list.size()];
        List<func> l = new ArrayList<>();
        // a+a=a
        for (int i = 0; i < list.size(); i++) {
            func v = list.get(i);
            if (!b[i]) {
                l.add(v);
                for (int j = i + 1; j < list.size(); j++) {
                    if (v.eq(list.get(j))) {
                        b[j] = true;
                    }
                }
            }
        }
        list = l;
    }

    @Override
    public func not() {
        // return new nor(f);
        func a = list.get(0);
        func b = list.size() == 2 ? list.get(1) : new or(wout(0));
        return a.not().and(b.not());
    }

    @Override
    protected boolean eq2(func v) {
        return isEq(list, v.list);
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
    public cons get(variable[] v, cons[] c) {
        for (func term : list) {
            if (term.get(v, c).getValue()) {
                return cons.HIGH;
            }
        }
        return cons.LOW;
    }

    @Override
    public List<variable> list() {
        Set<variable> result = new HashSet<>();
        for (func term : list) {
            result.addAll(term.list());
        }
        List<variable> list = asList(result);
        sort2(list);
        return list;
    }

    @Override
    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : this.list) {
            list.add(term.alternate());
        }
        return new or(list);
    }


}
