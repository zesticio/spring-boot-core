package io.zestic.core.pdu;

public class TerminatingZeroNotFoundException extends PduException {

    private static final long serialVersionUID = 1L;

    public TerminatingZeroNotFoundException() {
        super("Terminating zero not found in buffer.");
    }

    public TerminatingZeroNotFoundException(String s) {
        super(s);
    }
}
