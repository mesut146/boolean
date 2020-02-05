package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.*;

class ShuntingYard{
    private static final int left = 0;
    private static final int right = 1;
    private static final Map<String, int[]> OPERATORS = new HashMap<String, int[]>(){{
        put("or", new int[]{0, left});
        put("nor", new int[]{5, left});
        put("and", new int[]{5, left});
        put("nand", new int[]{5, left});
        put("xor", new int[]{5, left});
        put("xnor", new int[]{5, left});
        put("not1", new int[]{6, right});
        put("not2", new int[]{6, left});
    }};

    private static boolean isOperator(Token token) {
        return OPERATORS.containsKey(token.val);
    }

    private static boolean isAssociative(Token token, int type) {
        return OPERATORS.get(token.val)[1] == type;
    }

    private static final int cmpPrecedence(Token token1, Token token2) {
        return OPERATORS.get(token1.val)[0] - OPERATORS.get(token2.val)[0];
    }

    public static List<Token> infixToRPN(List<Token> inputTokens) {
        List<Token> out = new ArrayList<>();
        Stack<Token> stack = new Stack<>();
        for (Token token : inputTokens) {
            if (token.type==TokenType.Operator) {
                while (!stack.empty() && isOperator(stack.peek())) {
                    if ((isAssociative(token, left) && cmpPrecedence(
                            token, stack.peek()) <= 0)
                            || (isAssociative(token, right) && cmpPrecedence(
                            token, stack.peek()) < 0)) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if (token.type==TokenType.Open) {
                stack.push(token);
            } else if (token.type==TokenType.Close) {
                while (!stack.empty() && stack.peek().type!=TokenType.Open) {
                    out.add(stack.pop());
                }
                stack.pop();
            } else {
                out.add(token);
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        return out;
    }
}
