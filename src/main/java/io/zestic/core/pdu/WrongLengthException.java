package io.zestic.core.pdu;

public class WrongLengthException extends TlvException {

    public WrongLengthException() {
        super("The TLV is shorter or longer than allowed.");
    }

    public WrongLengthException(int min, int max, int actual) {
        super("The TLV is shorter or longer than allowed: " + " min=" + min
                + " max=" + max + " actual=" + actual + ".");
    }
}
