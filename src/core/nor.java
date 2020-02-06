package core;

import java.util.*;

public class nor extends func {

    public nor(func... arg) {
        this.f = asList(arg);
    }

    public nor(List<func> list) {
        this.f = new ArrayList<>(list);
    }

    @Override
    String toString2() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            s.append(f.get(i));
            if (i < f.size() - 1) {
                s.append(norDel);
            }
        }
        return s.toString();
    }

    @Override
    public func simplify() {
        // TODO: Implement this method
        return this;
    }

    @Override
    public func not() {
        // TODO: Implement this method
        return new or(f);
    }

    @Override
    public cons get(var[] v, cons[] c) {
        // TODO: Implement this method
        return null;
    }

    @Override
    protected boolean eq2(func v) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public int total() {
        // TODO: Implement this method
        return 0;
    }

    @Override
    public List<var> list() {
        // TODO: Implement this method
        return null;
    }

    public func alternate() {
        List<func> list = new ArrayList<>();
        for (func p : f) {
            list.add(p.not().alternate());
        }
        return new and(list);
    }

}
