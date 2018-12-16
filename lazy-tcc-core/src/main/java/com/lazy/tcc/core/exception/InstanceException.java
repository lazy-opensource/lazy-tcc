package com.lazy.tcc.core.exception;

/**
 * <p>
 *     InstanceException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public class InstanceException extends RuntimeException {

    static final long serialVersionUID = -7945346575676456L;

    public InstanceException() {
    }

    public InstanceException(String message) {
        super(message);
    }

    public InstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceException(Throwable cause) {
        super(cause);
    }

    public InstanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
