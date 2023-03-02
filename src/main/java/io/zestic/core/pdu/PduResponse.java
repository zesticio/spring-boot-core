package io.zestic.core.pdu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PduResponse extends Pdu {

    @JsonProperty("message")
    private String message;
    @JsonProperty("result")
    private int result;

    public PduResponse() {
    }

    public PduResponse(int result, String message) {
        this.result = result;
        this.message = message;
    }
}