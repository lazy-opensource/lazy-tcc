package com.lazy.tcc.core.processor;

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
public class DistributedTransactionProcessor extends AbstractTransactionProcessor {


    /**
     * try phase processor
     */
    private final TransactionProcessor processor = new TryPhaseTransactionProcessor();

    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {

        //default true
        return true;
    }

    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {
        return processor.processor(pointInfo);
    }
}
