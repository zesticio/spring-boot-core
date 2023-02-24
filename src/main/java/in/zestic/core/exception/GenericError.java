package in.zestic.core.exception;

import lombok.Getter;

@Getter
public enum GenericError implements ApplicationError {

    ROK(0x000000, "Success"),
    RTE_METHOD_NOT_IMPL(0x800001, "Runtime exception, Method not implemented");

    private Integer code;
    private String message;

    GenericError(Integer code, String mesg) {
        this.code = code;
        this.message = mesg;
    }
}
