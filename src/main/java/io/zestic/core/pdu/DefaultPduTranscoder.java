package io.zestic.core.pdu;

public class DefaultPduTranscoder implements PduTranscoder {

    public DefaultPduTranscoder() {
    }

    @Override
    public ByteBuffer encode(Pdu pdu) {

        if (pdu instanceof PduResponse) {
            PduResponse response = (PduResponse) pdu;
            if (response.getResult() == null) {
                response.setResult(lookupResultMessage(pdu.getCommandStatus()));
            }
        }
        return null;
    }

    @Override
    public Pdu decode(ByteBuffer buffer) {
        return null;
    }

    public String lookupResultMessage(int commandStatus) {
        String resultMessage = null;
        if (resultMessage == null) {
            resultMessage = Constants.STATUS_MESSAGE_MAP.get(commandStatus);
        }
        return resultMessage;
    }
}
