package com.lazy.tcc.core;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.exception.CancelException;
import com.lazy.tcc.core.exception.ConfirmException;
import com.lazy.tcc.core.exception.TransactionManagerException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.propagator.TransactionContextPropagator;
import com.lazy.tcc.core.repository.TransactionRepository;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.threadpool.SysDefaultThreadPool;

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
public final class TransactionManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionManager.class);
    private final ThreadLocal<LinkedList<Transaction>> currentThreadTransactionHolder = new ThreadLocal<>();
    private final TransactionRepository transactionRepository = TransactionRepositoryFactory.create();
    private final ConcurrentHashMap<Class<? extends TransactionContextPropagator>, TransactionContextPropagator> propagatorHolder = new ConcurrentHashMap<>();

    private static TransactionManager single;

    private TransactionManager() {
    }

    public static TransactionManager getSingle() {
        if (single == null) {
            synchronized (TransactionManager.class) {
                if (single == null) {
                    single = new TransactionManager();
                }
            }
        }
        return single;
    }

    public Transaction begin() {

        Transaction transaction = new Transaction();
        transactionRepository.insert(transaction);
        currentThreadTransactionHolder.get().push(transaction);

        return transaction;
    }

    public void commit(boolean asyncCommit) {

        final Transaction transaction = getCurrentTransaction();

        assert transaction != null;
        transaction.setTxPhase(TransactionPhase.CONFIRM);
        transactionRepository.update(transaction);

        if (asyncCommit) {

            try {

                long statTime = System.currentTimeMillis();

                SysDefaultThreadPool.executeIoCrowdThreadTask(() -> commitTransaction(transaction));

                LOGGER.debug("async submit cost time:" + (System.currentTimeMillis() - statTime));
            } catch (Throwable commitException) {

                LOGGER.warn("compensable transaction async submit confirm failed, recovery job will try to confirm later.", commitException);
                throw new ConfirmException(commitException);
            }
        } else {

            commitTransaction(transaction);
        }
    }

    private void commitTransaction(Transaction transaction) {
        for (Participant participant : transaction.getParticipants()) {
            participant.confirm(this.getLocalTransactionContext());
        }

        this.transactionRepository.delete(transaction.getTxId());
    }

    public void rollback(boolean asyncCancel) {

        final Transaction transaction = getCurrentTransaction();
        assert transaction != null;
        transaction.setTxPhase(TransactionPhase.CANCEL);

        //If this step is unsuccessful, the timer compensates for rollback
        transactionRepository.update(transaction);

        if (asyncCancel) {

            try {

                SysDefaultThreadPool.executeIoCrowdThreadTask(() -> rollbackTransaction(transaction));

            } catch (Throwable rollbackException) {

                LOGGER.warn("compensable transaction async rollback failed, recovery job will try to rollback later.", rollbackException);
                throw new CancelException(rollbackException);
            }
        } else {

            rollbackTransaction(transaction);
        }
    }

    private void rollbackTransaction(Transaction transaction) {
        try {

            for (Participant participant : transaction.getParticipants()) {
                participant.cancel(this.getLocalTransactionContext());
            }

            transactionRepository.delete(transaction.getTxId());
        } catch (Throwable rollbackException) {

            LOGGER.warn("compensable transaction rollback failed, recovery job will try to rollback later.", rollbackException);
            throw new CancelException(rollbackException);
        }
    }

    public void participant(WeavingPointInfo pointInfo) {

    }

    public boolean hasDistributedActiveTransaction(WeavingPointInfo pointInfo) {
        if (this.hasLocalActiveTransaction()) {
            return true;
        }
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

    public void cleanCurrentTransaction(Transaction transaction) {
        if (hasLocalActiveTransaction()) {
            Transaction curTransaction = this.getCurrentTransaction();
            if (curTransaction == transaction) {
                currentThreadTransactionHolder.get().pop();

                this.transactionRepository.delete(transaction.getTxId());
            }
        }
    }

    public TransactionContext getLocalTransactionContext() {

        Transaction transaction = this.getCurrentTransaction();

        if (transaction == null) {
            return null;
        }

        return new TransactionContext()
                .setTxId(transaction.getTxId())
                .setTxPhase(transaction.getTxPhase());

    }


    public TransactionContext getDistributedTransactionContext(WeavingPointInfo pointInfo) {

        TransactionContext context = this.getLocalTransactionContext();

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
