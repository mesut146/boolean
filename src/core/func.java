package core;

import java.util.*;
import com.mesut.bool.parser2.*;
import java.io.*;

public abstract class func
{
    public List<func> f=new ArrayList<>();
    tt tt=null;
    static String andDel=" and ";
    static String orDel=" or ";
    static String xorDel=" xor ";
    static String nandDel=" nand ";
    static String norDel=" nor ";
    static String xnorDel=" xnor ";

    @Override
    public final String toString()
    {
        return toString2();
    }
    
    abstract String toString2();
    public abstract func simplify();
    
    public func and(func f){
        return new and(this,f).simplify();
    }
    public func or(func f){
        return new or(this,f).simplify();
    }
    public func xor(func f){
        return new xor(this,f).simplify();
    }
    public abstract func not();
    public boolean isAnd(){
        return this instanceof and;
    }
    public boolean isOr(){
        return this instanceof or;
    }
    public boolean isXor(){
        return this instanceof xor;
    }
    public boolean isNand(){
        return this instanceof nand;
    }
    public boolean isNor(){
        return this instanceof nor;
    }
    public boolean isXnor(){
        return this instanceof xnor;
    }
    public boolean isCons(){
        return this instanceof cons;
    }
    public boolean isVar(){
        return this instanceof var;
    }
    
    public cons asCons(){
        return (cons)this;
    }
    public boolean isLow(){
        return isCons()&&!asCons().b;
    }
    
    public boolean isHigh(){
        return isCons()&&asCons().b;
    }

    public abstract cons get(var[] v,cons[] c);
    public cons get(List<var> v,List<cons> c){
        return get(v.toArray(new var[0]),c.toArray(new cons[0]));
    }
    public cons get(var v,cons c){
        return get(new var[]{v},new cons[]{c});
    }
    public func get(String s){
        String p[]=s.split(",");
        var v[]=new var[p.length];
        cons c[]=new cons[p.length];
        String t[];
        for(int i=0;i<p.length;i++){
            t=p[i].split("=");
            v[i]=new var(t[0]);
            c[i]=new cons(Integer.parseInt(t[1]));
        }
        return get(v,c);
    }
    
    public var asVar(){
        return (var)this;
    }
    
    public tt truthTable(){
        if(tt==null){
            tt=new tt(this);
            tt.calc();
        }
        return tt;
    }
    
    public final boolean eq(func v){
        return super.getClass()==v.getClass()?eq2(v):false;
    }
    protected abstract boolean eq2(func v);

    public static boolean isEq(List<func> l1,List<func> l2){
        int l;
        if ((l=l1.size())!=l2.size()){
            return false;
        }
        boolean b[]=new boolean[l];
        boolean u;
        for(int i=0;i<l;i++){
            func p=l1.get(i);
            u=false;
            for(int j=0;j<l;j++){
                if(!b[j]&&p.eq(l2.get(j))){
                    b[j]=true;
                    u=true;
                    break;
                }
            }
            if(!u){
                return false;
            }
        }
        return true;
    }
    
    static List<func> free(){
        return new ArrayList<func>();
    }
    
    public static func parse(String s){
        return parser.Parser.parse(s);
    }
    
    public static func parse2(String s){
        BooleanParser parser=new BooleanParser(new StringReader(s));
        try
        {
            return parser.expr();
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public abstract int total();
    
   // public abstract func cnf();
    
    static List<func> asList(func[] p){
        List<func> l=new ArrayList<>();
        for(func v:p){
            l.add(v);
        }
        return l;
    }
    static int find(List<?> l,Class c){
        for(int i=0;i<l.size();i++){
            if(l.get(i).getClass()==c){
                return i;
            }
        }
        return -1;
    }
    
    public String top(){
        return "("+toString()+")";
    }
    
    public List<func> wout(int i){
        List<func> copy=new ArrayList<>(f);
        copy.remove(i);
        return copy;
    }
    
    //reconstruct with only and/or
    public func alternate(){
        return this;
    }

    public abstract List<var> list();

    public static void sort2(List<var> l){
        Collections.sort(l,new Comparator<var>(){
                @Override
                public int compare(var o1,var o2){
                    return o1.c<o2.c?-1:1;
                }
            });
    }
    public static void sort(List<func> l){
        Collections.sort(l,new Comparator<func>(){
                @Override
                public int compare(func o1,func o2){
                    if(o1.isVar()&&o2.isVar()){
                        return o1.asVar().c<o2.asVar().c?-1:1;
                    }
                    return 0;
                }
        });
    }
    

    <T,E> List<E> cst(List<T> l,Class<E> c){
        List<E> l2=new ArrayList<>();
        for (T t:l){
            l2.add((E)t);
        }
        return l2;
    }

}
