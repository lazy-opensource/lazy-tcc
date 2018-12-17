package com.lazy.tcc.core.annotation;

import com.lazy.tcc.core.propagator.IdempotentContextPropagator;
import com.lazy.tcc.core.propagator.TransactionContextPropagator;

import java.lang.annotation.*;

/**
 * <p>
 * Ensuring Idempotent by Frame
 * Configuration can only be effectively guaranteed if the service provider implements
 * the method. Definition on the service provider interface can not be effectively
 * guaranteed. There are uncertainties, such as timeouts.
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Idempotent {

    /**
     * Transaction propagator
     *
     * @return {@link TransactionContextPropagator}
     */
    Class<? extends IdempotentContextPropagator> propagator();

}
