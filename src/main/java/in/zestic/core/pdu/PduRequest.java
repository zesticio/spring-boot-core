package in.zestic.core.pdu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class PduRequest<R extends PduResponse> extends Pdu {

    public PduRequest() {
    }

    @JsonIgnore
    abstract public R createResponse();

    @JsonIgnore
    abstract public Class<R> getResponseClass();
}
