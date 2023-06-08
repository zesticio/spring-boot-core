package io.zestic.core.exception;

import io.zestic.core.pdu.Pdu;
import io.zestic.core.pdu.PduException;

public class InvalidPDUException extends PduException {
    private Exception underlyingException = null;

    public InvalidPDUException(Pdu pdu, Exception e) {
        super(pdu);
        underlyingException = e;
    }

    public InvalidPDUException(Pdu pdu, String s) {
        super(pdu, s);
    }

    public String toString() {
        String s = super.toString();
        if (underlyingException != null) {
            s += "\nUnderlying exception: " + underlyingException.toString();
        }
        return s;
    }

    public Exception getException() {
        return underlyingException;
    }
}