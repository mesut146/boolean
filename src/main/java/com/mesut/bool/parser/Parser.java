package com.mesut.bool.parser;

import com.mesut.bool.core.cons;
import com.mesut.bool.core.func;
import com.mesut.bool.core.variable;
import com.mesut.bool.operators.*;

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
                a = new or(a, b);
            } else if (is(TokenKind.XOR)) {
                consume(TokenKind.XOR);
                func b = expr();
                a = new xor(a, b);
            }else if (is(TokenKind.NOR)) {
                consume(TokenKind.NOR);
                func b = expr();
                a = new nor(a, b);
            }
            else if (is(TokenKind.XNOR)) {
                consume(TokenKind.XNOR);
                func b = expr();
                a = new xnor(a, b);
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
                a = new and(a, b);
            } else if (is(TokenKind.NAND)) {
                consume(TokenKind.NAND);
                func b = term();
                a = new nand(a, b);
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
            return new not(a);
        }
        func a = element();
        if (is(TokenKind.QUOTE)) {
            consume(TokenKind.QUOTE);
            return new not(a);
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
            return new variable(token.value);
        } else {
            return cons();
        }
    }

    func cons() {
        if (is(TokenKind.ZERO)) {
            consume(TokenKind.ZERO);
            return cons.LOW;
        } else {
            consume(TokenKind.OR);
            return cons.HIGH;
        }
    }
}
