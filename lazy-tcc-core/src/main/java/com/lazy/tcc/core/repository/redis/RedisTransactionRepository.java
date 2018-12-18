package com.lazy.tcc.core.repository.redis;

import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;

import java.util.List;

/**
 * <p>
 * RedisRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class RedisTransactionRepository extends AbstractTransactionRepository {


    @Override
    public int doInsert(TransactionEntity transaction) {
        return 0;
    }

    @Override
    public int doUpdate(TransactionEntity transaction) {
        return 0;
    }

    @Override
    public int doDelete(Long id) {
        return 0;
    }

    @Override
    public TransactionEntity doFindById(Long id) {
        return null;
    }

    @Override
    public int createTable() {
        return 0;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public List<TransactionEntity> findAllFailure() {
        return null;
    }
}
