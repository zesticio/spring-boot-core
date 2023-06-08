package io.zestic.core.pdu;

public class EnquireLinkRequest extends PduRequest<EnquireLinkResponse> {

    public EnquireLinkRequest() {
        super(Constants.ENQUIRE_LINK);
    }

    @Override
    public void setData(ByteBuffer buffer) throws PduException, NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException {

    }

    @Override
    public ByteBuffer getData() throws ValueNotSetException {
        return null;
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
}
