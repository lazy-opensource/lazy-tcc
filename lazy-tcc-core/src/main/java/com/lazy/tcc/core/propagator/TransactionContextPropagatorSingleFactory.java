package com.lazy.tcc.core.propagator;

import com.lazy.tcc.core.exception.TransactionManagerException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class TransactionContextPropagatorSingleFactory {

    private final static ConcurrentHashMap<Class<? extends TransactionContextPropagator>,
            TransactionContextPropagator> propagatorHolder = new ConcurrentHashMap<>();


    public static TransactionContextPropagator create(Class<? extends TransactionContextPropagator> clazz) {

        if (propagatorHolder.isEmpty() || propagatorHolder.get(clazz) == null) {

            try {

                TransactionContextPropagator propagator = clazz.newInstance();
                propagatorHolder.put(clazz, propagator);
            } catch (Exception e) {
                throw new TransactionManagerException("Instantiating transaction propagator exceptions", e);
            }
        }

        return propagatorHolder.get(clazz);
    }
}
