package com.mesut.bool;

import java.util.*;

public class TruthTable {
    public List<Variable> vars;
    public List<List<Cons>> in;
    public List<List<Cons>> out;
    public func[] f;
    boolean calculated = false;

    public TruthTable(func... f) {
        this.f = f;
        in = new ArrayList<>();
        out = new ArrayList<>();
        vars = new ArrayList<>();
    }

    public static void sortVars(List<Variable> list) {
        list.sort(Comparator.comparing(Variable::getName));
    }

    public void calc() {
        Set<Variable> set = new HashSet<>();
        for (func term : f) {
            term.vars(set);
        }
        vars.addAll(set);
        sortVars(vars);

        int rows = (int) Math.pow(2, vars.size());
        for (int i = 0; i < rows; i++) {
            List<Cons> lc = new ArrayList<>();
            for (char c : fix(Integer.toBinaryString(i), vars.size()).toCharArray()) {
                lc.add(new Cons(c));
            }
            in.add(lc);
            List<Cons> ls = new ArrayList<>();
            for (func func : f) {
                ls.add(func.get(vars.toArray(new Variable[0]), lc.toArray(new Cons[0])));
            }
            out.add(ls);
        }
        calculated = true;
    }

    // add 0's to str so that total is the final length
    public static String fix(String str, int total) {
        int remaining = Math.abs(total - str.length());
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < remaining; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        if (!calculated) {
            calc();
        }
        StringBuilder sb = new StringBuilder("\n");
        //print functions with name
        for (int i = 0; i < f.length; i++) {
            sb.append("F").append(i + 1).append("=").append(f[i]).append("\n");
        }
        sb.append(join(vars)).append(" | ");
        for (int i = 0; i < f.length; i++) {
            sb.append("F").append(i + 1);
            if (i < f.length - 1) {
                sb.append(" ");
            }
        }
        sb.append("\n");
        for (int i = 0; i < 2 * (vars.size() + f.length) + 2; i++) {
            sb.append("-");
        }
        sb.append("\n");
        for (int i = 0; i < in.size(); i++) {
            sb.append(join(in.get(i)));

            sb.append(" | ").append(join(out.get(i)));
            if (i < in.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    // join list with a space
    String join(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public String getOutStr() {
        StringBuilder sb = new StringBuilder();
        for (List<Cons> cons : out) {
            sb.append(cons.get(0));
        }
        return sb.toString();
    }
}
