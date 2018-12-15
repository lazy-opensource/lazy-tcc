package com.lazy.tcc.core.processor;

import com.lazy.tcc.common.enums.Propagation;
import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.processor.support.AbstractTransactionProcessor;

/**
 * <p>
 * DistributedTransactionInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public final class DistributedTransactionProcessor extends AbstractTransactionProcessor {

    private static DistributedTransactionProcessor single;

    public static DistributedTransactionProcessor getSingle() {
        if (single == null) {
            synchronized (DistributedTransactionProcessor.class) {
                if (single == null) {
                    single = new DistributedTransactionProcessor();
                }
            }
        }
        return single;
    }

    private DistributedTransactionProcessor() {

    }

    private DistributedParticipantProcessor participantProcessor = DistributedParticipantProcessor.getSingle();

    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {

        TransactionContext context = this.transactionManager.getDistributedTransactionContext(pointInfo);

        return context == null || TransactionPhase.TRY.equals(context.getTxPhase());
    }

    @Override
    public Object processor(WeavingPointInfo pointInfo) throws Throwable {
        if (!isAccept(pointInfo)) {

            //not accept
            return pointInfo.getJoinPoint().proceed();
        }

        boolean isNewTransaction = this.isNewTransaction(pointInfo);

        //new transaction
        if (isNewTransaction) {

            return this.doProcessor(pointInfo);
        }

        //participant transaction
        return participantProcessor.processor(pointInfo);
    }

    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {

        Object invokeVal;
        Transaction transaction = null;

        try {

            //begin tranction
            transaction = this.transactionManager.begin();

            try {

                //execute program
                invokeVal = pointInfo.getJoinPoint().proceed();
            } catch (Throwable tryException) {

                logger.error("program exception, rollback transaction");
                this.transactionManager.rollback(pointInfo.getCompensable().asyncCancel());

                throw tryException;
            }

            //program execute success, commit transaction
            this.transactionManager.commit(pointInfo.getCompensable().asyncConfirm());
        } catch (Throwable ex) {

            logger.error("transaction exception, clean current transaction, Give job compensation transaction rollback", ex);
            this.transactionManager.cleanCurrentTransaction(transaction);

            throw ex;
        }

        return invokeVal;
    }

    /**
     * is new transaction
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Boolean}
     */
    private boolean isNewTransaction(WeavingPointInfo pointInfo) {

        TransactionContext context = this.transactionManager.getDistributedTransactionContext(pointInfo);
        if (context == null) {
            return true;
        }

        if (TransactionPhase.TRY.equals(context.getTxPhase()) &&
                Propagation.REQUIRES_NEW.equals(pointInfo.getCompensable().propagation())) {
            return true;
        }

        return false;
    }
}
