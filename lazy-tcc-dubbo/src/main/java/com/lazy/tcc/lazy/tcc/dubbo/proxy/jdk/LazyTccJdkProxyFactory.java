package com.lazy.tcc.lazy.tcc.dubbo.proxy.jdk;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.jdk.JdkProxyFactory;

import java.lang.reflect.Proxy;

/**
 * The Class From tcc-transaction
 */
public class LazyTccJdkProxyFactory extends JdkProxyFactory {

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {

        T proxy = (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler(invoker));

        T tccProxy = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new LazyTccInvokerInvocationHandler(proxy,invoker));

        return tccProxy;
    }
}