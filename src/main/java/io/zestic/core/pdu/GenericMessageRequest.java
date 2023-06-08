package io.zestic.core.pdu;

public class GenericMessageRequest extends PduRequest<GenericMessageResponse> {

    private String serviceType = "";
    private Integer esmClass = 0x00;
    private Integer protocolId = 0x00;
    private String scheduleDeliveryTime = "";
    private String validityPeriod = "";
    private Integer shortMessageLength = 0x00;

    public GenericMessageRequest() {
        super(Constants.GENERIC_REQUEST);
    }

    @Override
    public GenericMessageResponse createResponse() {
        return new GenericMessageResponse();
    }

    @Override
    public Class<GenericMessageResponse> getResponseClass() {
        return GenericMessageResponse.class;
    }

    public void setServiceType(String serviceType) throws WrongLengthOfStringException {
        checkCString(serviceType, Constants.SERVICE_TYPE_LENGTH);
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setEsmClass(Integer esmClass) {
        this.esmClass = esmClass;
    }

    public Integer getEsmClass() {
        return this.esmClass;
    }

    public void setProtocolId(Integer protocolId) {
        this.protocolId = protocolId;
    }

    public Integer getProtocolId() {
        return this.protocolId;
    }

    public void setScheduleDeliveryTime(String scheduleDeliveryTime) throws WrongDateFormatException {
        checkDate(scheduleDeliveryTime);
        this.scheduleDeliveryTime = scheduleDeliveryTime;
    }

    public String getScheduleDeliveryTime() {
        return this.scheduleDeliveryTime;
    }

    public void setValidityPeriod(String validityPeriod) throws WrongDateFormatException {
        checkDate(validityPeriod);
        this.validityPeriod = validityPeriod;
    }

    public String getValidityPeriod() {
        return this.validityPeriod;
    }

    // setSmLength() is private as it's set to length of the message
    private void setShortMessageLength(Integer shortMessageLength) {
        this.shortMessageLength = shortMessageLength;
    }

    public Integer getShortMessageLength() {
        return this.shortMessageLength;
    }

    @Override
    public void setBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, PduException {
        setServiceType(buffer.removeCString());
        setEsmClass(buffer.removeInt());
        setProtocolId(buffer.removeInt());
        setScheduleDeliveryTime(buffer.removeCString());
        setValidityPeriod(buffer.removeCString());
        setShortMessageLength(buffer.removeInt());
    }

    @Override
    public ByteBuffer getBody() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.appendCString(getServiceType());
        buffer.appendInt(getEsmClass());
        buffer.appendInt(getProtocolId());
        buffer.appendCString(getScheduleDeliveryTime());
        buffer.appendCString(getValidityPeriod());
        buffer.appendInt(getShortMessageLength());
        return buffer;
    }
}
