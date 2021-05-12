package me.foolishchow.android.datepicker.validator;

public class ValidateResult {
    public final int rangeStart;
    public final int rangeEnd;
    public final int current;

    public ValidateResult(int rangeStart, int rangeEnd, int current) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.current = current;
    }
}
