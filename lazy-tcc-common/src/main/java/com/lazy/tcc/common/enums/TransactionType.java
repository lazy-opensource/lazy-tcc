package com.lazy.tcc.common.enums;

/**
 * <p>
 *     TransactionType Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public enum TransactionType {


    ROOT_TX(1),
    PARTICIPANT_TX(2),;

    private int val;

    TransactionType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public TransactionType setVal(int val) {
        this.val = val;
        return this;
    }
}
