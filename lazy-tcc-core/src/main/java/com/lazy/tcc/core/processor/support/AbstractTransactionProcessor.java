package com.lazy.tcc.core.processor.support;

import com.lazy.tcc.core.TransactionManager;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.processor.TransactionProcessor;

/**
 * <p>
 * AbstractTransactionInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public abstract class AbstractTransactionProcessor implements TransactionProcessor {

    /**
     * transactionManager
     */
    protected final TransactionManager transactionManager = new TransactionManager();

    /**
     * logger
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * is accept
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Boolean}
     */
    protected abstract boolean isAccept(WeavingPointInfo pointInfo);

    /**
     * doProcessor
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @throws Throwable {@link Throwable}
     */
    protected abstract Object doProcessor(WeavingPointInfo pointInfo) throws Throwable;

    @Override
    public Object processor(WeavingPointInfo pointInfo) throws Throwable {

        if (isAccept(pointInfo)) {

            return this.doProcessor(pointInfo);
        }

        return pointInfo.getJoinPoint().proceed();
    }
}
