package core;
import java.util.*;

public class var extends func
{
    char c;
    boolean not=false;

    public var(char c){
        this.c=c;
    }
    public var(String s){
        c=s.charAt(0);
    }

    @Override
    String toString2()
    {
        return c+(not?"'":"");
    }

    @Override
    public func simplify()
    {
        return this;
    }

    @Override
    public var not() {
        var v=new var(c);
        v.not=!not;
        return v;
    }

    @Override
    protected boolean eq2(func v) {
        var a=(var)v;
        if (c==a.c&&not==a.not){
            return true;
        }
        return false;
    }

    @Override
    public int total()
    {
        return 1;
    }

    @Override
    public cons get(var[] v, cons[] c)
    {
        for(int i=0;i<v.length;i++){
            if(v[i].c==this.c){
                cons t=c[i];
                return not?(cons)t.not():t;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return super.eq((func)obj);
    }
    @Override
    public int hashCode() {
        return c;
    }

    @Override
    public List<var> list() {
        return new ArrayList<var>(){{
            add(new var(c));
        }};
    }
}
