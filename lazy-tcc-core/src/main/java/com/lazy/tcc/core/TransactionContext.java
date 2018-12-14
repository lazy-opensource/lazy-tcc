package com.lazy.tcc.core;

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
    private TransactionStatus txStatus;


    public Long getTxId() {
        return txId;
    }

    public TransactionContext setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionStatus getTxStatus() {
        return txStatus;
    }

    public TransactionContext setTxStatus(TransactionStatus txStatus) {
        this.txStatus = txStatus;
        return this;
    }
}
