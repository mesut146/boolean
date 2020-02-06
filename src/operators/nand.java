package operators;

import java.util.*;

public class nand extends func {

    public nand(func... array) {
        this.f = asList(array);
    }

    public nand(List<func> list) {
        this.f = new ArrayList<>(list);
    }

    @Override
    String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);
            if (!term.isCons() && !term.isNand() && !term.isVar()) {
                sb.append(term.top());
            } else {
                sb.append(term);
            }
            if (i < f.size() - 1) {
                sb.append(nandDel);
            }
        }
        return sb.toString();
    }

    @Override
    public func simplify() {
        return this;
    }

    @Override
    public func not() {
        return new and(f);
    }

    @Override
    public cons get(var[] v, cons[] c) {
        return alternate().get(v, c);
    }

    @Override
    protected boolean eq2(func other) {
        return isEq(f, other.f);
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
    public List<var> list() {
        Set<var> result = new HashSet<>();
        for (func term : f) {
            result.addAll(term.list());
        }
        return asList(result);
    }

    public func alternate() {
        // System.out.println("altering " + this);
        List<func> list = new ArrayList<>();
        for (func term : f) {
            list.add(term.not().alternate());
        }
        return new or(list);
    }

}
