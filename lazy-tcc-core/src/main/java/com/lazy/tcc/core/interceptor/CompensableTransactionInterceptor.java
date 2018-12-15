package com.lazy.tcc.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CompensableTransactionInterceptor.class);

    /**
     * processor
     */
    private final DistributedTransactionProcessor processor = DistributedTransactionProcessor.getSingle();

    @Around("@annotation(compensable)")
    public Object around(ProceedingJoinPoint joinPoint, Compensable compensable) throws Throwable {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("weaving point method: [%s], args: [%s], compensable config info: [%s]"
                    , joinPoint.getSignature(), JSON.toJSONString(joinPoint.getArgs()), JSON.toJSONString(compensable)));
        }

        //try phase processor
        return processor.processor(new WeavingPointInfo(compensable, joinPoint));
    }

}
