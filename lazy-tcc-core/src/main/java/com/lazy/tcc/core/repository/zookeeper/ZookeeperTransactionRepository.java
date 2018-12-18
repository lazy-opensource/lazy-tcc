package com.lazy.tcc.core.repository.zookeeper;

import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;

/**
 * <p>
 * ZookeeperRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class ZookeeperTransactionRepository extends AbstractTransactionRepository {


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
}
