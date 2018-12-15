package com.lazy.tcc.common.enums;

/**
 * <p>
 * TransactionPhase Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public enum TransactionPhase {


    TRY(1),
    CONFIRM(2),
    CANCEL(3),;

    private int val;

    TransactionPhase(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public TransactionPhase setVal(int val) {
        this.val = val;
        return this;
    }

    public static TransactionPhase valueOf(int val) {

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
