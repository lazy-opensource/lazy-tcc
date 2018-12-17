package com.lazy.tcc.core.processor;

import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.processor.support.AbstractProcessor;

/**
 * <p>
 * IdempotentProcessor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class IdempotentProcessor extends AbstractProcessor {

    private static IdempotentProcessor single;

    public static IdempotentProcessor getSingle() {
        if (single == null) {
            synchronized (IdempotentProcessor.class) {
                if (single == null) {
                    single = new IdempotentProcessor();
                }
            }
        }
        return single;
    }


    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {

        return pointInfo.getIdempotent() != null;
    }

    /**
     *
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable
     */
    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {


        return null;
    }

}
