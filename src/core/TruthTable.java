package core;

import java.util.*;

public class TruthTable {
    List<var> vars;
    List<List<cons>> in;
    List<List<cons>> out;
    func[] f;

    public TruthTable(func... f) {
        this.f = f;
        in = new ArrayList<>();
        out = new ArrayList<>();
        vars = new ArrayList<>();
    }

    public void calc() {
        HashSet<var> set = new HashSet<>();
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
            for (int j = 0; j < f.length; j++) {
                ls.add(f[j].get(vars.toArray(new var[0]), lc.toArray(new cons[0])));
            }
            out.add(ls);
        }

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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.length; i++) {
            sb.append("F" + (i + 1) + "=" + f[i].toString() + "\n");
        }
        sb.append(join(vars) + " | ");
        for (int i = 0; i < f.length; i++) {
            sb.append("F" + (i + 1));
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

            sb.append(" | " + join(out.get(i)));
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
