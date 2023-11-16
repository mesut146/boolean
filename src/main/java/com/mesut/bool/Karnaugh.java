package com.mesut.bool;

import java.util.ArrayList;
import java.util.List;

public class Karnaugh {
    public TruthTable tt;
    int rowVarSize, colVarSize;
    Gray[] r;//row vars
    Gray[] c;//col vars
    int cellCountx, cellCounty;
    int[][] map;//data
    List<Group> groups = new ArrayList<>();

    public Karnaugh(func f) {
        tt = new TruthTable(f);
        tt.calc();
        init();
    }

    void init() {
        int len = tt.vars.size();

        rowVarSize = len / 2;
        colVarSize = len - rowVarSize;

        cellCountx = (int) Math.pow(2, rowVarSize);
        cellCounty = (int) Math.pow(2, colVarSize);

        r = new Gray[cellCountx];
        c = new Gray[cellCounty];
        Gray.set(r, rowVarSize);
        Gray.set(c, colVarSize);

        //init map
        map = new int[cellCountx][cellCounty];
        for (int i = 0; i < cellCountx; i++) {
            for (int j = 0; j < cellCounty; j++) {
                Gray gray = new Gray(len);
                System.arraycopy(r[i].arr, 0, gray.arr, 0, rowVarSize);
                System.arraycopy(c[j].arr, 0, gray.arr, rowVarSize, colVarSize);
                map[i][j] = tt.out.get(gray.toInt()).get(0).value ? 1 : 0;
            }
        }
    }

    static class Gray {
        byte[] arr;

        public Gray(int len) {
            this.arr = new byte[len];
        }

        void init(int i) {
            String s = TruthTable.fix(Integer.toBinaryString(i ^ (i >> 1)), arr.length);
            for (int j = 0; j < arr.length; j++) {
                arr[j] = (byte) (s.charAt(j) - '0');
            }
        }

        static void set(Gray[] b, int len) {
            int x = b.length;
            for (int i = 0; i < x; i++) {
                b[i] = new Gray(len);
                b[i].init(i);
            }
        }

        int toInt() {
            return Integer.parseInt(toString(), 2);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (byte b : arr) {
                sb.append(b);
            }
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tt.vars.size(); i++) {
            if (i == rowVarSize) {
                sb.append(",");
            }
            sb.append(tt.vars.get(i));
        }
        sb.append("\n");
        //col vars
        sb.append("    ");
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].arr.length; j++) {
                sb.append(c[i].arr[j]);
            }
            sb.append(" | ");
        }
        sb.append("\n");
        //rows
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].arr.length; j++) {
                sb.append(r[i].arr[j]);
            }
            sb.append("   ");
            for (int j = 0; j < c.length; j++) {
                sb.append(map[i][j]);
                sb.append("   ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static func solve(func f) {
        return new Karnaugh(f).solve();
    }

    public func solve() {
        groups.clear();
        //mark all 1 width or 1 height groups
        for (int i = 0; i < cellCountx; i++) {
            for (int j = 0; j < cellCounty; j++) {
                if (map[i][j] == 0) continue;
                //expand right
                int w = 1, h = 1;
                while (j + w < cellCounty && map[i][j + w] == 1) {
                    w++;
                }
                //scale to power of two
                while (!isPowerOf2(w * h)) {
                    w--;
                }
                Group g1 = new Group(i, j, w, h);
                w = 1;
                //expand down
                while (i + h < cellCountx && map[i + h][j] == 1) {
                    h++;
                }
                //scale to power of two
                while (!isPowerOf2(w * h)) {
                    h--;
                }
                if (g1.w == 1 && h > 1) {
                    groups.add(new Group(i, j, w, h));
                } else {
                    groups.add(g1);
                }
            }
        }
        eliminate();
        expand();
        //todo, dont care
        //build func
        List<func> res = new ArrayList<>();
        for (Group g : groups) {
            List<func> terms = new ArrayList<>();
            grayDown(terms, g);
            grayRight(terms, g);
            res.add(new And(terms));
        }
        return new Or(res);
    }

    void grayDown(List<func> terms, Group g) {
        Gray last = r[g.x];
        boolean[] changed = new boolean[last.arr.length];
        for (int h = g.x + 1; h < g.x + g.h; h++) {
            Gray gray = r[h];
            //compare gray
            for (int a = 0; a < last.arr.length; a++) {
                if (last.arr[a] != gray.arr[a]) {
                    changed[a] = true;
                }
            }
        }
        //pick not changing row vars
        for (int a = 0; a < changed.length; a++) {
            if (changed[a]) continue;
            if (last.arr[a] == 1) {
                terms.add(tt.vars.get(a));
            } else {
                terms.add(tt.vars.get(a).not());
            }
        }
    }

    void grayRight(List<func> terms, Group g) {
        Gray last = c[g.y];
        boolean[] changed = new boolean[last.arr.length];
        for (int h = g.y + 1; h < g.y + g.w; h++) {
            Gray gray = c[h];
            //compare gray
            for (int a = 0; a < last.arr.length; a++) {
                if (last.arr[a] != gray.arr[a]) {
                    changed[a] = true;
                }
            }
        }
        //pick not changing row vars
        for (int a = 0; a < changed.length; a++) {
            if (changed[a]) continue;
            if (last.arr[a] == 1) {
                terms.add(tt.vars.get(a + rowVarSize));
            } else {
                terms.add(tt.vars.get(a + rowVarSize).not());
            }
        }
    }

    //expand
    void expand() {
        //todo check power of 2
        for (Group g : groups) {
            //try to grow g by reflecting
            if (g.w == 1) {//reflect right
                int w = 2;
                for (; w < cellCounty; w++) {
                    if (g.y + w > cellCounty) break;
                    if (hasColZero(g.x, g.y + w - 1, w)) {
                        break;
                    }
                    //System.out.println("merge right " + g);
                }
                g.w = w - 1;
            } else if (g.h == 1) {//reflect down
                int h = 2;
                for (; h < cellCountx; h++) {
                    if (g.x + h > cellCountx) break;
                    if (hasRowZero(g.x + h - 1, g.y, h)) {
                        break;
                    }
                    //System.out.println("merge left " + g);
                }
                g.h = h - 1;
            }
        }
    }

    boolean hasRowZero(int x, int y, int w) {
        for (int c = 0; c < w; c++) {
            if (map[x][y + c] == 0) {
                return true;
            }
        }
        return false;
    }

    boolean hasColZero(int x, int y, int h) {
        for (int r = 0; r < h; r++) {
            if (map[x + r][y] == 0) {
                return true;
            }
        }
        return false;
    }

    //remove if a Group is already covered by other groups
    void eliminate() {
        for (int i = 0; i < groups.size(); i++) {
            Group g = groups.get(i);//(3,1,2,1),(3,2,2,1),(3,3,1,1)
            boolean[][] cov = new boolean[g.h][g.w];
            //is g covered by others
            for (int j = 0; j < groups.size(); j++) {
                if (i == j) continue;
                Group g2 = groups.get(j);
                //mark all intercepting cells
                for (int a = 0; a < g2.h; a++) {//down
                    for (int b = 0; b < g2.w; b++) {
                        int x1 = g2.x - g.x + a;
                        int y1 = g2.y - g.y + b;
                        if (x1 < 0 || y1 < 0) continue;
                        if (x1 < cov.length && y1 < cov[0].length) {
                            cov[x1][y1] = true;
                        }
                    }
                }
            }
            boolean covered = true;
            for (boolean[] b : cov) {
                for (boolean cell : b) {
                    if (!cell) {
                        covered = false;
                        break;
                    }
                }
            }
            if (covered) {
                groups.remove(i);
                eliminate();
                return;
            }
        }
    }

    public static boolean isPowerOf2(int n) {
        double d = Math.log(n) / Math.log(2);
        return (double) (int) d == d;
    }

    static class Group {
        int x, y, w, h;

        public Group(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d,%d,%d)", x, y, w, h);
        }

    }
}



