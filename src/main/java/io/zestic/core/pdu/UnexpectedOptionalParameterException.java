package io.zestic.core.pdu;

public class UnexpectedOptionalParameterException extends PduException {
    private int tag = 0;

    public UnexpectedOptionalParameterException() {
        super("The optional parameter wasn't expected for the PDU.");
    }

    public UnexpectedOptionalParameterException(short tag) {
        super("The optional parameter wasn't expected for the PDU:" + " tag=" + tag + ".");
    }
}