package com.lazy.tcc.core;

import com.lazy.tcc.core.exception.TransactionManagerException;
import com.lazy.tcc.core.propagator.TransactionContextPropagator;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * Transaction Manager
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class TransactionManager {

    private ThreadLocal<LinkedList<Transaction>> currentThreadTransactionHolder = new ThreadLocal<>();
    private final ConcurrentHashMap<Class<? extends TransactionContextPropagator>, TransactionContextPropagator> propagatorHolder = new ConcurrentHashMap<>();

    public Transaction begin(WeavingPointInfo pointInfo) {

        return null;
    }

    public void commit(WeavingPointInfo pointInfo) {
        Transaction currentTransaction = currentThreadTransactionHolder.get().peek();
        for (Participant participant : currentTransaction.getParticipants()) {
            participant.confirm(getDistributedTransactionContext(pointInfo));
        }
    }

    public void rollback(WeavingPointInfo pointInfo) {
        Transaction currentTransaction = currentThreadTransactionHolder.get().peek();
        for (Participant participant : currentTransaction.getParticipants()) {
            participant.cancel(getDistributedTransactionContext(pointInfo));
        }
    }

    public void participant(WeavingPointInfo pointInfo) {

    }

    public boolean hasDistributedActiveTransaction(WeavingPointInfo pointInfo) {
        return this.getTransactionPropagator(pointInfo).getContext() != null;
    }

    public boolean hasLocalActiveTransaction() {
        return currentThreadTransactionHolder.get() != null && !currentThreadTransactionHolder.get().isEmpty();
    }

    public Transaction getCurrentTransaction() {
        if (hasLocalActiveTransaction()) {
            return currentThreadTransactionHolder.get().peek();
        }
        return null;
    }

    public void cleanCurrentLocalTransaction(Transaction transaction) {
        if (hasLocalActiveTransaction()) {
            Transaction curTransaction = this.getCurrentTransaction();
            if (curTransaction == transaction) {
                currentThreadTransactionHolder.get().pop();
            }
        }
    }

    public TransactionContext getLocalTransactionContext(WeavingPointInfo pointInfo) {

        Transaction transaction = this.getCurrentTransaction();

        if (transaction == null) {
            return null;
        }

        return new TransactionContext()
                .setTxId(transaction.getTxId())
                .setTxPhase(transaction.getTxPhase())
                .setTxType(transaction.getTxType());

    }


    public TransactionContext getDistributedTransactionContext(WeavingPointInfo pointInfo) {

        TransactionContext context = this.getLocalTransactionContext(pointInfo);

        if (context == null) {

            return this.getTransactionPropagator(pointInfo).getContext();
        }

        return context;

    }

    public TransactionContextPropagator getTransactionPropagator(WeavingPointInfo pointInfo) {

        Class<? extends TransactionContextPropagator> cls = pointInfo.getCompensable().propagator();

        if (propagatorHolder.isEmpty() || propagatorHolder.get(cls) == null) {

            try {

                TransactionContextPropagator propagator = cls.newInstance();
                propagatorHolder.put(cls, propagator);
            } catch (Exception e) {
                throw new TransactionManagerException("Instantiating transaction propagator exceptions", e);
            }
        }

        return propagatorHolder.get(cls);
    }


}
