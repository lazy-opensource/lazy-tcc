package com.lazy.tcc.core.processor;

import com.lazy.tcc.core.WeavingPointInfo;

/**
 * <p>
 * TransactionInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public interface TransactionProcessor {


    /**
     * processor
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable {@link Throwable}
     */
    Object processor(WeavingPointInfo pointInfo) throws Throwable;

}
