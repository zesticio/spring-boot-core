package io.zestic.core.pdu;

public class TlvInteger extends Tlv {

    private int value = 0;

    public TlvInteger() {
        super(4, 4);
    }

    public TlvInteger(Short tag) {
        super(tag, 4, 4);
    }

    public TlvInteger(Short tag, Integer value) {
        super(tag, 4, 4);
        this.value = value;
        markValueSet();
    }

    protected void setValueData(ByteBuffer buffer) throws TlvException {
        checkLength(buffer);
        try {
            value = buffer.removeInt();
        } catch (NotEnoughDataInByteBufferException e) {
            // can't happen as the size is already checked by checkLength()
        }
        markValueSet();
    }

    protected ByteBuffer getValueData() throws ValueNotSetException {
        ByteBuffer valueBuf = new ByteBuffer();
        valueBuf.appendInt(getValue());
        return valueBuf;
    }

    public void setValue(int p_value) {
        value = p_value;
        markValueSet();
    }

    public int getValue() throws ValueNotSetException {
        if (hasValue()) {
            return value;
        } else {
            throw new ValueNotSetException();
        }
    }

    public String debugString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("int:");
        buffer.append(super.debugString());
        buffer.append(String.format("%s", value));
        return buffer.toString();
    }
}
