package core;

import java.util.*;

public class Karnaugh
{
    boolean[][] map;//data
    var[] row, col;
    boolean[][] r;//row vars
    boolean[][] c;//col vars

    public Karnaugh(func f)
    {
        tt t = new tt(f);
        t.calc();
        System.out.println(t);
        int len = t.vars.size();
        row = new var[len / 2];
        col = new var[len - row.length];

        map = new boolean[(int) Math.pow(2, row.length)][(int) Math.pow(2, col.length)];
        r = new boolean[map.length][row.length];
        c = new boolean[map[0].length][col.length];
        for (int i = 0; i < row.length; i++)
        {
            row[i] = t.vars.get(i);
        }
        for (int i = 0; i < col.length; i++)
        {
            col[i] = t.vars.get(row.length + i);
        }
        gray(r);
        gray(c);
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {
                boolean b[] = new boolean[len];
                System.arraycopy(r[i], 0, b, 0, r[i].length);
                System.arraycopy(c[j], 0, b, r[i].length, c[j].length);
                map[i][j] = t.out.get(b2int(b)).get(0).b;
            }
        }
        //System.out.println(arr(map));
    }

    int b2int(boolean b[])
    {
        return Integer.parseInt(b2str(b, null), 2);
    }

    String b2str(boolean b[], String del)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < b.length; i++)
        {
            s.append(b[i] ? "1" : "0");
            if (del != null && i < b.length - 1)
            {
                s.append(del);
            }
        }
        return s.toString();
    }

    String arr(boolean[][] b)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < b[0].length; j++)
            {
                s.append(b[i][j] ? "1" : "0");
                if (j < b[0].length - 1)
                {
                    s.append(",");
                }
            }
            if (i < b.length - 1)
            {
                s.append("\n");
            }
        }
        return s.toString();
    }

    void gray(boolean b[][])
    {
        int x = b.length;
        int y = b[0].length;
        for (int i = 0; i < x; i++)
        {
            String s = tt.fix(Integer.toBinaryString(i ^ (i >> 1)), y);
            for (int j = 0; j < y; j++)
            {
                b[i][j] = s.charAt(j) == '1';
            }
        }
    }

    public func simplify()
    {
        System.out.println(arr(map));
        int x = map.length;
        int y = map[0].length;
        List<group> l = new ArrayList<>();
        int a, b, c, d;
        boolean[][] bb = new boolean[x][y];
        System.out.printf("x=%d y=%d\n", x, y);
        for (int i = 0; i < x; i++)
        {
            System.out.printf("i=%d\n", i);
            for (int j = 0; j < y; j++)
            {
                if (!bb[i][j] && map[i][j])
                {
                    int k = j;
                    while (k < x && map[i][k])
                    {//right horizontal
                        k++;
                        bb[i][k] = true;
                    }
                    int o=k;
                    /*while (!pow2(o-j)){
                     o--;
                     }*/
                    l.add(new group(i, j, i, o));
                    System.out.printf("g=%d %d %d %d %d\n", i, j, i, o, k);

                }
            }
        }
        System.out.println(l);
        return null;
    }

    public static boolean pow2(int n)
    {
        double d = Math.log(n) / Math.log(2);
        return (double) (int) d == d;
    }
}

class group
{
    int x,y,xx,yy;
    public group(int x, int y, int xx, int yy)
    {
        this.x = xx;
        this.y = y;
        this.xx = xx;
        this.yy = yy;
    }

    @Override
    public String toString()
    {
        return String.format("(%d,%d,%d,%d)", x, y, xx, yy);
    }


}

