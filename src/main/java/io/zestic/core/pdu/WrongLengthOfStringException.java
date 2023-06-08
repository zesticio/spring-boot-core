package io.zestic.core.pdu;

public class WrongLengthOfStringException extends PduException {
    public WrongLengthOfStringException() {
        super("The string is shorter or longer than required.");
    }

    public WrongLengthOfStringException(int min, int max, int actual) {
        super("The string is shorter or longer than required: " + " min=" + min
                + " max=" + max + " actual=" + actual + ".");
    }
}