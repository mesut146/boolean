package core;
import java.util.*;

public class xor extends func
{

    @Override
    protected boolean eq2(func v)
    {
        // TODO: Implement this method
        return false;
    }

    @Override
    public int total()
    {
        // TODO: Implement this method
        return 0;
    }
    
    public xor(func... l){
        f=asList(l);
    }
    public xor(List<func> l){
        //a'b+ab'
        //
        f.addAll(l);
    }
    
    @Override
    public func simplify()
    {
        return this;
    }

    @Override
    String toString2()
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<f.size();i++){
            func p=f.get(i);
            if(!p.isCons()&&!p.isXor()&&!p.isVar()){
                s.append(p.top());
            }else{
                s.append(p);
            }
            if(i<f.size()-1){
                s.append(xorDel);
            }
        }
        return s.toString();
    }

    @Override
    public func alternate()
    {
        func a=f.get(0);
        func b=f.size()==2?f.get(1):new xor(wout(0));
        
        a=a.alternate();
        b=b.alternate();
        func l=a.and(b.not());
        func r=a.not().and(b);
        return l.or(r);
    }

    @Override
    public func not()
    {
        return new xnor(f);
        //return alternate().not();
    }

    @Override
    public cons get(var[] v, cons[] c)
    {
        return alternate().get(v,c);
    }

    @Override
    public List<var> list() {
        return alternate().list();
    }
}
