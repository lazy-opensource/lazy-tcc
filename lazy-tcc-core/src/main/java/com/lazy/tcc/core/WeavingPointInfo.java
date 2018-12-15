package com.lazy.tcc.core;

import com.lazy.tcc.core.annotation.Compensable;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>
 * WeavingPointInfo Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class WeavingPointInfo {


    private final Compensable compensable;
    private final ProceedingJoinPoint joinPoint;

    public WeavingPointInfo(Compensable compensable, ProceedingJoinPoint joinPoint) {
        this.compensable = compensable;
        this.joinPoint = joinPoint;

    }

    public Compensable getCompensable() {
        return compensable;
    }

    public ProceedingJoinPoint getJoinPoint() {
        return joinPoint;
    }
}
