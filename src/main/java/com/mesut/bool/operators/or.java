package com.mesut.bool.operators;

import com.mesut.bool.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class or extends func {
    public or(func... f) {
        this.f = asList(f);
    }

    public or(List<func> f) {
        this.f.addAll(f);
    }

    @Override
    protected String toString2() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            func p = f.get(i);
            if (!p.isCons() && !p.isOr() && !p.isVar()) {
                s.append(p.top());
            } else {
                s.append(p);
            }
            if (i < f.size() - 1) {
                s.append(orDel);
            }
        }
        return s.toString();
    }

    @Override
    public func simplify() {
        List<func> l = free();
        // a+(b+c)=a+b+c
        // a+1=1,a+0=a
        for (func v : f) {
            if (v.isOr()) {
                l.addAll(v.f);
            } else if (v.isCons()) {
                if (v.isHigh()) {
                    return cons.HIGH;
                }
                continue;
            } else {
                l.add(v);
            }
        }
        f.clear();
        f.addAll(l);
        if (f.size() == 0) {
            return cons.LOW;
        }
        if (f.size() == 1) {
            return f.get(0);
        }

        if (true) {
            return this;
        }
        // todo
        var();
        if (f.size() == 1) {
            return f.get(0);
        }
        s();
        return this;
    }

    List<var> toVar(func l) {
        List<var> l1 = new ArrayList<>();
        if (l.isVar()) {
            l1.add((var) l);
            return l1;
        }
        l1.addAll((List<var>) (List<?>) l.f);
        var.sort2(l1);

        return l1;
    }

    private void s() {
        // a+ab=a
        for (int i = 0; i < f.size(); i++) {
            func v = f.get(i);
            for (int j = i + 1; j < f.size(); j++) {
                func p = f.get(j);
                List<var> l1 = toVar(v);
                List<var> l2 = toVar(p);
                if (Math.abs(l1.size() - l2.size()) == 1) {
                    if (l1.size() < l2.size() && l2.containsAll(l1)) {
                        f.remove(j);
                        s();
                        return;
                    } else if (l2.size() < l1.size() && l1.containsAll(l2)) {
                        f.remove(i);
                        s();
                        return;
                    }
                }
            }
        }
    }

    private void var() {
        boolean b[] = new boolean[f.size()];
        List<func> l = new ArrayList<>();
        // a+a=a
        for (int i = 0; i < f.size(); i++) {
            func v = f.get(i);
            if (!b[i]) {
                l.add(v);
                for (int j = i + 1; j < f.size(); j++) {
                    if (v.eq(f.get(j))) {
                        b[j] = true;
                    }
                }
            }
        }
        f = l;
    }

    @Override
    public func not() {
        // return new nor(f);
        func a = f.get(0);
        func b = f.size() == 2 ? f.get(1) : new or(wout(0));
        return a.not().and(b.not());
    }

    @Override
    protected boolean eq2(func v) {
        return isEq(f, v.f);
    }

    @Override
    public int total() {
        int total = 0;
        for (func term : f) {
            total += term.total();
        }
        return total;
    }

    @Override
    public cons get(var[] v, cons[] c) {
        for (func term : f) {
            if (term.get(v, c).getValue()) {
                return cons.HIGH;
            }
        }
        return cons.LOW;
    }

    @Override
    public List<var> list() {
        Set<var> result = new HashSet<>();
        for (func term : f) {
            result.addAll(term.list());
        }
        System.out.println("or.list=" + result);
        List<var> list = asList(result);
        sort2(list);
        return list;
    }

    @Override
    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : f) {
            list.add(term.alternate());
        }
        return new or(list);
    }



}
