package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.repository.jdbc.MysqlTransactionRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

/**
 * <p>
 * TransactionRepositoryFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class TransactionRepositoryFactory {


    private TransactionRepositoryFactory() {
    }

    private static volatile MysqlTransactionRepository repository;

    public static MysqlTransactionRepository create() {
        if (repository == null) {
            synchronized (TransactionRepositoryFactory.class) {
                if (repository == null) {
                    try {
                        repository = (MysqlTransactionRepository) SpiConfiguration.getInstance().getTxRepository().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return repository;
    }

}
