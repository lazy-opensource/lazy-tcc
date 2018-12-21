package com.lazy.tcc.lazy.tcc.dubbo.proxy.jdk;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.core.interceptor.IdempotentInterceptor;
import com.lazy.tcc.core.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;

/**
 * The Class From tcc-transaction
 */
public class LazyTccInvokerInvocationHandler extends com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler {

    private Object target;
    private static final TransactionInterceptor TRANSACTION_INTERCEPTOR = new TransactionInterceptor();
    private static final IdempotentInterceptor IDEMPOTENT_INTERCEPTOR = new IdempotentInterceptor();

    public LazyTccInvokerInvocationHandler(Invoker<?> handler) {
        super(handler);
    }

    public <T> LazyTccInvokerInvocationHandler(T target, Invoker<T> invoker) {
        super(invoker);
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Compensable compensable = method.getAnnotation(Compensable.class);
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        if (compensable != null) {

            return TRANSACTION_INTERCEPTOR.around(new MethodProceedingJoinPoint(proxy, target, method, args), compensable);
        } else if (idempotent != null) {

            return IDEMPOTENT_INTERCEPTOR.around(new MethodProceedingJoinPoint(proxy, target, method, args), idempotent);
        } else {

            return super.invoke(target, method, args);
        }
    }
}

