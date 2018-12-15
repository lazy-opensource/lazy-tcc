package com.lazy.tcc.core.propagator;

import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.propagator.support.AbstractTransactionContextPropagator;

/**
 * <p>
 * LocalTransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class LocalTransactionContextPropagator extends AbstractTransactionContextPropagator{


    @Override
    public void setContext(TransactionContext context) {

    }

    @Override
    public TransactionContext getContext() {

        return null;
    }
}
