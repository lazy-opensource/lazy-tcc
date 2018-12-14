package com.lazy.tcc.core;

import java.io.Serializable;

/**
 * <p>
 * Transaction participants
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class Participant implements Serializable{

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 4565437665462L;

    private Invoker confirmMethodInvoker;
    private Invoker cancelMethodInvoker;

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

    void confirm(TransactionContext context){
        confirmMethodInvoker.invoker(context);
    }

    void cancel(TransactionContext context){
        cancelMethodInvoker.invoker(context);
    }


}
