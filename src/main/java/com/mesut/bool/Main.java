package com.mesut.bool;


public class Main {

    static void usage() {
        System.err.println("java -jar boolean.jar [-pos | -sop | -tt | -alter | -k[arnaugh]] <expr>");
    }

    static boolean is_zero_ones(String str) {
        return str.matches("[01][01].*");
    }

    static String[] build_vars(String str) {
        int len = (int) (Math.log(str.length()) / Math.log(2));
        String[] arr = new String[len];
        for (int i = 0; i < len; i++) {
            arr[i] = Character.toString((char) ('A' + i));
        }
        return arr;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
            return;
        }
        String cmd = args[0];
        String in = args[1];
        switch (cmd) {
            case "-pos":
                if (is_zero_ones(in)) {
                    System.out.println(func.parsePOS(in, build_vars(in)));
                } else {
                    System.out.println(func.parse(in).pos());
                }
                break;
            case "-sop":
                if (is_zero_ones(in)) {
                    System.out.println(func.parseSOP(in, build_vars(in)));
                } else {
                    System.out.println(func.parse(in).sop());
                }
                break;
            case "-tt":
                System.out.println(func.parse(in).truthTable());
                break;
            case "-alter":
                System.out.println(func.parse(in).alternate());
                break;
            case "-k":
            case "-karnaugh":
                func f;
                if (is_zero_ones(in)) {
                    f = func.parseSOP(in, build_vars(in));
                } else {
                    f = func.parse(in);
                }
                System.out.println(Karnaugh.solve(f));
                break;
            default:
                System.err.println("invalid command: " + cmd);
                usage();
        }
    }
}
