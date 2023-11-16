package com.mesut.bool;

import java.util.Set;

public class Not extends func {

    func f;

    public Not(func f) {
        this.f = f;
    }

    @Override
    public String toString() {
        if (Config.notMode == Config.NotMode.BANG) {
            return "!" + f.top();
        }
        else if (Config.notMode == Config.NotMode.TILDE) {
            return "~" + f.top();
        }
        else if (Config.notMode == Config.NotMode.QUOTE) {
            return f.top() + "'";
        }
        return "not " + f.top();
    }

    @Override
    public String top() {
        if (f.isVar()) {
            return toString();
        }
        return super.top();
    }

    @Override
    public func simplify() {
        if (f.isNot()) {
            return f.asNot().f;
        }
        return this;
    }

    @Override
    public func not() {
        //return new not(f);
        return f;
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        return (Cons) f.get(v, c).not();
    }

    @Override
    protected boolean eq2(func v) {
        return false;
    }

    @Override
    public void vars(Set<Variable> set) {
        f.vars(set);
    }
}
