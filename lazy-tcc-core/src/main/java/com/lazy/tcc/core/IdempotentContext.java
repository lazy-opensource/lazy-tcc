package com.lazy.tcc.core;

import com.lazy.tcc.common.enums.TransactionPhase;

import java.io.Serializable;

/**
 * <p>
 * IdempotentContext Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public class IdempotentContext implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 6774623452L;

    private String reqSerialNum;
    private Long txId;
    private TransactionPhase txPhase;

    public Long getTxId() {
        return txId;
    }

    public IdempotentContext setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionPhase getTxPhase() {
        return txPhase;
    }

    public IdempotentContext setTxPhase(TransactionPhase txPhase) {
        this.txPhase = txPhase;
        return this;
    }

    public String getReqSerialNum() {
        return reqSerialNum;
    }

    public IdempotentContext setReqSerialNum(String reqSerialNum) {
        this.reqSerialNum = reqSerialNum;
        return this;
    }
}
