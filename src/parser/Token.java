package parser;
import java.lang.reflect.*;
import core.func;
import core.cons;
import core.*;

public class Token{
    String val;
    TokenType type;
    public func f;
    String p;
    
    public Token(String s,TokenType t){
        val=s;
        type=t;
        if(t==TokenType.Operator){
            if(".*&".indexOf(val)!=-1){
                val="and";
            }else if("+|".indexOf(val)!=-1){
                val="or";
            }else if("@^".indexOf(val)!=-1){
                val="xor";
            }else if(val.equals("%")){
                val="xnor";
            }else if(val.equals("-")){
                val="nor";
            }else if(val.equals("/")){
                val="nand";
            }else if(val.equals("!")){
                val="not1";
            }else if(val.equals("'")){
                val="not2";
            }
        }else if(t==TokenType.Variable){
            f=new var(val);
        }
    }
    public Token(char c,TokenType t){
        this(""+c,t);
    }
    public Token(TokenType t){
        type=t;
    }

    public Token(func p,TokenType t){
        f=p;
        type=t;
    }

    @Override
    public String toString() {
        return val+":"+type;
    }
}
