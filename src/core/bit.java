package core;

// represents single bit
public class bit {
    boolean value;

    public bit(boolean value) {
        this.value = value;
    }

    public bit(int value) {
        this.value = (value == 1);
    }

    public boolean isTrue() {
        return value;
    }

    public boolean isFalse() {
        return !value;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }
}
