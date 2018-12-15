package com.lazy.tcc.core.exception;

/**
 * <p>
 *     TransactionInterceptorException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class TransactionInterceptorException extends RuntimeException {

    private static final long serialVersionUID = -5590745766939L;

    public TransactionInterceptorException() {
    }

    public TransactionInterceptorException(String message) {
        super(message);
    }

    public TransactionInterceptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionInterceptorException(Throwable cause) {
        super(cause);
    }

    public TransactionInterceptorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
