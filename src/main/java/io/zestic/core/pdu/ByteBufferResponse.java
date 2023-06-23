package io.zestic.core.pdu;

public class ByteBufferResponse extends PduResponse {

    private String messageId = "";

    public ByteBufferResponse() {
        super(Constants.BYTE_BUFFER_RESPONSE);
    }

    public void setMessageId(String value) throws WrongLengthOfStringException {
        checkString(value, Constants.MAX_MESSAGE_LENGTH);
        messageId = value;
    }

    public String getMessageId() {
        return messageId;
    }

    @Override
    protected void setBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException,
            PduException {
        setMessageId(buffer.removeCString());
    }

    @Override
    protected ByteBuffer getBody() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.appendCString(messageId);
        return buffer;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        return builder.toString();
    }
}
