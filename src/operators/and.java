package operators;

import java.util.*;

public class and extends func {

    public and(func... list) {
        this.f = asList(list);
    }

    public and(List<func> list) {
        this.f.addAll(list);
    }

    @Override
    String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);
            if (!term.isCons() && !term.isAnd() && !term.isVar()) {
                sb.append(term.top());
            } else {
                sb.append(term);
            }

            if (i < f.size() - 1) {
                sb.append(andDel);
            }
        }
        return sb.toString();
    }

    @Override
    public func simplify() {
        List<func> l = new ArrayList<>();
        // a(bc)=abc,a.1=a,a.0=0
        for (func v : f) {
            if (v.isAnd()) {
                l.addAll(v.f);
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
        f.clear();
        f.addAll(l);
        if (f.size() == 0) {
            return cons.HIGH;
        } else if (f.size() == 1) {
            return f.get(0);
        }
        if (true) {
            return this;
        }
        // a(b+c)=ab+ac
        l.clear();
        int k = find(f, or.class);
        if (k != -1) {
            func o = f.get(k);
            List<func> p = free();
            p.addAll(f);
            p.remove(k);
            func a = new and(p).simplify();
            func t;
            for (int j = 0; j < o.f.size(); j++) {
                t = o.f.get(j).and(a);
                l.add(t);
            }
            return new or(l).simplify();
        }
        // aa=a,aa'=0
        boolean b[] = new boolean[f.size()];
        l = free();
        for (int i = 0; i < f.size(); i++) {
            func v = f.get(i);
            if (!b[i]) {
                l.add(v);
                for (int j = i + 1; j < f.size(); j++) {
                    if (v.eq(f.get(j))) {
                        b[j] = true;
                    } else if (v.eq(f.get(j).not())) {
                        return cons.LOW;
                    }
                }
            }
        }
        f.clear();
        f.addAll(l);

        sort(f);

        for (func p : f) {
            if (!p.isVar()) {
                // System.out.println("f="+f);
            }
        }
        return this;
    }

    @Override
    public func not() {
        return new nand(f);
        /*
         * func a=f.get(0); func b=f.size()==2?f.get(1):new or(wout(0)); return a.not().or(b.not());
         */
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
            if (!term.get(v, c).value) {
                return cons.LOW;
            }
        }
        return cons.HIGH;
    }

    @Override
    public List<var> list() {
        Set<var> result = new HashSet<>();
        for (func term : f) {
            result.addAll(term.list());
        }
        return asList(result);
    }

    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func term : f) {
            list.add(term.alternate());
        }
        return new and(list);
    }
}
