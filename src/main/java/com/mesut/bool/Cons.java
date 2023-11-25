package com.mesut.bool;

public class Cons extends func {
    public static final Cons LOW = new Cons(false);
    public static final Cons HIGH = new Cons(true);
    public static final Cons ZERO = LOW, ONE = HIGH;
    public final boolean value;

    public Cons(boolean value) {
        this.value = value;
    }

    public Cons(int value) {
        this.value = (value == 1);
    }

    public Cons(char chr) {
        this.value = chr == '1';
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }

    @Override
    public func not() {
        return new Cons(!value);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Cons) {
            return value == ((Cons) other).value;
        }
        return false;
    }

    @Override
    public Cons get(Variable[] v, Cons[] c) {
        return this;
    }

    public boolean getValue() {
        return value;
    }
}
