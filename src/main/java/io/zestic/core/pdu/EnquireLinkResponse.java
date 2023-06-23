package io.zestic.core.pdu;

public class EnquireLinkResponse extends PduResponse {

    public EnquireLinkResponse() {
        super(Constants.ENQUIRE_LINK_RESPONSE);
    }

    @Override
    protected void setBody(ByteBuffer buffer)
            throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException, PduException {
    }

    @Override
    protected ByteBuffer getBody() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append(System.getProperty("line.separator"));
        return buffer.toString();
    }
}
