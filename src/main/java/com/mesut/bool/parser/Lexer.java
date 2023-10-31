package com.mesut.bool.parser;

public class Lexer {
    String input;
    int pos;

    public Lexer(String input) {
        this.input = input;
    }


    public Token nextToken() {
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
                pos++;
            } else {
                break;
            }
        }
        if (pos >= input.length()) return new Token(TokenKind.EOF);
        char c = input.charAt(pos);
        int start = pos++;
        if (Character.isLetter(c)) {
            while (pos < input.length() && Character.isLetter(c = input.charAt(pos))) {
                pos++;
            }
            String val = input.substring(start, pos);
            switch (val) {
                case "and":
                    return new Token(TokenKind.AND);
                case "or":
                    return new Token(TokenKind.OR);
                case "xor":
                    return new Token(TokenKind.XOR);
                case "nor":
                    return new Token(TokenKind.NOR);
                case "xnor":
                    return new Token(TokenKind.XNOR);
                case "nand":
                    return new Token(TokenKind.NAND);
                case "not":
                    return new Token(TokenKind.NOT);
            }
            return new Token(val);
        }
        switch (c) {
            case '0':
                return new Token(TokenKind.ZERO);
            case '1':
                return new Token(TokenKind.ONE);
            case '(':
                return new Token(TokenKind.LPAREN);
            case ')':
                return new Token(TokenKind.RPAREN);
            case '+':
            case '|':
                return new Token(TokenKind.OR);
            case '.':
            case '&':
            case '*':
                return new Token(TokenKind.AND);
            case '^':
                return new Token(TokenKind.XOR);
            case '\'':
                return new Token(TokenKind.QUOTE);
            case '~':
                return new Token(TokenKind.TILDE);
            default:
                throw new RuntimeException("invalid char '" + c + "' at pos " + start);
        }
    }
}
