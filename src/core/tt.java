package core;

import java.util.*;

public class tt
{
    List<var> vars;
    List<List<cons>> in;
    List<List<cons>> out;
    func[] f;

    public tt(func...f)
    {
        this.f = f;
        in = new ArrayList<>();
        out = new ArrayList<>();
        vars = new ArrayList<>();
    }

    public void calc()
    {
        HashSet<var> h=new HashSet<>();
        for (func p:f)
        {
            h.addAll(p.list());
        }
        vars.addAll(h);
        func.sort2(vars);

        int m=(int)Math.pow(2, vars.size());
        for (int i=0;i < m;i++)
        {
            List<cons> lc=new ArrayList<>();
            for (char c:fix(Integer.toBinaryString(i), vars.size()).toCharArray())
            {
                lc.add(new cons(c));
            }
            //System.out.println(lc);
            in.add(lc);
            List<cons> ls=new ArrayList<>();
            for (int j=0;j < f.length;j++)
            {
                ls.add(f[j].get(vars.toArray(new var[0]), lc.toArray(new cons[0])));
            }
            out.add(ls);
        }

    }

    public static String fix(String s, int m)
    {
        int l=Math.abs(m - s.length());
        for (int i=0;i < l;i++)
        {
            s = "0" + s;
        }
        return s;
    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder();
        for (int i=0;i < f.length;i++)
        {
            s.append("F" + (i + 1) + "=" + f[i].toString() + "\n");
        }
        s.append(arr(vars) + " | ");
        for (int i=0;i < f.length;i++)
        {
            s.append("F" + (i + 1));
            if (i < f.length - 1)
            {
                s.append(" ");
            }
        }
        s.append("\n");
        for (int i=0;i < 2 * (vars.size() + f.length) + 2;i++)
        {
            s.append("-");
        }
        s.append("\n");
        for (int i=0;i < in.size();i++)
        {
            s.append(arr(in.get(i)));

            s.append(" | " + arr(out.get(i)));
            if (i < in.size() - 1)
            {
                s.append("\n");
            }
        }
        return s.toString();
    }

    String arr(List<?> l)
    {
        StringBuilder s=new StringBuilder();
        for (int i=0;i < l.size();i++)
        {
            s.append(l.get(i));
            if (i < l.size() - 1)
            {
                s.append(" ");
            }
        }
        return s.toString();
    }
}
