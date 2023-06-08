package io.zestic.core.pdu;

public class TlvOctets extends Tlv {

    private ByteBuffer value = null;

    public TlvOctets() {
        super();
    }

    public TlvOctets(short p_tag) {
        super(p_tag);
    }

    public TlvOctets(short p_tag, int min, int max) {
        super(p_tag, min, max);
    }

    public TlvOctets(short p_tag, ByteBuffer p_value) throws TlvException {
        super(p_tag);
        setValueData(p_value);
    }

    public TlvOctets(short p_tag, int min, int max, ByteBuffer p_value)
            throws TlvException {
        super(p_tag, min, max);
        setValueData(p_value);
    }

    protected void setValueData(ByteBuffer buffer) throws TlvException {
        checkLength(buffer);
        if (buffer != null) {
            try {
                value = buffer.removeBuffer(buffer.length());
            } catch (NotEnoughDataInByteBufferException e) {
                throw new Error(
                        "Removing buf.length() data from ByteBuffer buf "
                        + "reported too little data in buf, which shouldn't happen.");
            }
        } else {
            value = null;
        }
        markValueSet();
    }

    protected ByteBuffer getValueData() throws ValueNotSetException {
        ByteBuffer valueBuf = new ByteBuffer();
        valueBuf.appendBuffer(getValue());
        return valueBuf;
    }

    public void setValue(ByteBuffer p_value) {
        if (p_value != null) {
            try {
                value = p_value.removeBuffer(p_value.length());
            } catch (NotEnoughDataInByteBufferException e) {
                throw new Error(
                        "Removing buf.length() data from ByteBuffer buf "
                        + "reported too little data in buf, which shouldn't happen.");
            }
        } else {
            value = null;
        }
        markValueSet();
    }

    public ByteBuffer getValue() throws ValueNotSetException {
        if (hasValue()) {
            return value;
        } else {
            throw new ValueNotSetException();
        }
    }

    @Override
    public String debugString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("oct:");
        buffer.append(super.debugString());
        if (value == null) {
            buffer.append(String.format("%s", "NULL"));
        } else {
            buffer.append(String.format("%s", value.getHexDump()));
        }
        return buffer.toString();
    }

}
