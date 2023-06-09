package io.zestic.core.pdu;

public class TlvString extends Tlv {

    private String value;

    public TlvString() {
        super();
    }

    public TlvString(Short tag) {
        super(tag);
    }

    public TlvString(Short tag, Integer min, Integer max) {
        super(tag, min, max);
    }

    public TlvString(Short tag, String value) throws TlvException {
        super(tag);
        setValue(value);
    }

    public TlvString(Short tag, Integer min, Integer max, String value)
            throws TlvException {
        super(tag, min, max);
        setValue(value);
    }

    public void setValueData(ByteBuffer buffer) throws TlvException {
        checkLength(buffer);
        if (buffer != null) {
            try {
                value = buffer.removeCString();
            } catch (NotEnoughDataInByteBufferException e) {
                throw new TlvException(
                        "Not enough data for string in the buffer.");
            } catch (TerminatingZeroNotFoundException e) {
                throw new TlvException(
                        "String terminating zero not found in the buffer.");
            }
        } else {
            value = new String("");
        }
        markValueSet();
    }

    public ByteBuffer getValueData() throws ValueNotSetException {
        ByteBuffer valueBuf = new ByteBuffer();
        valueBuf.appendCString(getValue());
        return valueBuf;
    }

    public void setValue(String value) throws WrongLengthException {
        checkLength(value.length() + 1);
        this.value = value;
        markValueSet();
    }

    public String getValue() throws ValueNotSetException {
        if (hasValue()) {
            return value;
        } else {
            throw new ValueNotSetException();
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("string:");
        buffer.append(super.toString());
        buffer.append(String.format("%s", value));
        return buffer.toString();
    }
}
