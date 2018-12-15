package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.SpiConfiguration;

/**
 * <p>
 * TransactionRepositoryFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class TransactionRepositoryFactory {


    private static TransactionRepository repository;

    public static TransactionRepository create() {
        if (repository == null) {
            synchronized (TransactionRepositoryFactory.class) {
                if (repository == null) {
                    try {
                        repository = SpiConfiguration.getInstance().getTxRepository().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return repository;
    }

}
