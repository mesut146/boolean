/* BooleanParser.java */
/* Generated By:JavaCC: Do not edit this line. BooleanParser.java */
package com.mesut.bool.parser2;

import core.*;
import java.util.*;

public class BooleanParser implements BooleanParserConstants {

  final public func line() throws ParseException {func f;
    f = expr();
    jj_consume_token(0);
{if ("" != null) return f;}
    throw new Error("Missing return statement in function");
}

  final public func expr() throws ParseException {func a,b;
    a = term();
    label_1:
    while (true) {
      if (jj_2_1(3)) {
        ;
      } else {
        break label_1;
      }
      if (jj_2_2(3)) {
        jj_consume_token(OR);
        b = expr();
a=new or(a,b);
      } else if (jj_2_3(3)) {
        jj_consume_token(XOR);
        b = expr();
a=new xor(a,b);
      } else if (jj_2_4(3)) {
        jj_consume_token(NOR);
        b = expr();
a=new nor(a,b);
      } else if (jj_2_5(3)) {
        jj_consume_token(XNOR);
        b = expr();
a=new xnor(a,b);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return a;}
    throw new Error("Missing return statement in function");
}

  final public func term() throws ParseException {func a,b;
    a = unary();
    label_2:
    while (true) {
      if (jj_2_6(3)) {
        ;
      } else {
        break label_2;
      }
      if (jj_2_7(3)) {
        jj_consume_token(AND);
        b = term();
a=new and(a,b);
      } else if (jj_2_8(3)) {
        jj_consume_token(NAND);
        b = term();
a=new nand(a,b);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return a;}
    throw new Error("Missing return statement in function");
}

  final public func unary() throws ParseException {func a;
  boolean not=false;
    if (jj_2_9(3)) {
      jj_consume_token(30);
not=true;
    } else {
      ;
    }
    a = element();
    if (jj_2_12(3)) {
      if (jj_2_10(3)) {
        jj_consume_token(31);
      } else if (jj_2_11(3)) {
        jj_consume_token(QUOTE);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
not=true;
    } else {
      ;
    }
if(not){
      {if ("" != null) return a.not();}
    }
    {if ("" != null) return a;}
    throw new Error("Missing return statement in function");
}

  final public func element() throws ParseException {func a;
    if (jj_2_13(3)) {
      a = cons();
{if ("" != null) return a;}
    } else if (jj_2_14(3)) {
      a = var();
{if ("" != null) return a;}
    } else if (jj_2_15(3)) {
      jj_consume_token(LPAREN);
      a = expr();
      jj_consume_token(RPAREN);
{if ("" != null) return a;}
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public cons cons() throws ParseException {cons c;
    if (jj_2_16(3)) {
      jj_consume_token(ZERO);
c=cons.LOW;
    } else if (jj_2_17(3)) {
      jj_consume_token(ONE);
c=cons.HIGH;
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return c;}
    throw new Error("Missing return statement in function");
}

  final public var var() throws ParseException {var v;
  Token token;
    token = jj_consume_token(IDENT);
{if ("" != null) return new var(token.toString());}
    throw new Error("Missing return statement in function");
}

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_3()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_4()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_5()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  private boolean jj_2_6(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_6()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  private boolean jj_2_7(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_7()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(6, xla); }
  }

  private boolean jj_2_8(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_8()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(7, xla); }
  }

  private boolean jj_2_9(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_9()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(8, xla); }
  }

  private boolean jj_2_10(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_10()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(9, xla); }
  }

  private boolean jj_2_11(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_11()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(10, xla); }
  }

  private boolean jj_2_12(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_12()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(11, xla); }
  }

  private boolean jj_2_13(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_13()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(12, xla); }
  }

  private boolean jj_2_14(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_14()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(13, xla); }
  }

  private boolean jj_2_15(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_15()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(14, xla); }
  }

  private boolean jj_2_16(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_16()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(15, xla); }
  }

  private boolean jj_2_17(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_17()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(16, xla); }
  }

  private boolean jj_3R_5()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_3_16()) return false;
    jj_scanpos = xsp;
    if (jj_3_17()) return true;
    return false;
  }

  private boolean jj_3R_3()
 {
    if (jj_3R_4()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_1()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3_15()
 {
    if (jj_scan_token(LPAREN)) return true;
    if (jj_3R_3()) return true;
    if (jj_scan_token(RPAREN)) return true;
    return false;
  }

  private boolean jj_3_14()
 {
    if (jj_3R_6()) return true;
    return false;
  }

  private boolean jj_3R_8()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_3_13()) return false;
    jj_scanpos = xsp;
    if (!jj_3_14()) return false;
    jj_scanpos = xsp;
    if (jj_3_15()) return true;
    return false;
  }

  private boolean jj_3_13()
 {
    if (jj_3R_5()) return true;
    return false;
  }

  private boolean jj_3_9()
 {
    if (jj_scan_token(30)) return true;
    return false;
  }

  private boolean jj_3R_7()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_9()) jj_scanpos = xsp;
    if (jj_3R_8()) return true;
    xsp = jj_scanpos;
    if (jj_3_12()) jj_scanpos = xsp;
    return false;
  }

  private boolean jj_3_17()
 {
    if (jj_scan_token(ONE)) return true;
    return false;
  }

  private boolean jj_3_11()
 {
    if (jj_scan_token(QUOTE)) return true;
    return false;
  }

  private boolean jj_3_8()
 {
    if (jj_scan_token(NAND)) return true;
    if (jj_3R_4()) return true;
    return false;
  }

  private boolean jj_3_7()
 {
    if (jj_scan_token(AND)) return true;
    if (jj_3R_4()) return true;
    return false;
  }

  private boolean jj_3_6()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_3_7()) return false;
    jj_scanpos = xsp;
    if (jj_3_8()) return true;
    return false;
  }

  private boolean jj_3R_4()
 {
    if (jj_3R_7()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_6()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3R_6()
 {
    if (jj_scan_token(IDENT)) return true;
    return false;
  }

  private boolean jj_3_10()
 {
    if (jj_scan_token(31)) return true;
    return false;
  }

  private boolean jj_3_12()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_3_10()) return false;
    jj_scanpos = xsp;
    if (jj_3_11()) return true;
    return false;
  }

  private boolean jj_3_5()
 {
    if (jj_scan_token(XNOR)) return true;
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3_4()
 {
    if (jj_scan_token(NOR)) return true;
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3_3()
 {
    if (jj_scan_token(XOR)) return true;
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3_16()
 {
    if (jj_scan_token(ZERO)) return true;
    return false;
  }

  private boolean jj_3_2()
 {
    if (jj_scan_token(OR)) return true;
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3_1()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_3_2()) return false;
    jj_scanpos = xsp;
    if (!jj_3_3()) return false;
    jj_scanpos = xsp;
    if (!jj_3_4()) return false;
    jj_scanpos = xsp;
    if (jj_3_5()) return true;
    return false;
  }

  /** Generated Token Manager. */
  public BooleanParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[0];
  static private int[] jj_la1_0;
  static {
	   jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {};
	}
  final private JJCalls[] jj_2_rtns = new JJCalls[17];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public BooleanParser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public BooleanParser(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new BooleanParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 0; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public BooleanParser(java.io.Reader stream) {
	 jj_input_stream = new JavaCharStream(stream, 1, 1);
	 token_source = new BooleanParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new JavaCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new BooleanParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public BooleanParser(BooleanParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(BooleanParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error {
    @Override
    public Throwable fillInStackTrace() {
      return this;
    }
  }
  static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[32];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 0; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 32; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 17; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			   case 2: jj_3_3(); break;
			   case 3: jj_3_4(); break;
			   case 4: jj_3_5(); break;
			   case 5: jj_3_6(); break;
			   case 6: jj_3_7(); break;
			   case 7: jj_3_8(); break;
			   case 8: jj_3_9(); break;
			   case 9: jj_3_10(); break;
			   case 10: jj_3_11(); break;
			   case 11: jj_3_12(); break;
			   case 12: jj_3_13(); break;
			   case 13: jj_3_14(); break;
			   case 14: jj_3_15(); break;
			   case 15: jj_3_16(); break;
			   case 16: jj_3_17(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

}
