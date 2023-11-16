package com.mesut.bool.parser;

import com.mesut.bool.*;

public class Parser {
    Lexer lexer;
    Token peeked;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public boolean is(TokenKind kind) {
        if (peeked == null) {
            peeked = lexer.nextToken();
        }
        if (peeked != null) {
            return peeked.kind == kind;
        }
        return false;
    }

    Token consume(TokenKind kind) {
        Token tok;
        if (peeked != null) {
            tok = peeked;
            peeked = null;
        } else {
            tok = lexer.nextToken();
            if (tok.kind==TokenKind.EOF) {
                throw new RuntimeException("eof");
            }
        }
        if (tok.kind != kind) {
            throw new RuntimeException("expecting " + kind + " got:" + tok);
        }
        return tok;
    }

    public func expr() {
        func a = term();
        while (true) {
            if (is(TokenKind.OR)) {
                consume(TokenKind.OR);
                func b = expr();
                a = new Or(a, b);
            } else if (is(TokenKind.XOR)) {
                consume(TokenKind.XOR);
                func b = expr();
                a = new Xor(a, b);
            }else if (is(TokenKind.NOR)) {
                consume(TokenKind.NOR);
                func b = expr();
                a = new Nor(a, b);
            }
            else if (is(TokenKind.XNOR)) {
                consume(TokenKind.XNOR);
                func b = expr();
                a = new Xnor(a, b);
            } else {
                break;
            }
        }
        return a;
    }

    func term() {
        func a = unary();
        while (true) {
            if (is(TokenKind.AND)) {
                consume(TokenKind.AND);
                func b = term();
                a = new And(a, b);
            } else if (is(TokenKind.NAND)) {
                consume(TokenKind.NAND);
                func b = term();
                a = new Nand(a, b);
            } else {
                break;
            }
        }
        return a;
    }

    func unary() {
        if (is(TokenKind.TILDE)){
            consume(TokenKind.TILDE);
            func a = element();
            return new Not(a);
        }
        func a = element();
        if (is(TokenKind.QUOTE)) {
            consume(TokenKind.QUOTE);
            return new Not(a);
        }
        return a;
    }

    func element() {
        if (is(TokenKind.LPAREN)) {
            consume(TokenKind.LPAREN);
            func a = expr();
            consume(TokenKind.RPAREN);
            return a;
        } else if (is(TokenKind.IDENT)) {
            Token token = consume(TokenKind.IDENT);
            return new Variable(token.value);
        } else {
            return cons();
        }
    }

    func cons() {
        if (is(TokenKind.ZERO)) {
            consume(TokenKind.ZERO);
            return Cons.LOW;
        } else {
            consume(TokenKind.OR);
            return Cons.HIGH;
        }
    }
}
