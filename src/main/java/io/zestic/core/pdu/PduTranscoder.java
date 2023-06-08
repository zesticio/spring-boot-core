package io.zestic.core.pdu;

public interface PduTranscoder {

    /**
     * Encodes a PDU into a new Buffer.
     *
     * @param pdu
     * @return
     */
    public ByteBuffer encode(Pdu pdu);

    /**
     * Decodes a Buffer into a new PDU.
     *
     * @param buffer
     * @return
     */
    public Pdu decode(ByteBuffer buffer);
}
