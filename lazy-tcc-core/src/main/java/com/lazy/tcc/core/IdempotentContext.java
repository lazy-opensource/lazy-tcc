package com.lazy.tcc.core;

/**
 * <p>
 *     IdempotentContext Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public class IdempotentContext extends TransactionContext {

    private String reqSerialNumber;

    public String getReqSerialNumber() {
        return reqSerialNumber;
    }

    public IdempotentContext setReqSerialNumber(String reqSerialNumber) {
        this.reqSerialNumber = reqSerialNumber;
        return this;
    }
}
