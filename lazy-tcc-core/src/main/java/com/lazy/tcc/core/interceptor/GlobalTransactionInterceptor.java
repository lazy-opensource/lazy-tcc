package com.lazy.tcc.core.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <p>
 * GlobalTransactionInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/14.
 */
@Aspect
public class GlobalTransactionInterceptor {


    @Pointcut("@annotation(com.lazy.tcc.common.annotation.Compensable)")
    public void definitionPoint() {

    }

    @Before("definitionPoint()")
    public void before(JoinPoint joinPoint) {

    }

}
