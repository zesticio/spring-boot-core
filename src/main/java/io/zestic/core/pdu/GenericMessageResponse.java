package io.zestic.core.pdu;

public class GenericMessageResponse extends PduResponse {

    private String messageId = "";

    public GenericMessageResponse() {
        super(Constants.GENERIC_RESPONSE);
    }

    public void setMessageId(String value) throws WrongLengthOfStringException {
        checkString(value, Constants.MAX_MESSAGE_LENGTH);
        messageId = value;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException,
            PduException {
        setMessageId(buffer.removeCString());
    }

    @Override
    public ByteBuffer getBody() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.appendCString(messageId);
        return buffer;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("%-50s", "submit-multi-resp"));
        buffer.append(String.format("%-50s", super.toString()));
        buffer.append(String.format("%-20s%-30s", "message-id", getMessageId()));
        return buffer.toString();
    }
}
