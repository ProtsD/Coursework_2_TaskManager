package enums;

public enum Periodicity {
    SINGLE(0),
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3),
    ANNUAL(4);
    public final int value;

    Periodicity(int value) {
        this.value = value;
    }
}
