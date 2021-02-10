package com.mesut.bool.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TruthTable {
    List<variable> vars;
    List<List<cons>> in;
    List<List<cons>> out;
    func[] f;
    boolean calculated = false;

    public TruthTable(func... f) {
        this.f = f;
        in = new ArrayList<>();
        out = new ArrayList<>();
        vars = new ArrayList<>();
    }

    public void calc() {
        HashSet<variable> set = new HashSet<>();
        for (func term : f) {
            set.addAll(term.list());
        }
        vars.addAll(set);
        func.sort2(vars);

        int rows = (int) Math.pow(2, vars.size());
        for (int i = 0; i < rows; i++) {
            List<cons> lc = new ArrayList<>();
            for (char c : fix(Integer.toBinaryString(i), vars.size()).toCharArray()) {
                lc.add(new cons(c));
            }
            // System.out.println(lc);
            in.add(lc);
            List<cons> ls = new ArrayList<>();
            for (func func : f) {
                ls.add(func.get(vars.toArray(new variable[0]), lc.toArray(new cons[0])));
            }
            out.add(ls);
        }
        calculated = true;
    }

    // add 0's to str so that total is the final length
    public static String fix(String str, int total) {
        int remaining = Math.abs(total - str.length());
        for (int i = 0; i < remaining; i++) {
            str = "0" + str;
        }
        return str;
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
}
