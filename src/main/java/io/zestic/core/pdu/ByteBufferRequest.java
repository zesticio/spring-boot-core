package io.zestic.core.pdu;

public class ByteBufferRequest extends PduRequest<ByteBufferResponse> {

    private String payload;
    private String hash;

    public ByteBufferRequest() {
        super(Constants.BYTE_BUFFER_REQUEST);
    }

    @Override
    public ByteBufferResponse createResponse() {
        return new ByteBufferResponse();
    }

    @Override
    public Class<ByteBufferResponse> getResponseClass() {
        return ByteBufferResponse.class;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    protected void setBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, PduException {
        setPayload(buffer.removeCString());
        setHash(buffer.removeCString());
    }

    @Override
    protected ByteBuffer getBody() {
        ByteBuffer body = new ByteBuffer();
        body.appendCString(payload);
        body.appendCString(hash);
        return body;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(System.getProperty("line.separator"));
        builder.append(String.format("%-4s %s: %s", " ", "payload", payload));
        builder.append(System.getProperty("line.separator"));
        builder.append(String.format("%-4s %s: %s", " ", "hash", hash));
        builder.append(System.getProperty("line.separator"));
        return builder.toString();
    }

}
