package com.mesut.bool.operators;

import com.mesut.bool.Config;
import com.mesut.bool.core.cons;
import com.mesut.bool.core.func;
import com.mesut.bool.core.variable;

import java.util.List;

public class not extends func {

    func f;

    public not(func f) {
        this.f = f;
    }

    @Override
    protected String toString2() {
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
        return new not(f);
    }

    @Override
    public cons get(variable[] v, cons[] c) {
        return (cons) f.get(v, c).not();
    }

    @Override
    protected boolean eq2(func v) {
        return false;
    }

    @Override
    public int total() {
        return 0;
    }

    @Override
    public List<variable> list() {
        return null;
    }
}
