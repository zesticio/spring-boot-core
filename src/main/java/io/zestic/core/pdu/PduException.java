package io.zestic.core.pdu;


/**
 * Incorrect format of PDU passed as a parameter or received from SMSC.
 */

public class PduException extends Exception {

    private transient Pdu pdu = null;

    public PduException() {
    }

    public PduException(Pdu pdu) {
        setPDU(pdu);
    }

    public PduException(String s) {
        super(s);
    }

    public PduException(Pdu pdu, String s) {
        super(s);
        setPDU(pdu);
    }

    public String toString() {
        String s = super.toString();
        if (pdu != null) {
            s += "\nPDU debug string: " + pdu.toString();
        }
        return s;
    }

    public void setPDU(Pdu pdu) {
        this.pdu = pdu;
    }

    public Pdu getPDU() {
        return pdu;
    }

    public boolean hasPDU() {
        return pdu != null;
    }
}
