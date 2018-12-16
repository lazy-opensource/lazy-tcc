package com.lazy.tcc.core.annotation;

import com.lazy.tcc.common.enums.Propagation;
import com.lazy.tcc.core.propagator.LocalTransactionContextPropagator;
import com.lazy.tcc.core.propagator.TransactionContextPropagator;

import java.lang.annotation.*;

/**
 * <p>
 * Definition A Compensable Annotation
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Compensable {

    /**
     * Transaction communication mode
     *
     * @return {@link Propagation}
     * @see Propagation
     */
    Propagation propagation() default Propagation.REQUIRED;

    /**
     * confirm method
     *
     * @return {@link String}
     */
    String confirmMethod() default "";

    /**
     * cancel method
     *
     * @return {@link String}
     */
    String cancelMethod() default "";

    /**
     * async confirm
     *
     * @return {@link Boolean}
     */
    boolean asyncConfirm() default false;

    /**
     * async cancel
     *
     * @return {@link Boolean}
     */
    boolean asyncCancel() default false;

    /**
     * Transaction propagator
     *
     * @return {@link TransactionContextPropagator}
     */
    Class<? extends TransactionContextPropagator> propagator() default LocalTransactionContextPropagator.class;


}