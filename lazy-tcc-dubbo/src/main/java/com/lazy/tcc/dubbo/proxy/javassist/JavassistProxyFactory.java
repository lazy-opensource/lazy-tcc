package com.lazy.tcc.dubbo.proxy.javassist;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;


public class JavassistProxyFactory extends com.alibaba.dubbo.rpc.proxy.javassist.JavassistProxyFactory {

    @SuppressWarnings({"unchecked"})
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        T proxy = (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));

        T tccProxy = (T) Proxy.getProxy(interfaces).newInstance(new TccInvokerInvocationHandler(proxy, invoker));


        return tccProxy;
    }
}
