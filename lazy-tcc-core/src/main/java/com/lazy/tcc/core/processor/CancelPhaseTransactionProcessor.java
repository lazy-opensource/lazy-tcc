package com.lazy.tcc.core.processor;

import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.processor.support.AbstractTransactionProcessor;

/**
 * <p>
 *     CancelPhaseTransactionInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class CancelPhaseTransactionProcessor extends AbstractTransactionProcessor {


    @Override
    protected boolean isAccept() {
        return false;
    }

    @Override
    protected void doProcessor(WeavingPointInfo pointInfo) {

    }
}
