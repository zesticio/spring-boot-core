package io.zestic.core.pdu;

import org.apache.logging.log4j.util.Strings;

public class EnquireLinkRequest extends PduRequest<EnquireLinkResponse> {

    public EnquireLinkRequest() {
        super(Constants.ENQUIRE_LINK);
    }

    @Override
    public EnquireLinkResponse createResponse() {
        EnquireLinkResponse response = new EnquireLinkResponse();
        response.setSequenceNumber(getSequenceNumber());
        return response;
    }

    @Override
    public Class<EnquireLinkResponse> getResponseClass() {
        return null;
    }

    @Override
    public void setBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, PduException {
    }

    @Override
    public ByteBuffer getBody() {
        ByteBuffer buffer = new ByteBuffer();
        return buffer;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Strings.repeat("-",60));
        buffer.append(System.getProperty("line.separator"));
        buffer.append(String.format("%-8s %-32s", "pdu ", "enquire-link"));
        buffer.append(System.getProperty("line.separator"));
        buffer.append(String.format("header %-16s", super.toString()));
        buffer.append(System.getProperty("line.separator"));
        buffer.append(debugStringOptional());
        return buffer.toString();
    }

    public static void main(String[] args) {
        EnquireLinkRequest request = new EnquireLinkRequest();
        System.out.println(request.toString());
    }
}
