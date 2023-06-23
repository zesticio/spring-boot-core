package io.zestic.core.pdu;

public class EnquireLinkRequest extends PduRequest<EnquireLinkResponse> {

    public EnquireLinkRequest() {
        super(Constants.ENQUIRE_LINK_REQUEST);
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
    protected void setBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException, PduException {
    }

    @Override
    protected ByteBuffer getBody() {
        ByteBuffer buffer = new ByteBuffer();
        return buffer;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(System.getProperty("line.separator"));
        return builder.toString();
    }

    public static void main(String[] args) {
        EnquireLinkRequest request = new EnquireLinkRequest();
        System.out.println(request.toString());
    }
}
