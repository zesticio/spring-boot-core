package io.zestic.core.pdu;

public class WrongDateFormatException extends PduException {
    public WrongDateFormatException() {
        super("Date must be either null or of format YYMMDDhhmmsstnnp");
    }

    public WrongDateFormatException(String dateStr) {
        super("Date must be either null or of format YYMMDDhhmmsstnnp and not "
                + dateStr + ".");
    }

    public WrongDateFormatException(String dateStr, String msg) {
        super("Invalid date " + dateStr + ": " + msg);
    }
}
