package com.lazy.tcc.core.processor;

import com.lazy.tcc.common.utils.SnowflakeIdWorkerUtils;
import com.lazy.tcc.common.utils.StringUtils;
import com.lazy.tcc.core.Invoker;
import com.lazy.tcc.core.Participant;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.exception.TransactionManagerException;
import com.lazy.tcc.core.processor.support.AbstractProcessor;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 * ParticipantProcessor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class ParticipantProcessor extends AbstractProcessor {

    private static ParticipantProcessor single;

    public static ParticipantProcessor getSingle() {
        if (single == null) {
            synchronized (ParticipantProcessor.class) {
                if (single == null) {
                    single = new ParticipantProcessor();
                }
            }
        }
        return single;
    }


    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {
        return this.transactionManager.hasDistributedActiveTransaction(pointInfo)
                && StringUtils.isNotBlank(pointInfo.getCompensable().cancelMethod())
                && StringUtils.isNotBlank(pointInfo.getCompensable().confirmMethod());
    }

    /**
     * Ensure that the transaction participant log is inserted after the current
     * participant try phase is successfully executed
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable
     */
    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {

        //If this is an exception, the local transaction rolls back the business operation
        Object invokerVal = pointInfo.getJoinPoint().proceed();

        //add participant
        this.doParticipant(pointInfo);

        return invokerVal;
    }

    /**
     * participant
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @throws Throwable {@link Throwable}
     */
    void participant(WeavingPointInfo pointInfo) throws Throwable {

        if (!this.isAccept(pointInfo)) {
            return;
        }

        this.doParticipant(pointInfo);
    }

    /**
     * doParticipant
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @throws Throwable {@link Throwable}
     */
    private void doParticipant(WeavingPointInfo pointInfo) throws Throwable {

        Transaction transaction = this.transactionManager.getDistributedTransaction(pointInfo);

        if (transaction == null) {
            throw new TransactionManagerException("not exists active transaction");
        }

        Method method = ((MethodSignature) (pointInfo.getJoinPoint().getSignature())).getMethod();
        Invoker cancelInvoker = new Invoker()
                .setTxId(transaction.getTxId())
                .setArgs(pointInfo.getJoinPoint().getArgs())
                .setMethodName(pointInfo.getCompensable().cancelMethod())
                .setParameterTypes(method.getParameterTypes())
                .setTargetClass(method.getClass());

        Invoker confirmInvoker = new Invoker()
                .setTxId(transaction.getTxId())
                .setArgs(pointInfo.getJoinPoint().getArgs())
                .setMethodName(pointInfo.getCompensable().confirmMethod())
                .setParameterTypes(method.getParameterTypes())
                .setTargetClass(pointInfo.getJoinPoint().getTarget().getClass());

        transaction.getParticipants().add(
                new Participant()
                        .setCancelIdempotentId(String.valueOf(SnowflakeIdWorkerUtils.getSingle().nextId()))
                        .setTxId(transaction.getTxId())
                        .setCancelMethodInvoker(cancelInvoker)
                        .setConfirmMethodInvoker(confirmInvoker)
        );

        //add participant
        this.transactionManager.participant(transaction);
    }

    /**
     * cancel method
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Method}
     */
    private Method getCancelMethod(WeavingPointInfo pointInfo) {

        try {

            Method method = ((MethodSignature) (pointInfo.getJoinPoint().getSignature())).getMethod();

            Method cancelMethod = pointInfo.getJoinPoint().getTarget().getClass()
                    .getDeclaredMethod(pointInfo.getCompensable().cancelMethod(), method.getParameterTypes());

            if (cancelMethod == null){
                throw new TransactionManagerException("not definition cancel method " + pointInfo.getCompensable().cancelMethod());
            }
            return method;
        } catch (NoSuchMethodException e) {
            throw new TransactionManagerException(e);
        }
    }

    /**
     * confirm method
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Method}
     */
    private Method getConfirmMethod(WeavingPointInfo pointInfo) {

        try {

            Method method = ((MethodSignature) (pointInfo.getJoinPoint().getSignature())).getMethod();

            Method confirmMethod = pointInfo.getJoinPoint().getTarget().getClass()
                    .getDeclaredMethod(pointInfo.getCompensable().cancelMethod(), method.getParameterTypes());
            if (confirmMethod == null){
                throw new TransactionManagerException("not definition confirm method " + pointInfo.getCompensable().confirmMethod());
            }
            return method;
        } catch (NoSuchMethodException e) {
            throw new TransactionManagerException(e);
        }
    }
}
