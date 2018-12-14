package com.lazy.tcc.core.exception;

/**
 * <p>
 * ConfirmException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class TryException extends RuntimeException {

    static final long serialVersionUID = -3546575439L;

    public TryException() {
    }

    public TryException(String message) {
        super(message);
    }

    public TryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TryException(Throwable cause) {
        super(cause);
    }

    public TryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
