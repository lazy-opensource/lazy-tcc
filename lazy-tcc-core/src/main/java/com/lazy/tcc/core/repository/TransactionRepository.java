package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.Transaction;

/**
 * <p>
 * ITransactionRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/12.
 */
public interface TransactionRepository {

    int insert(Transaction transaction);

    int update(Transaction transaction);

    int delete(Long id);

    Transaction findById(Long id);

}
