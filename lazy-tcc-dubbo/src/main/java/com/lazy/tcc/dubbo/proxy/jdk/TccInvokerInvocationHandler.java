package com.lazy.tcc.dubbo.proxy.jdk;


import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.interceptor.TransactionInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public class TccInvokerInvocationHandler extends InvokerInvocationHandler {

    private Object target;
    private final Invoker<?> invoker;

    public TccInvokerInvocationHandler(Invoker<?> handler) {
        super(handler);
        this.invoker = handler;
    }

    public <T> TccInvokerInvocationHandler(T target, Invoker<T> invoker) {
        super(invoker);
        this.target = target;
        this.invoker = invoker;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Compensable compensable = method.getAnnotation(Compensable.class);

        if (compensable != null) {

            ProceedingJoinPoint pjp = new MethodProceedingJoinPoint(proxy, invoker, target, method, args);
            return new TransactionInterceptor().around(pjp, compensable);
        } else {
            return super.invoke(target, method, args);
        }
    }


}
