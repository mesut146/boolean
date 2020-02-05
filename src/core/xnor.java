package core;
import java.util.*;

public class xnor extends func
{

    public xnor(func...arg){
        f=asList(arg);
    }
    
    public xnor(List<func> list){
        f=new ArrayList<>(list);
    }
    
    @Override
    String toString2()
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<f.size();i++){
            func p=f.get(i);
            if(!p.isCons()&&!p.isXnor()&&!p.isVar()){
                s.append(p.top());
            }else{
                s.append(p);
            }
            if(i<f.size()-1){
                s.append(xnorDel);
            }
        }
        return s.toString();
    }

    @Override
    public func simplify()
    {
        // TODO: Implement this method
        return this;
    }

    @Override
    public func not()
    {
        // TODO: Implement this method
        return new xor(f);
    }

    @Override
    public cons get(var[] v, cons[] c)
    {
        // TODO: Implement this method
        return null;
    }

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

    @Override
    public List<var> list()
    {
        // TODO: Implement this method
        return null;
    }
    
    public func alternate()
    {
        List<func> list=new ArrayList<>();
        for(func p:f){
            list.add(p.alternate());
        }
        return new xor(list).alternate().not();
    }
}
