package com.lazy.tcc.core.processor;

import com.lazy.tcc.common.enums.Propagation;
import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.processor.support.AbstractTransactionProcessor;

/**
 * <p>
 * TryPhaseTransactionInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class TryPhaseTransactionProcessor extends AbstractTransactionProcessor {


    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {

        TransactionContext context = this.transactionManager.getDistributedTransactionContext(pointInfo);

        return context == null || TransactionPhase.TRY.equals(context.getTxPhase());
    }

    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {

        Object invokeVal = null;
        Transaction transaction = null;

        try {

            if (isNewTransaction(pointInfo)) {

                transaction = this.transactionManager.begin(pointInfo);
            } else {

                this.transactionManager.participant(pointInfo);
            }

            try {

                invokeVal = pointInfo.getJoinPoint().proceed();
            } catch (Throwable ex) {

                this.transactionManager.rollback(pointInfo);
            }

            this.transactionManager.commit(pointInfo);
        } catch (Throwable ex) {

            this.transactionManager.cleanCurrentLocalTransaction(transaction);
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
