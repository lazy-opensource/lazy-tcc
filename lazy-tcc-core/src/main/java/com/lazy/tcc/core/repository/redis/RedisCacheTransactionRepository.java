package com.lazy.tcc.core.repository.redis;

import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.repository.support.AbstractCacheTransactionRepository;

/**
 * <p>
 * RedisTransactionRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class RedisCacheTransactionRepository extends AbstractCacheTransactionRepository {


    @Override
    public int doInsert(Transaction transaction) {
        return 0;
    }

    @Override
    public int doUpdate(Transaction transaction) {
        return 0;
    }

    @Override
    public int doDelete(Long id) {
        return 0;
    }

    @Override
    public Transaction doFindById(Long id) {
        return null;
    }
}
