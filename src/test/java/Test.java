import com.mesut.bool.Karnaugh;
import com.mesut.bool.Main;
import com.mesut.bool.TruthTable;
import com.mesut.bool.func;
import org.junit.Assert;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

    @org.junit.Test
    public void parser() {
        Assert.assertEquals("a.b", func.parseSOP("0001", "a", "b").sort().toString());
        Assert.assertEquals("(a+b).(a+b').(a'+b)", func.parsePOS("0001", "a", "b").sort().toString());

        Assert.assertEquals("a'.b+a.b'+a.b", func.parseSOP("0111", "a", "b").sort().toString());
        Assert.assertEquals("a+b", func.parsePOS("0111", "a", "b").sort().toString());

        Assert.assertEquals("(a+b).(a'+b')",func.parsePOS("0110", "a", "b").toString());
        Assert.assertEquals("a'.b+a.b'",func.parseSOP("0110", "a", "b").toString());
    }

    @org.junit.Test
    public void testPos() {
        System.out.println(func.parse("a.b").sop());
        System.out.println(func.parse("a.(b+c)").sop());
        System.out.println(func.parse("(a+b).c").sop());
        System.out.println(func.parse("(a+b).(c+d)").sop());

        System.out.println(func.parse("a.b").pos());
        System.out.println(func.parse("a+b").pos());
        System.out.println(func.parse("a.b+a.c").pos());
    }

    @org.junit.Test
    public void alter() {
        Assert.assertEquals("a.b'+a'.b", func.parse("a xor b").alternate().toString());
        Assert.assertEquals("a'+b'", func.parse("a nand b").alternate().toString());
        Assert.assertEquals("a'.b'", func.parse("a nor b ").alternate().toString());
        Assert.assertEquals("(a'+b).(a+b')", func.parse("a xnor b ").alternate().toString());

        func f = func.parse("a and (b xor c)");
        Assert.assertEquals("a.(b.c'+b'.c)", f.alternate().toString());
        Assert.assertEquals("a nand (b^c)", f.not().toString());
        Assert.assertEquals("a'+(b'+c).(b+c')", f.not().alternate().toString());
    }

    @org.junit.Test
    public void truth() {
        func f = func.parse("a.b");
        System.out.println(new TruthTable(f));
    }

    @org.junit.Test
    public void eval() {
        Assert.assertEquals("0001", func.parse("a.b").truthTable().getOutStr());
        Assert.assertEquals("0111", func.parse("a+b").truthTable().getOutStr());
        Assert.assertEquals("0110", func.parse("a^b").truthTable().getOutStr());
        Assert.assertEquals("1110", func.parse("a nand b").truthTable().getOutStr());
        Assert.assertEquals("1000", func.parse("a nor b").truthTable().getOutStr());
        Assert.assertEquals("1001", func.parse("a xnor b").truthTable().getOutStr());
    }

    @org.junit.Test
    public void half_adder() {
        func sum = func.parse("a xor b");
        func carry = func.parse("a And b");
        System.out.printf("sum=%s carry=%s\n", sum, carry);
        System.out.println(new TruthTable(sum, carry));
    }

    @org.junit.Test
    public void full_adder() {
        func sum = func.parse("(a xor b) xor c");
        func carry = func.parse("(c and (a xor b)) or (a and b)");
        System.out.printf("sum=%s carry=%s\n", sum, carry);
        System.out.println(new TruthTable(sum, carry));
        //System.out.println("tt.sum=" + sum.truthTable());
    }

    @org.junit.Test
    public void karnaugh() {
        Assert.assertEquals("a", Karnaugh.solve(func.parse("a.(a+b)")).toString());

        String s1 = Karnaugh.solve(func.parse("a+a'.b")).toString();
        Assert.assertTrue(s1.equals("a+b") || s1.equals("b+a"));

        Assert.assertEquals("B.C.D'+A.C'+A.B'", Karnaugh.solve(func.parseSOP("0000001011111110","A", "B", "C", "D")).toString());
    }

    @org.junit.Test
    @Ignore
    public void karnaugh2() {
        func f = func.parseSOP("0000001011111110", "A", "B", "C", "D");
        //func f =func.parse("A.B.C.D+E");
        System.out.println(f);
        Karnaugh k = new Karnaugh(f);
        System.out.println(k.tt);
        System.out.println(k);
        System.out.println(k.solve().truthTable());
        //System.out.println(func.parse("A.C'+A.B'+B.C.D'").truthTable());
    }

    @org.junit.Test
    public void test_main() {
        Main.main("-sop 0001".split(" "));
        Main.main("-pos 0111".split(" "));
    }
}
