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
    public static final Integer ENQUIRE_LINK = 0x00000001;
    public static final Integer ENQUIRE_LINK_RESP = 0x80000001;
    public static final Integer GENERIC_REQUEST = 0x00000002;
    public static final Integer GENERIC_RESPONSE = 0x80000002;

    //Command status error codes
    public static final Integer STATUS_OK = 0x00000000;
    public static final Integer STATUS_INV_MSG_LEN = 0x00000001;

    public static final Map<Integer, String> STATUS_MESSAGE_MAP;

    static {
        STATUS_MESSAGE_MAP = new HashMap<Integer, String>();
        STATUS_MESSAGE_MAP.put(STATUS_OK, "OK");
        STATUS_MESSAGE_MAP.put(STATUS_INV_MSG_LEN, "Message length invalid");
    }
}
