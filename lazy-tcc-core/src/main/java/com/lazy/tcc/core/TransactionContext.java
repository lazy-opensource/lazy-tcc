package com.lazy.tcc.core;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.enums.TransactionType;

/**
 * <p>
 * TransactionContext Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class TransactionContext {

    private Long txId;
    private TransactionPhase txPhase;
    private TransactionType txType;

    public TransactionType getTxType() {
        return txType;
    }

    public TransactionContext setTxType(TransactionType txType) {
        this.txType = txType;
        return this;
    }

    public Long getTxId() {
        return txId;
    }

    public TransactionContext setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionPhase getTxPhase() {
        return txPhase;
    }

    public TransactionContext setTxPhase(TransactionPhase txPhase) {
        this.txPhase = txPhase;
        return this;
    }
}
