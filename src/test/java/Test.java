public class Test {


    public void inner() {
        final int x = 5;
        String str = "str";
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(x);
            }
        };
        runnable.run();
    }
}
