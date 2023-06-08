package io.zestic.core.pdu;

public class NotEnoughDataInByteBufferException extends PduException {
    private static final long serialVersionUID = -3167903689273009558L;

    private int available;
    private int expected;

    public NotEnoughDataInByteBufferException(int p_available, int p_expected) {
        super("Not enough data in byte buffer. " + "Expected " + p_expected
                + ", available: " + p_available + ".");
        available = p_available;
        expected = p_expected;
    }

    public NotEnoughDataInByteBufferException(String s) {
        super(s);
        available = 0;
        expected = 0;
    }

    public int getAvailable() {
        return available;
    }

    public int getExpected() {
        return expected;
    }
}
