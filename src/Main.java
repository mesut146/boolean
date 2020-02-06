import com.mesut.bool.core.Karnaugh;
import com.mesut.bool.core.TruthTable;
import com.mesut.bool.core.func;
import com.mesut.bool.core.var;


public class Main {
    public static void main(String[] args) {
        var a = new var("a");
        // var b = new var("b");
        // var c = new var("c");
        // var d = new var("d");
        // var e = new var("e");
        test();
        //half_adder();
        //full_adder();
    }

    static void test() {
        func f = func.parse("a and (b xor c)");
        System.out.println("f = " + f);
        System.out.println("f.not = " + f.not());
        System.out.println("f.alter = " + f.alternate());
        System.out.println("f.not.alter = " + f.not().alternate());// todo correct alter

        System.out.println(f.truthTable());
    }

    static void half_adder() {
        func sum = func.parse("a xor b");
        func carry = func.parse("a or b");
        System.out.printf("sum=%s carry=%s\n", sum, carry);
        System.out.println("tt=" + new TruthTable(sum, carry));
    }

    static void full_adder() {
        func sum = func.parse("(a xor b) xor carry_in");
        func carry_out = func.parse("(carry_in and (a xor b)) or (b and a)");
        System.out.printf("sum=%s carry=%s\n", sum, carry_out);
        System.out.println("tt=" + new TruthTable(sum, carry_out));
        //System.out.println("tt.sum=" + sum.truthTable());
    }

    static void karnaugh() {
        func f = func.parse("a and (a or b)");
        Karnaugh k = new Karnaugh(f);
        k.simplify();
    }

}
