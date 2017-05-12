package ir.jaryaan.matchmatch.exception;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ApiUnknownException extends ApiException {

    public ApiUnknownException() {
    }

    public ApiUnknownException(String message) {
        super(message);
    }

    public ApiUnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiUnknownException(Throwable cause) {
        super(cause);
    }
}
