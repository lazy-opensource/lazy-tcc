package com.lazy.tcc.core;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.common.utils.StringUtils;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.core.exception.TransactionManagerException;
import com.lazy.tcc.core.propagator.IdempotentContextPropagator;
import com.lazy.tcc.core.propagator.IdempotentContextPropagatorSingleFactory;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>
 * Invoker Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
@EqualsAndHashCode
public class Invoker implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -9943454654L;


    private Long txId;

    private Class targetClass;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;

    private String reqSerialNum;

    public String getReqSerialNum() {
        return reqSerialNum;
    }

    public Invoker setReqSerialNum(String reqSerialNum) {
        this.reqSerialNum = reqSerialNum;
        return this;
    }

    public Long getTxId() {
        return txId;
    }

    public Invoker setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Invoker setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Invoker setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Invoker setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public Invoker setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    @SuppressWarnings({"unchecked"})
    void invoker(TransactionContext context) {

        if (!this.txId.equals(context.getTxId())) {
            throw new TransactionManagerException(String.format("invoker [%s] txId not match current transaction context [%s] txId"
                    , JSON.toJSONString(this), JSON.toJSONString(context)));
        }

        if (StringUtils.isNotBlank(this.getMethodName())) {

            try {

                Object target = BeanFactory.getSingle().getApplicationContext().getBean(this.targetClass);

                Method method = target.getClass().getMethod(this.getMethodName(), this.getParameterTypes());

                com.lazy.tcc.core.annotation.Idempotent idempotent = method.getAnnotation(Idempotent.class);

                //handler idempotent
                if (idempotent != null && idempotent.applicationRole().equals(ApplicationRole.CONSUMER)) {

                    IdempotentContextPropagatorSingleFactory.create(IdempotentContextPropagator.class).setIdempotentContext(
                            new IdempotentContext().setReqSerialNum(this.reqSerialNum)
                                    .setTxId(this.txId)
                                    .setTxPhase(context.getTxPhase())
                    );
                }

                //reflect invoker method
                method.invoke(target, this.getArgs());
            } catch (Exception e) {

                throw new TransactionManagerException(e);
            }
        }

    }

}
