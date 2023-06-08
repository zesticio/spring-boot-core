package io.zestic.core.pdu;

public class IntegerOutOfRangeException extends PduException {
    public IntegerOutOfRangeException() {
        super("The integer is lower or greater than required.");
    }

    public IntegerOutOfRangeException(int min, int max, int val) {
        super("The integer is lower or greater than required: " + " min=" + min
                + " max=" + max + " actual=" + val + ".");
    }
}