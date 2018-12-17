package com.lazy.tcc.core.propagator.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.propagator.support.AbstractTransactionContextPropagator;

/**
 * <p>
 * DubboTransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class DubboTransactionContextPropagator extends AbstractTransactionContextPropagator {

    @Override
    public void setContext(TransactionContext context) {

        if (context != null) {
            RpcContext.getContext().setAttachment(TX_PROPAGATOR_KEY, JSON.toJSONString(context));
        }
    }

    @Override
    public TransactionContext getContext() {

        String context = RpcContext.getContext().getAttachment(TX_PROPAGATOR_KEY);
        if (context == null) {
            return null;
        }

        return JSON.parseObject(context, TransactionContext.class);
    }
}
