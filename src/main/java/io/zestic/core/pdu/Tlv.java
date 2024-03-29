package io.zestic.core.pdu;

import lombok.Data;

public abstract class Tlv extends ByteData {

    private Short tag = 0;
    private String tagName;      // short description of this tag

    /**
     * If the value was set to the TLV; if yes, then the optional param was
     * present in the PDU (for received PDUs) or it was set the value (for PDUs
     * which will be transmitted)
     */
    private boolean valueIsSet = false; // must be set by setValueData()

    /**
     * For checking the min/max length limits that the particular limit
     * <i>shouldn't</i> be checked.
     */
    private static int DONT_CHECK_LIMIT = -1;

    /**
     * The minimal length of the data. If no min length is required, then set to
     * <code>DONT_CHECK_LIMIT</code>.
     */
    private int minLength = DONT_CHECK_LIMIT;

    /**
     * The maximal length of the data. If no max length is required, then set to
     * <code>DONT_CHECK_LIMIT</code>.
     */
    private int maxLength = DONT_CHECK_LIMIT;

    public Tlv() {
        super();
    }

    /**
     * Sets only the tag of the TLV.
     */
    public Tlv(Short tag) {
        super();
        this.tag = tag;
    }

    /**
     * Sets minimal an maximal length; no tag set.
     */
    public Tlv(Integer min, Integer max) {
        super();
        minLength = min;
        maxLength = max;
    }

    /**
     * Sets all the necessary params of the TLV.
     */
    public Tlv(Short tag, Integer min, Integer max) {
        super();
        this.tag = tag;
        minLength = min;
        maxLength = max;
    }

    /**
     * Sets the data of the value carried by the TLV. Derived classes must
     * override this method to provide their data related parsing of the value.
     */
    protected abstract void setValueData(ByteBuffer buffer) throws TlvException;

    /**
     * Returns the data of the value carried by the TLV as binary data. Derived
     * classes must override this method to provide their data related creation
     * of the buffer based on the value.
     *
     * @throws ValueNotSetException if the TLV wasn't set any data then there
     *                              is nothing to be returned (the optional parameter isn't transmitted
     *                              then.)
     */
    protected abstract ByteBuffer getValueData() throws ValueNotSetException;

    /**
     * Sets the tag of this TLV.
     */
    public void setTag(Short tag) {
        this.tag = tag;
    }

    /**
     * Returns the tag of this TLV.
     */
    public Short getTag() {
        return tag;
    }

    /**
     * Returns the length of the actual binary data representing the value
     * carried by this TLV.
     *
     * @throws ValueNotSetException as this method uses
     *                              <code>getValueData</code> for calculation of the length this exception
     *                              can be thrown
     * @see #getValueData()
     */
    public int getLength() throws ValueNotSetException {
        if (hasValue()) {
            ByteBuffer valueBuf = getValueData();
            if (valueBuf != null) {
                return valueBuf.length();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Overwrites <code>ByteData</code>'s <code>setData</code> and parses tag,
     * length and the binary data value from the buffer. For parsing the data
     * value calls abstract <code>setValueData</code>.
     */
    @Override
    public void setData(ByteBuffer buffer)
            throws NotEnoughDataInByteBufferException,
            TlvException {
        short newTag = buffer.removeShort();
        int length = buffer.removeShort();
        ByteBuffer valueBuf = buffer.removeBuffer(length);
        setValueData(valueBuf);
        setTag(newTag);
    }

    /**
     * Returns the binary TLV created from tag, length and binary data value
     * carried by this TLV.
     */
    public ByteBuffer getData() throws ValueNotSetException {
        if (hasValue()) {
            ByteBuffer tlvBuf = new ByteBuffer();
            tlvBuf.appendShort(getTag().shortValue());
            tlvBuf.appendShort(encodeUnsigned(getLength()));
            tlvBuf.appendBuffer(getValueData());
            return tlvBuf;
        } else {
            return null;
        }
    }

    /**
     * 'Remembers' that the value was (somehow) set.
     */
    protected void markValueSet() {
        valueIsSet = true;
    }

    /**
     * Returns if the value has been set.
     */
    public boolean hasValue() {
        return valueIsSet;
    }

    /**
     * Compares this TLV to another TLV. TLV are equal if their tags are equal.
     */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof Tlv)) {
            return getTag() == ((Tlv) obj).getTag();
        }
        return false;
    }

    /**
     * Throws exception if the <code>length</code> provided isn't between
     * provided <code>min</code> and <code>max</code> inclusive.
     */
    protected static void checkLength(int min, int max, int length)
            throws WrongLengthException {
        if ((length < min) || (length > max)) {
            throw new WrongLengthException(min, max, length);
        }
    }

    /**
     * Throws exception if the length provided isn't between min and max lengths
     * of this TLV.
     */
    protected void checkLength(int length) throws WrongLengthException {
        int min = 0;
        int max = 0;
        if (minLength != DONT_CHECK_LIMIT) {
            min = minLength;
        } else {
            min = 0;
        }
        if (maxLength != DONT_CHECK_LIMIT) {
            max = maxLength;
        } else {
            max = Integer.MAX_VALUE;
        }
        checkLength(min, max, length);
    }

    /**
     * Throws exception if the length of the buffer provided isn't between min
     * and max lengths provided.
     */
    protected static void checkLength(int min, int max, ByteBuffer buffer)
            throws WrongLengthException {
        int length;
        if (buffer != null) {
            length = buffer.length();
        } else {
            length = 0;
        }
        checkLength(min, max, length);
    }

    /**
     * Throws exception if the length of the buffer provided isn't between min
     * and max lengths of this TLV.
     */
    protected void checkLength(ByteBuffer buffer) throws WrongLengthException {
        Integer length;
        if (buffer != null) {
            length = buffer.length();
        } else {
            length = 0;
        }
        checkLength(length);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("tlv ");
        buffer.append(String.format("%s", tag));
        return buffer.toString();
    }
}
