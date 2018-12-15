package com.lazy.tcc.core.propagator;

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

        RpcContext.getContext().setAttachment(TX_PROPAGATOR_KEY, JSON.toJSONString(context));
    }

    @Override
    public TransactionContext getContext() {

        return JSON.parseObject(RpcContext.getContext().getAttachment(TX_PROPAGATOR_KEY), TransactionContext.class);
    }
}
