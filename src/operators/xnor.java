package operators;

import java.util.*;

public class xnor extends func {

    public xnor(func... array) {
        f = asList(array);
    }

    public xnor(List<func> list) {
        f.addAll(list);
    }

    @Override
    String toString2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);
            if (!term.isCons() && !term.isXnor() && !term.isVar()) {
                sb.append(term.top());
            } else {
                sb.append(term);
            }
            if (i < f.size() - 1) {
                sb.append(xnorDel);
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
        return new xor(f);
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
        List<func> list = new ArrayList<>();
        for (func term : f) {
            list.add(term.alternate());
        }
        return new xor(list).alternate().not();
    }
}
