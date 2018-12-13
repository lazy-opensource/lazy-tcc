package com.lazy.tcc.common.annotation;

import com.lazy.tcc.common.enums.Propagation;

import java.lang.annotation.*;

/**
 * <p>
 * Definition A Compensable
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




}
