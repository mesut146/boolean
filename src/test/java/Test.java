import com.mesut.bool.core.Karnaugh;
import com.mesut.bool.core.TruthTable;
import com.mesut.bool.core.func;
import org.junit.Assert;
import org.junit.Ignore;

public class Test {

    @org.junit.Test
    public void simplify() {
        //func f = func.parse("a+b.c'");
        func f = func.parse("a.b+a.b'");
        System.out.println(f);
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
    public void half_adder() {
        func sum = func.parse("a xor b");
        func carry = func.parse("a or b");
        System.out.printf("sum=%s carry=%s\n", sum, carry);
        System.out.println(new TruthTable(sum, carry));
    }

    @org.junit.Test
    public void full_adder() {
        func sum = func.parse("(a xor b) xor carry_in");
        func carry_out = func.parse("(carry_in and (a xor b)) or (b and a)");
        System.out.printf("sum=%s carry=%s\n", sum, carry_out);
        System.out.println("tt=" + new TruthTable(sum, carry_out));
        //System.out.println("tt.sum=" + sum.truthTable());
    }

    @org.junit.Test
    @Ignore
    public void karnaugh() {
        func f = func.parse("a and (a or b)");
        Karnaugh k = new Karnaugh(f);
        k.simplify();
    }
}
