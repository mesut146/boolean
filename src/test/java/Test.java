import com.mesut.bool.core.Karnaugh;
import com.mesut.bool.core.TruthTable;
import com.mesut.bool.core.func;

public class Test {

    @org.junit.Test
    public void simplify() {
        func f = func.parse("(a && b) || (a && c) || (b && !c)");
        System.out.println(f);
    }

    @org.junit.Test
    public void alter() {
        func f = func.parse("a and (b xor c)");
        System.out.println("f = " + f);
        System.out.println("f.not = " + f.not());
        System.out.println("f.alter = " + f.alternate());
        System.out.println("f.not.alter = " + f.not().alternate());// todo correct alter

        System.out.println(f.truthTable());
    }

    @org.junit.Test
    public void half_adder() {
        func sum = func.parse("a xor b");
        func carry = func.parse("a or b");
        System.out.printf("sum=%s carry=%s\n", sum, carry);
        System.out.println("tt=" + new TruthTable(sum, carry));
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
    public void karnaugh() {
        func f = func.parse("a and (a or b)");
        Karnaugh k = new Karnaugh(f);
        k.simplify();
    }
}
