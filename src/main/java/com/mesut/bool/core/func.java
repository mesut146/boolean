package com.mesut.bool.core;

import com.mesut.bool.operators.*;
import com.mesut.bool.parser.Lexer;
import com.mesut.bool.parser.Parser;

import java.io.StringReader;
import java.util.*;

public abstract class func {
    public List<func> list = new ArrayList<>();// list of elements
    TruthTable tt = null;

    @Override
    public final String toString() {
        return toString2();
    }

    // internal printing
    protected abstract String toString2();

    public func simplify(){return this;}

    public func and(func f) {
        return new and(this, f).simplify();
    }

    public func or(func f) {
        return new or(this, f).simplify();
    }

    public func xor(func f) {
        return new xor(this, f).simplify();
    }

    // invert
    public func not(){
        return new not(this);
    }

    public boolean isAnd() {
        return this instanceof and;
    }

    public boolean isOr() {
        return this instanceof or;
    }

    public boolean isXor() {
        return this instanceof xor;
    }

    public boolean isNand() {
        return this instanceof nand;
    }

    public boolean isNor() {
        return this instanceof nor;
    }

    public boolean isXnor() {
        return this instanceof xnor;
    }

    public boolean isCons() {
        return this instanceof cons;
    }

    public boolean isVar() {
        return this instanceof variable;
    }

    public boolean isNot(){
        return this instanceof not;
    }

    public not asNot(){
        return (not)this;
    }

    public cons asCons() {
        return (cons) this;
    }

    public boolean isLow() {
        return isCons() && !asCons().value;
    }

    public boolean isHigh() {
        return isCons() && asCons().value;
    }

    public abstract cons get(variable[] v, cons[] c);

    public cons get(List<variable> v, List<cons> c) {
        return get(v.toArray(new variable[0]), c.toArray(new cons[0]));
    }

    public cons get(variable v, cons c) {
        return get(new variable[]{v}, new cons[]{c});
    }

    public func get(String str) {
        String[] groups = str.split(",");
        variable[] vars = new variable[groups.length];
        cons[] cons = new cons[groups.length];
        String[] assign;
        for (int i = 0; i < groups.length; i++) {
            assign = groups[i].split("=");
            vars[i] = new variable(assign[0]);
            cons[i] = new cons(Integer.parseInt(assign[1]));
        }
        return get(vars, cons);
    }

    public variable asVar() {
        return (variable) this;
    }

    public TruthTable truthTable() {
        if (tt == null) {
            tt = new TruthTable(this);
            tt.calc();
        }
        return tt;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof func)) {
            return false;
        }
        return eq2((func) other);
    }

    public final boolean eq(func v) {
        return super.getClass() == v.getClass() && eq2(v);
    }

    // internal equals
    protected abstract boolean eq2(func v);

    // compare two list by all elements
    public static boolean isEq(List<func> l1, List<func> l2) {
        int l;
        if ((l = l1.size()) != l2.size()) {
            return false;
        }
        boolean[] b = new boolean[l];
        boolean u;
        for (int i = 0; i < l; i++) {
            func p = l1.get(i);
            u = false;
            for (int j = 0; j < l; j++) {
                if (!b[j] && p.eq(l2.get(j))) {
                    b[j] = true;
                    u = true;
                    break;
                }
            }
            if (!u) {
                return false;
            }
        }
        return true;
    }

    protected static List<func> free() {
        return new ArrayList<func>();
    }

    public static func parse(String expr) {
        return new Parser(new Lexer(expr)).expr();
    }

    // total variable count
    public abstract int total();

    // list of variables
    public abstract List<variable> list();

    // public abstract func cnf();

    public static <T> List<T> asList(T[] array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    public static <T> List<T> asList(Set<T> set) {
        return new ArrayList<>(set);
    }

    public static int find(List<?> list, Class<?> target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass() == target) {
                return i;
            }
        }
        return -1;
    }

    // cover with paranthesis
    public String top() {
        if(isVar()){
           return toString();
        }
        return "(" + toString() + ")";
    }

    // without i th element
    public List<func> wout(int i) {
        List<func> copy = new ArrayList<>(list);
        copy.remove(i);
        return copy;
    }

    // reconstruct with only and/or
    public func alternate() {
        return this;
    }


    // sort by var
    public static void sort2(List<variable> list) {
        Collections.sort(list, new Comparator<variable>() {
            @Override
            public int compare(variable v1, variable v2) {
                return v1.getName().compareTo(v2.getName());
            }
        });
    }

    // sort by var only
    public static void sort(List<func> list) {
        Collections.sort(list, new Comparator<func>() {
            @Override
            public int compare(func v1, func v2) {
                if (v1.isVar() && v2.isVar()) {
                    return v1.asVar().getName().compareTo(v2.asVar().getName());
                }
                return 0;
            }
        });
    }

    // cast list,T to E
    <T, E> List<E> cst(List<T> list, Class<E> target) {
        List<E> result = new ArrayList<>();
        for (T term : list) {
            result.add((E) term);
        }
        return result;
    }

}
