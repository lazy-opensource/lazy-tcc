package com.lazy.tcc.core.processor;

import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.processor.support.AbstractTransactionProcessor;

/**
 * <p>
 * DistributedParticipantProcessor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class DistributedParticipantProcessor extends AbstractTransactionProcessor {

    private static DistributedParticipantProcessor single;

    public static DistributedParticipantProcessor getSingle() {
        if (single == null) {
            synchronized (DistributedParticipantProcessor.class) {
                if (single == null) {
                    single = new DistributedParticipantProcessor();
                }
            }
        }
        return single;
    }


    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {
        return this.transactionManager.hasDistributedActiveTransaction(pointInfo);
    }

    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {
        return null;
    }
}
