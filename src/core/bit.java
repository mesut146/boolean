package core;

public class bit
{
    boolean b;

    public bit(boolean b)
    {
        this.b = b;
    }
    public bit(int i)
    {
        b = (i == 1);
    }

    public boolean isTrue()
    {
        return b;
    }
    public boolean isFalse()
    { return !b; }

    @Override
    public String toString()
    {
        return b ?"1": "0";
    }
}
