package parser;
import core.func;
import java.util.*;
import java.nio.*;

public class Parser
{
    String s;
    int i,len;

    private Parser(String s)
    {
        s = s.replace("||", "+");
        s = s.replace("&&", ".");
        s = s.replaceAll("[^n]and", ".");
        s = s.replaceAll("[^xn]or", "+");
        s = s.replace("xor", "^");
        s = s.replace("xnor", "%");
        s = s.replace("nand", "/");
        s = s.replace("nor", "-");
        s = s.replace(" ", "");
        this.s = s;
        i = 0;
        len = s.length();
    }
    public static func parse(String s)
    {
        return new Parser(s).parse();
    }

    public func parse()
    {
        Token a;
        List<Token> l=new ArrayList<>();
        Token b=null;
        //x(a+b)c

        while ((a = next()) != null)
        {
            if (b != null)
            {
                if (t1(b.type) && t2(a.type))
                {
                    l.add(new Token("and", TokenType.Operator));
                }
            }
            l.add(a);          
            b = a;
        }
        //System.out.println(l);
        List<Token> rpn=ShuntingYard.infixToRPN(l);
        //System.out.println("rpn="+rpn);

        Stack<Token> stack = new Stack<>();

        for (Token t:rpn)
        {
            if (t.type == TokenType.Operator)
            {
                if (t.val.startsWith("not"))
                {
                    stack.push(new Token(stack.pop().f.not(), TokenType.Function));
                }
                else
                {
                    func v=OPS.get(t.val).eval(stack.pop().f, stack.pop().f);
                    stack.push(new Token(v, TokenType.Function));
                }
            }
            else
            {
                stack.push(t);
            }
        }
        return stack.pop().f;
    }

    boolean t1(TokenType t)
    {
        return t == TokenType.Variable || t == TokenType.Close;
    }
    boolean t2(TokenType t)
    {
        return t == TokenType.Variable || t == TokenType.Open;
    }

    Token next()
    {
        char c;
        while (true)
        {
            c = get();
            if (c == '\0')
            {
                break;
            }
            if (c == ' ')
            {
                continue;
            }
            else if (isOperator(c))
            {
                return new Token(c, TokenType.Operator);
            }
            else if (c == '(')
            {
                return new Token(TokenType.Open);
            }
            else if (c == ')')
            {
                return new Token(TokenType.Close);
            }
            else if (isWord(c))
            {
                return new Token(c, TokenType.Variable);
            }
        }
        return null;
    }

    char get()
    {
        if (i < s.length())
        {
            return s.charAt(i++);
        }
        return '\0';
    }
    boolean isWord(char c)
    {
        return (c >= 'a' && c <= 'z') || ((c >= 'A' && c <= 'Z'));
    }
    boolean isOperator(char c)
    {
        return "+|.*&@^-/%!'".indexOf(c) != -1;
    }

    public interface Operation
    {
        func eval(func e1, func e2);
    }
    public static Map<String, Operation> OPS = new HashMap<String, Operation>(){{
            put("and", new Operation(){ public func eval(func e1, func e2)
                    { return e2.and(e1); }});
            put("or", new Operation(){ public func eval(func e1, func e2)
                    { return e2.or(e1); }});
            put("xor", new Operation(){ public func eval(func e1, func e2)
                    { return e2.xor(e1); }});
            put("xnor", new Operation(){ public func eval(func e1, func e2)
                    { return e2.xor(e1).not(); }});
            put("nor", new Operation(){ public func eval(func e1, func e2)
                    { return e2.or(e1).not(); }});
            put("nand", new Operation(){ public func eval(func e1, func e2)
                    { return e2.and(e1).not(); }});
        }};
}
