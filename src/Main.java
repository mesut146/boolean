import core.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        var a = new var("a");
        // var b = new var("b");
        // var c = new var("c");
        // var d = new var("d");
        // var e = new var("e");
        func f = null, g = null;
        // f = func.parse2("a xor b");
        f = func.parse2("a and (b xor c)");
        System.out.println("f= " + f);
        System.out.println("f.not= " + f.not());
        // System.out.println("f.alter= " + f.alternate());
        // System.out.println("f.not.alter= " + f.not().alternate());

        System.out.println(f.truthTable());
        // Karnaugh k=new Karnaugh(f);
        // k.simplify();

    }

    static void test() {

    }
}
