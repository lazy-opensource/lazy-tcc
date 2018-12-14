package com.lazy.tcc.core;

import java.io.Serializable;

/**
 * <p>
 *     Invoker Definition
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/14.
 */
public class Invoker implements Serializable{

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -9943454654L;


    private Class targetClass;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;

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

    public void invoker(TransactionContext context){

    }
}
