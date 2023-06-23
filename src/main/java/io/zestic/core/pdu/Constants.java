package io.zestic.core.pdu;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final Integer PDU_INT_LENGTH = 4;
    public static final Integer PDU_HEADER_LENGTH = 16;
    public static final Integer DATE_LEN = 17;
    public static final Integer MAX_MESSAGE_LENGTH = 64;
    public static final Integer SERVICE_TYPE_LENGTH = 6;

    public static final int TLV_HEADER_SIZE = 4; // 2 shorts: tag & length

    //Command set
    public static final Integer DEFAULT_COMMAND_ID = 0x00000000;
    public static final Integer ENQUIRE_LINK_REQUEST = 0x00000001;
    public static final Integer ENQUIRE_LINK_RESPONSE = 0x80000001;
    public static final Integer BYTE_BUFFER_REQUEST = 0x00000002;
    public static final Integer BYTE_BUFFER_RESPONSE = 0x80000002;

    //Command status error codes
    public static final Integer STATUS_OK = 0x00000000;
    public static final Integer STATUS_INV_MSG_LEN = 0x00000001;

    public static final Map<Integer, String> STATUS_MESSAGE_MAP;
    public static final Map<Integer, String> COMMAND_SET_MAP;

    static {
        STATUS_MESSAGE_MAP = new HashMap<>();
        STATUS_MESSAGE_MAP.put(STATUS_OK, "OK (" + String.format("0x%08X", STATUS_OK) + ")");
        STATUS_MESSAGE_MAP.put(STATUS_INV_MSG_LEN, "Message length invalid (" + String.format("0x%08X", STATUS_INV_MSG_LEN) + ")");

        COMMAND_SET_MAP = new HashMap<>();
        COMMAND_SET_MAP.put(DEFAULT_COMMAND_ID, "Command id not set (" + String.format("0x%08X", DEFAULT_COMMAND_ID) + ")");
        COMMAND_SET_MAP.put(ENQUIRE_LINK_REQUEST, "Enquire link request (" + String.format("0x%08X", ENQUIRE_LINK_REQUEST) + ")");
        COMMAND_SET_MAP.put(ENQUIRE_LINK_RESPONSE, "Enquire link response (" + String.format("0x%08X", ENQUIRE_LINK_RESPONSE) + ")");
        COMMAND_SET_MAP.put(BYTE_BUFFER_REQUEST, "Byte buffer request (" + String.format("0x%08X", BYTE_BUFFER_REQUEST) + ")");
        COMMAND_SET_MAP.put(BYTE_BUFFER_RESPONSE, "Byte buffer response (" + String.format("0x%08X", BYTE_BUFFER_RESPONSE) + ")");
    }
}
