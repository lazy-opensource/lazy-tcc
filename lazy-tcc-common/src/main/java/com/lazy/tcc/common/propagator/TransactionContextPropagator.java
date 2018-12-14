package com.lazy.tcc.common.propagator;

import com.lazy.tcc.common.TransactionContext;

/**
 * <p>
 * TransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public abstract class TransactionContextPropagator {


    /**
     * set transaction propagator
     *
     * @param context transaction context
     */
    public abstract void setContext(TransactionContext context);

    /**
     * get transaction propagator
     *
     * @return {@link TransactionContext}
     */
    public abstract TransactionContext getContext();


}
