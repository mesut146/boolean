package com.mesut.bool.core;

import java.util.ArrayList;
import java.util.List;

public class cons extends func {
    public static final cons LOW = new cons(false);
    public static final cons HIGH = new cons(true);
    public static final cons ZERO = LOW, ONE = HIGH;
    boolean value;

    public cons(boolean value) {
        this.value = value;
    }

    public cons(int value) {
        this.value = (value == 1);
    }

    public cons(char chr) {
        this.value = chr == '1';
    }

    @Override
    protected String toString2() {
        return value ? "1" : "0";
    }

    @Override
    public cons simplify() {
        return this;
    }

    @Override
    public func not() {
        return new cons(!value);
    }

    @Override
    protected boolean eq2(func v) {
        return value == v.asCons().value;
    }

    @Override
    public int total() {
        return 0;
    }

    @Override
    public cons get(var[] v, cons[] c) {
        return this;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public List<var> list() {
        return new ArrayList<var>();
    }
}
