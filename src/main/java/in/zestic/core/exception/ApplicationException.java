package in.zestic.core.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationError error;

    public ApplicationException() {
        this.error = GenericError.ROK;
    }

    public ApplicationException(ApplicationError errorType) {
        this.error = errorType;
    }

    public ApplicationException(ApplicationError errorType, String message) {
        super(message);
        this.error = errorType;
    }

    public ApplicationException(ApplicationError errorType, String message, Throwable cause) {
        super(message, cause);
        this.error = errorType;
    }
}
