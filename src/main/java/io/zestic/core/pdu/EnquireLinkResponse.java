package io.zestic.core.pdu;

public class EnquireLinkResponse extends PduResponse {

    public EnquireLinkResponse() {
        super(Constants.ENQUIRE_LINK_RESP);
    }

    public void setBody(ByteBuffer buffer)
            throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException, PduException {
    }

    public ByteBuffer getBody() {
        return null;
    }

    public String toHex() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("%-50s", "enquire-link-response"));
        buffer.append(String.format("%-50s", super.debugString()));
        return buffer.toString();
    }
}
