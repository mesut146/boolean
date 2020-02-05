package core;

import java.util.ArrayList;
import java.util.List;

public class cons extends func
{
    public static final cons LOW=new cons(false);
    public static final cons HIGH=new cons(true);
    public static final cons ZERO=LOW,ONE=HIGH;
    
    boolean b;
    public cons(boolean b){
        this.b=b;
    }
    public cons(int i){
        this.b=(i==1);
    }
    public cons(char c){ this.b=c=='1'; }
    @Override
    String toString2()
    {
        return b?"1":"0";
    }

    @Override
    public cons simplify()
    {
        return this;
    }

    @Override
    public func not() {
        return new cons(!b);
    }

    @Override
    protected boolean eq2(func v) {
        return b==v.asCons().b;
    }

    @Override
    public int total()
    {
        return 1;
    }

    @Override
    public cons get(var[] v, cons[] c)
    {
        return this;
    }

    @Override
    public List<var> list() {
        return new ArrayList<var>();
    }
}
