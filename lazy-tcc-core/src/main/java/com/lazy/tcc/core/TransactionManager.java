package com.lazy.tcc.core;

import java.util.Queue;

/**
 * <p>
 * Transaction Manager
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class TransactionManager {

    private ThreadLocal<Queue<Transaction>> currentThreadTransaction = new ThreadLocal<>();

    public void begin(TransactionContext context) {

    }

    public void commit(TransactionContext context) {
        Transaction currentTransaction = currentThreadTransaction.get().peek();
        for (Participant participant : currentTransaction.getParticipants()) {
            participant.confirm(context);
        }
    }

    public void rollback(TransactionContext context) {
        Transaction currentTransaction = currentThreadTransaction.get().peek();
        for (Participant participant : currentTransaction.getParticipants()) {
            participant.cancel(context);
        }
    }

    public boolean isActive(){
        return currentThreadTransaction.get() != null && !currentThreadTransaction.get().isEmpty();
    }

    public void cleanup() {
        currentThreadTransaction.remove();
    }


}
