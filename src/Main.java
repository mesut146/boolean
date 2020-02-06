import core.*;
import java.nio.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        var a = new var("a");
        var b = new var("b");
        var c = new var("c");
        var d = new var("d");
        var e = new var("e");
        func f = null, g = null;
        // f=func.parse("a*b+c");
        f = func.parse2("a and (b xor c)");
        System.out.println(f);
        System.out.println(f.not());
        System.out.println(f.alternate());
        // System.out.println(f.truthTable());
        // Karnaugh k=new Karnaugh(f);
        // k.simplify();

    }

    static void test() {
        List<func> l = Arrays.asList(new var("a"));
        l.remove(0);
    }
}
