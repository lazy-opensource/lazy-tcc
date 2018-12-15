package com.lazy.tcc.core.exception;

/**
 * <p>
 *     CrudIOException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public class CrudIOException extends RuntimeException {

    public CrudIOException() {
    }

    public CrudIOException(String message) {
        super(message);
    }

    public CrudIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrudIOException(Throwable cause) {
        super(cause);
    }

    public CrudIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
