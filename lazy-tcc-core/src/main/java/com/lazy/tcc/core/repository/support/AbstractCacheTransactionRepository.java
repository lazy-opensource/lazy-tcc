package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.repository.TransactionRepository;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.cache.CacheFactory;
import com.lazy.tcc.core.cache.Cache;

/**
 * <p>
 * AbstractTransactionRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public abstract class AbstractCacheTransactionRepository implements TransactionRepository {

    private Cache<Long, Transaction> cache = CacheFactory.create();

    @Override
    public int insert(Transaction transaction) {
        int count = this.doInsert(transaction);
        if (count > 0) {
            cache.put(transaction.getTxId(), transaction);
        }
        return count;
    }

    @Override
    public int update(Transaction transaction) {
        int count = this.doUpdate(transaction);
        if (count > 0) {
            cache.put(transaction.getTxId(), transaction);
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        int count = this.doDelete(id);
        if (count > 0) {
            cache.remove(id);
        }
        return count;
    }

    @Override
    public Transaction findById(Long id) {
        Transaction tx = cache.get(id);
        if (tx == null) {
            tx = this.doFindById(id);
            if (tx != null) {
                cache.put(id, tx);
            }
        }
        return tx;
    }


    public abstract int doInsert(Transaction transaction);

    public abstract int doUpdate(Transaction transaction);

    public abstract int doDelete(Long id);

    public abstract Transaction doFindById(Long id);

}
