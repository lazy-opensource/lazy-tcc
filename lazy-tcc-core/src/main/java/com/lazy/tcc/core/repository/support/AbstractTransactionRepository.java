package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.entity.TransactionEntity;

/**
 * <p>
 *     AbstractTransactionRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public abstract class AbstractTransactionRepository extends AbstractCacheRepository<TransactionEntity, Long> {


    @Override
    public int createTable() {
        return 0;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }
}
