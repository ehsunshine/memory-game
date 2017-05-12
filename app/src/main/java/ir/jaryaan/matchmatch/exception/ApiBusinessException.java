package ir.jaryaan.matchmatch.exception;

/**
 * Created by ehsun on 5/12/2017.
 */

public class ApiBusinessException extends ApiException {

    public ApiBusinessException(String message) {
        super(message);
    }

    public ApiBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
