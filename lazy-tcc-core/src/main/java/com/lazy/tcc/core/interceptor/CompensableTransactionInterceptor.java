package com.lazy.tcc.core.interceptor;

import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.processor.DistributedTransactionProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * <p>
 * CompensableTransactionInterceptro Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
@Aspect
public class CompensableTransactionInterceptor {


    /**
     * processor
     */
    private final DistributedTransactionProcessor processor = new DistributedTransactionProcessor();

    @Around("@annotation(compensable)")
    public Object around(ProceedingJoinPoint joinPoint, Compensable compensable) throws Throwable {

        //try phase processor
        return processor.processor(new WeavingPointInfo(compensable, joinPoint));
    }

}
