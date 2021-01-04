package com.mesut.bool.operators;

import com.mesut.bool.Config;
import com.mesut.bool.core.*;

import java.util.*;

public class and extends func {

    public and(func... list) {
        this.list = asList(list);
    }

    public and(List<func> list) {
        this.list.addAll(list);
    }

    @Override
    protected String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (!term.isCons() && !term.isAnd() && !term.isVar()) {
                sb.append(term.top());
            } else {
                sb.append(term);
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
                    return cons.LOW;
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
        if (list.size() == 0) {
            return cons.HIGH;
        } else if (list.size() == 1) {
            return list.get(0);
        }
        if (true) {
            return this;
        }
        // a(b+c)=ab+ac
        l.clear();
        int k = find(list, or.class);
        if (k != -1) {
            func o = list.get(k);
            List<func> p = free();
            p.addAll(list);
            p.remove(k);
            func a = new and(p).simplify();
            func t;
            for (int j = 0; j < o.list.size(); j++) {
                t = o.list.get(j).and(a);
                l.add(t);
            }
            return new or(l).simplify();
        }
        // aa=a,aa'=0
        boolean b[] = new boolean[list.size()];
        l = free();
        for (int i = 0; i < list.size(); i++) {
            func v = list.get(i);
            if (!b[i]) {
                l.add(v);
                for (int j = i + 1; j < list.size(); j++) {
                    if (v.eq(list.get(j))) {
                        b[j] = true;
                    } else if (v.eq(list.get(j).not())) {
                        return cons.LOW;
                    }
                }
            }
        }
        list.clear();
        list.addAll(l);

        sort(list);

        for (func p : list) {
            if (!p.isVar()) {
                // System.out.println("f="+f);
            }
        }
        return this;
    }

    @Override
    public func not() {
        return new nand(list);
    }

    @Override
    protected boolean eq2(func v) {
        return isEq(list, v.list);
    }

    @Override
    public cons get(variable[] v, cons[] c) {
        for (func term : list) {
            if (!term.get(v, c).getValue()) {
                return cons.LOW;
            }
        }
        return cons.HIGH;
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
        for (func term : this.list) {
            list.add(term.alternate());
        }
        return new and(list);
    }
}
