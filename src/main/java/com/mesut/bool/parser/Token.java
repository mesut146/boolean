package com.mesut.bool.parser;

public class Token {
    String value;
    TokenKind kind;

    public Token(String value) {
        this.value = value;
        this.kind = TokenKind.IDENT;
    }

    public Token(TokenKind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        if (value != null) return value;
        return kind.toString();
    }
}
