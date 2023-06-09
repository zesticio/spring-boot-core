/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zestic.core.pdu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zestic.core.exception.InvalidPDUException;
import io.zestic.core.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

public abstract class Pdu extends ByteData {

    private static final Logger logger = LoggerFactory.getLogger(Pdu.class.getSimpleName());

    /**
     * This constant indicates that parsing of the buffer failed parsing of the header of PDU.
     *
     * @see #setData(ByteBuffer)
     * @see #valid
     */
    public static final byte VALID_NONE = 0;
    /**
     * This constant indicates that parsing of the buffer passed parsing of the header of PDU but failed parsing of
     * mandatory part of body.
     *
     * @see #setData(ByteBuffer)
     * @see #valid
     */
    public static final byte VALID_HEADER = 1;
    /**
     * This constant indicates that parsing of the buffer passed parsing of the mandatory part of body of PDU but
     * failed parsing of optional parameters.
     *
     * @see #setData(ByteBuffer)
     * @see #valid
     */
    public static final byte VALID_BODY = 2;
    /**
     * This constant indicates that parsing of the buffer passed all parts of the PDU, i.e. header, mandatory and
     * optional parameters.
     *
     * @see #setData(ByteBuffer)
     * @see #valid
     */
    public static final byte VALID_ALL = 3;

    /**
     * transaction id, unique for every transaction
     */
    private String transactionId = "";
    /**
     * Used to associate the current message with a previous message.
     * This header is commonly used to associate a response message with a
     * request message.
     */
    private String correlationId = "";

    /**
     * This is counter of sequence numbers. Each time the method <code>assignSequenceNumber</code> is called,
     * this counter is increased and the it's value is assigned as a sequence number of th PDU.
     */
    private static Integer sequenceNumber = 0;

    /**
     * Indicates that the sequence number has been changed either by setting by method <code>setSequenceNumber(int)
     * or by reading from buffer by method
     */
    private Boolean sequenceNumberChanged = false;

    /**
     * This indicates what stage was reached when parsing byte buffer in <code>setConstants</code> method.
     *
     * @see #VALID_NONE
     * @see #VALID_HEADER
     * @see #VALID_BODY
     * @see #VALID_ALL
     * @see #setData(ByteBuffer)
     */
    private byte valid = VALID_ALL;

    /**
     * This contains all optional parameters defined for particular concrete PDU. E.g. for submit_sm class
     * <code>SubmitShortMessageReq</code> puts here all it's possible optional parameters. It is used to build a byte
     * buffer from optional parameters as well as fill them from a buffer.
     */
    private Vector optionalParameters = new Vector(10, 2);

    /**
     * Contains optional parameters which aren't defined in the SMPP spec.
     */
    private Vector extraOptionalParameters = new Vector(1, 1);


    private Header header = null;

    public Pdu() {
        super();
    }

    public Pdu(Integer commandId) {
        super();
        checkHeader();
        setCommandId(commandId);
    }

    //Transaction and correlation ids for trace
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String value) {
        this.correlationId = value;
    }

    //Header related functions
    private ByteBuffer getHeader() {
        checkHeader();
        return header.getData();
    }

    public void setHeader(ByteBuffer buffer) throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, UnsupportedEncodingException {
        checkHeader();
        header.setData(buffer);
    }

    public void setCommandLength(int length) {
        checkHeader();
        header.setCommandLength(length);
    }

    public Integer getCommandLength() {
        checkHeader();
        return header.getCommandLength();
    }

    public Integer getSequenceNumber() {
        checkHeader();
        return header.getSequenceNumber();
    }

    public void setCommandStatus(Integer status) {
        checkHeader();
        this.header.setCommandStatus(status);
    }

    public Integer getCommandStatus() {
        checkHeader();
        return header.getCommandStatus();
    }

    public Integer getCommandId() {
        checkHeader();
        return header.getCommandId();
    }

    public void setCommandId(Integer id) {
        checkHeader();
        this.header.setCommandId(id);
    }

    protected void checkHeader() {
        if (header == null) {
            header = new Header();
        }
    }

    /**
     * Default method for setting mandatory parameters of the PDU. Derived classes should overwrite this method if
     * they want to fill their member variables with data from the binary data buffer.
     */
    public void setBody(ByteBuffer buffer)
            throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException, PduException {
    }

    /**
     * Default method for composing binary representation of the mandatory part of the PDU. Derived classes should
     * overwrite this method with composition of buffer from their member variables.
     */
    public ByteBuffer getBody() throws ValueNotSetException {
        return null;
    }

    /**
     * This method indicates that the object represents PDU which can and should be responded to.
     */
    public boolean canResponse() {
        return false;
    }

    /**
     * Returns if the object represents PDU which is a request. E.g. classes
     * derived from <code>Request</code> class return <code>true</code>.
     *
     * @return if the PDU represents request
     */
    public abstract boolean isRequest();

    /**
     * Returns if the object represents PDU which is response. E.g. classes
     * derived from <code>Response</code> class return <code>true</code>.
     *
     * @return if the PDU represents response
     */
    public abstract boolean isResponse();

    /**
     * Assigns newly generated sequence number if the sequence number hasn't been assigned yet. Doesn't have any
     * effect if the sequence number was already assigned.
     */
    public void assignSequenceNumber() {
        assignSequenceNumber(false);
    }

    /**
     * Assigns newly generated sequence number. If the sequence number was previously set by
     * <code>setSequenceNumber</code> method or from byte buffer in <code>setHeader</code>, this method only
     * assigns the number if the parameter <code>always</code> is true.
     */
    public void assignSequenceNumber(boolean always) {
        if ((!sequenceNumberChanged) || always) {
            setSequenceNumber(++sequenceNumber);
        }
    }

    /**
     * Sets the sequence number of the PDU. For PDUs whic are about to be sent to SMSC the sequence number is
     * generated automatically in the <code>Transmitter</code> class and under normal cicumstances there is no
     * need to set it explicitly.
     */
    public void setSequenceNumber(int seqNr) {
        checkHeader();
        header.setSequenceNumber(seqNr);
        sequenceNumberChanged = true;
    }

    /**
     * If the command status of the PDU is ESME_ROK.
     */
    public boolean isOk() {
        return getCommandStatus() == Constants.STATUS_OK;
    }

    /**
     * Sets if the PDU contains correctly formated data.
     */
    public void setValid(byte valid) {
        this.valid = valid;
    }

    /**
     * Parses the binary buffer to get the PDUs header, fields from mandatory part and fields from the optional part.
     * The header and optional part are parsed common way for all PDUs using functions <code>setHeader</code> and
     * <code>setOptionalBody</code> the mandatory body is parsed by the derived classes in <code>setBody</code> function.
     * If parsing throws an exception, the PDU's <code>getValid</code> function returns the phase which was correct last.
     * The buffer can contain more than one PDU, then only one PDU is taken from the buffer and the rest remains
     * unaltered.
     */
    @Override
    public void setData(ByteBuffer buffer) throws InvalidPDUException, PduException {
        int initialBufLen = buffer.length();
        try {
            setValid(VALID_NONE);
            // first try read header
            if (buffer.length() < Constants.PDU_HEADER_LENGTH) {
                logger.warn("not enough data for header in the buffer " + buffer.getHexDump());
            }

            // get the header from the buffer
            ByteBuffer headerBuf = buffer.removeBytes(Constants.PDU_HEADER_LENGTH);
            logger.debug("header " + headerBuf.getHexDump());
            setHeader(headerBuf);
            setValid(VALID_HEADER);

            // now read pdu's body for hex dump
            if (getCommandLength() > Constants.PDU_HEADER_LENGTH) {
                ByteBuffer tempBodyBuf = buffer.readBytes(getCommandLength() - Constants.PDU_HEADER_LENGTH);
                logger.debug("body " + tempBodyBuf.getHexDump());
            } else {
                logger.warn("no data for body " + buffer.getHexDump());
            }

            // parse the body
            setBody(buffer);
            setValid(VALID_BODY);
            if ((initialBufLen - buffer.length()) < getCommandLength()) {
                // i.e. parsed less than indicated by command length => must have optional parameters
                int optionalLength = getCommandLength() + buffer.length() - initialBufLen;
                logger.debug("have " + optionalLength + " bytes left.");
                ByteBuffer optionalBody = buffer.removeBuffer(optionalLength);
                setOptionalBody(optionalBody);
            }
            setValid(VALID_ALL);
        } catch (NotEnoughDataInByteBufferException e) {
            throw new InvalidPDUException(this, e);
        } catch (TerminatingZeroNotFoundException e) {
            throw new InvalidPDUException(this, e);
        } catch (PduException e) {
            e.setPDU(this);
            throw e;
        } catch (Exception e) {
            // transform generic exception into InvalidPDUException
            throw new InvalidPDUException(this, e);
        }

        if (buffer.length() != (initialBufLen - getCommandLength())) {
            // i.e. we've parsed out different number of bytes
            // than is specified by command length in the pdu's header
            throw new InvalidPDUException(this, "the parsed size of the message is not equal to command_length.");
        }
    }

    /**
     * Construct the binary PDU for sending to SMSC. First creates the mandatory part using <code>getBody</code> method,
     * which has to be implemented in the derived classes (if they represent PDU with body), then creates the optional
     * part of the PDU using <code>getOptionalBody</code>, which is common for all PDUs. Calculates the size of the PDU
     * and then creates the header using <code>getHeader</code> and returns the full binary PDU.
     */
    @Override
    public ByteBuffer getData() throws ValueNotSetException {
        // prepare all body
        ByteBuffer bodyBuf = new ByteBuffer();
        bodyBuf.appendBuffer(getBody());
        bodyBuf.appendBuffer(getOptionalBody());
        // get its size and add size of the header; set the result as length
        setCommandLength(bodyBuf.length() + Constants.PDU_HEADER_LENGTH);
        ByteBuffer pduBuf = getHeader();
        pduBuf.appendBuffer(bodyBuf);
        logger.debug("data " + pduBuf.getHexDump());
        return pduBuf;
    }

    /**
     * Creates buffer with all the optional parameters which have set their value, both from the optional parameters
     * defined in SMPP and extra optional parameters.
     */
    private ByteBuffer getOptionalBody() throws ValueNotSetException {
        ByteBuffer optBody = new ByteBuffer();
        optBody.appendBuffer(getOptionalBody(optionalParameters));
        optBody.appendBuffer(getOptionalBody(extraOptionalParameters));
        return optBody;
    }

    /**
     * Creates buffer with all the optional parameters contained in the <code>optionalParameters</code> list which have
     * set their value. For getting data of the optional parameter calls a method <code>getConstants</code> of the
     * <code>TLV</code> class.
     */
    private ByteBuffer getOptionalBody(Vector optionalParameters) throws ValueNotSetException {
        ByteBuffer optBody = new ByteBuffer();
        int size = optionalParameters.size();
        Tlv tlv = null;
        for (int i = 0; i < size; i++) {
            tlv = (Tlv) optionalParameters.get(i);
            if ((tlv != null) && tlv.hasValue()) {
                optBody.appendBuffer(tlv.getData());
            }
        }
        return optBody;
    }

    /**
     * Parses the binary buffer and obtains all optional parameters which the buffer contains, sets the optional
     * parameter fields. The optional parameter fields which the PDU can contain must be registered by the derived
     * class using <code>registerOptional</code>. Or there can be extra optional parameters with application/smsc
     * specific tags, which aren't defined in the SMPP specification. The optional parameters defined in SMPP are
     * accessible using appropriate getter functions in the derived PDU classes, the extra optional parameters are
     * accessible with generic function <code>getExtraOptional</code>. The buffer can't contain another data then
     * the optional parameters.
     */
    private void setOptionalBody(ByteBuffer buffer) throws NotEnoughDataInByteBufferException,
            UnexpectedOptionalParameterException, TlvException {
        short tag;
        short length;
        ByteBuffer tlvHeader;
        ByteBuffer tlvBuf;
        Tlv tlv = null;
        while (buffer.length() > 0) {
            // we prepare buffer with one parameter
            tlvHeader = buffer.readBytes(Constants.TLV_HEADER_SIZE);
            tag = tlvHeader.removeShort();
            tlv = findOptional(optionalParameters, tag);
            if (tlv == null) {
                // ok, got extra optional parameter not defined in SMPP spec
                // will keep it as octets
                tlv = new TlvOctets(tag);
                registerExtraOptional(tlv);
            }
            length = tlvHeader.removeShort();
            tlvBuf = buffer.removeBuffer(Constants.TLV_HEADER_SIZE + length);
            tlv.setData(tlvBuf);
        }
    }

    /**
     * Searches for registered or extra TLV with the given TLV tag. Returns the found TLV or null if not found. Used
     * when parsing the optional part of the binary PDU data.
     */
    private Tlv findOptional(Vector optionalParameters, short tag) {
        int size = optionalParameters.size();
        Tlv tlv = null;
        for (int i = 0; i < size; i++) {
            tlv = (Tlv) optionalParameters.get(i);
            if (tlv != null) {
                if (tlv.getTag() == tag) {
                    return tlv;
                }
            }
        }
        return null;
    }

    /**
     * Registeres a TLV as an extra optional parameter which can be contained in the PDU. Extra optional parameter is
     * TLV not defined in SMPP spec. The reason for this method is that if you know what type and size certain extra
     * optional parameter has to be, then you can create an instance of appropriate TLV class, e.g. <code>TLVInt</code>
     * class and register it as the carrying TLV instead of using the generic <code>TLVOctets</code>
     * class.
     */
    protected void registerExtraOptional(Tlv tlv) {
        if (tlv != null) {
            extraOptionalParameters.add(tlv);
        }
    }

    /**
     * Returns debug string from provided optional parameters.
     *
     * @param label
     * @param optionalParameters
     * @return
     */
    protected String debugStringOptional(String label, Vector optionalParameters) {
        StringBuilder buffer = new StringBuilder();
        int size = optionalParameters.size();
        if (size > 0) {
            buffer.append(String.format("%s ", label));
            Tlv tlv = null;
            for (int i = 0; i < size; i++) {
                tlv = (Tlv) optionalParameters.get(i);
                if ((tlv != null) && (tlv.hasValue())) {
                    buffer.append(String.format("%s ", tlv.toString()));
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Returns debug string of all optional parameters.
     *
     * @return
     */
    protected String debugStringOptional() {
        String dbgs = "";
        dbgs += debugStringOptional("opt", optionalParameters);
        dbgs += debugStringOptional("extraopt", extraOptionalParameters);
        return dbgs;
    }


    public byte[] toBytes() {
        return SerializationUtils.serialize(this);
    }

    public String hexDump() {
        return HexUtil.toHexString(toBytes());
    }

    public String toJson() {
        String json = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        buffer.append(String.format("0x%08X 0x%08X 0x%08X 0x%08X ",
                getCommandLength(),
                getCommandId(),
                getCommandStatus(),
                getSequenceNumber()
        ));
        buffer.append("]");
        return buffer.toString();
    }

    public static String hexdump(byte[] data) {
        final int perRow = 16;

        final String hexChars = "0123456789ABCDEF";
        StringBuilder dump = new StringBuilder();
        StringBuilder chars = null;
        for (int i = 0; i < data.length; i++) {
            int offset = i % perRow;
            if (offset == 0) {
                chars = new StringBuilder();
                dump.append(String.format("%04x", i))
                        .append("  ");
            }

            int b = data[i] & 0xFF;
            dump.append(hexChars.charAt(b >>> 4))
                    .append(hexChars.charAt(b & 0xF))
                    .append(' ');

            chars.append((char) ((b >= ' ' && b <= '~') ? b : '.'));

            if (i == data.length - 1 || offset == perRow - 1) {
                for (int j = perRow - offset - 1; j > 0; j--)
                    dump.append("-- ");
                dump.append("  ")
                        .append(chars)
                        .append('\n');
            }
        }
        return dump.toString();
    }
}
