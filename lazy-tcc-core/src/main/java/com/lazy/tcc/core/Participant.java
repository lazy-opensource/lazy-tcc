package com.lazy.tcc.core;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * Transaction participants
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
@EqualsAndHashCode
public class Participant implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 4565437665462L;

    private Long txId;
    private String cancelIdempotentId;
    private Invoker confirmMethodInvoker;
    private Invoker cancelMethodInvoker;

    public Long getTxId() {
        return txId;
    }

    public String getCancelIdempotentId() {
        return cancelIdempotentId;
    }

    public Participant setCancelIdempotentId(String cancelIdempotentId) {
        this.cancelIdempotentId = cancelIdempotentId;
        return this;
    }

    public Participant setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public Invoker getConfirmMethodInvoker() {
        return confirmMethodInvoker;
    }

    public Participant setConfirmMethodInvoker(Invoker confirmMethodInvoker) {
        this.confirmMethodInvoker = confirmMethodInvoker;
        return this;
    }

    public Invoker getCancelMethodInvoker() {
        return cancelMethodInvoker;
    }

    public Participant setCancelMethodInvoker(Invoker cancelMethodInvoker) {
        this.cancelMethodInvoker = cancelMethodInvoker;
        return this;
    }

    void confirm(TransactionContext context) {
        confirmMethodInvoker.invoker(context);
    }

    void cancel(TransactionContext context) {
        cancelMethodInvoker.invoker(context);
    }


}
