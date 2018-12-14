package com.lazy.tcc.core;

/**
 * <p>
 * Transaction Manager
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public interface ITransactionManager {

    void begin(TransactionContext context);

    void commit(TransactionContext context);

    void rollback(TransactionContext context);

}
