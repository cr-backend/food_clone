package kr.co.cr.food.exception;

public class OauthErrorException extends RuntimeException {
    public OauthErrorException() {
        super();
    }

    public OauthErrorException(String message) {
        super(message);
    }

    public OauthErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public OauthErrorException(Throwable cause) {
        super(cause);
    }

    protected OauthErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
