package com.lazy.tcc.core;

/**
 * <p>
 * TransactionStatus Definition
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/14.
 */
public enum TransactionStatus {


    TRY(1),
    CONFIRM(2),
    CANCEL(3),;

    private int val;

    TransactionStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public TransactionStatus setVal(int val) {
        this.val = val;
        return this;
    }

    public static TransactionStatus valueOf(int val) {

        switch (val) {
            case 1:
                return TRY;
            case 2:
                return CONFIRM;
            default:
                return CANCEL;
        }
    }

}
