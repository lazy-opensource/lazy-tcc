package com.lazy.tcc.core.listener;

import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.IdempotentRepositoryFactory;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.scheduler.CompensableTransactionScheduler;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.sql.DataSource;

/**
 * <p>
 * DefaultListener Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/19.
 */
public class DefaultListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultListener.class);

    private DataSource transactionDataSource;
    private DataSource idempotentDataSource;

    public DefaultListener(DataSource transactionDataSource, DataSource idempotentDataSource) {
        this.transactionDataSource = transactionDataSource;
        this.idempotentDataSource = idempotentDataSource;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {

            LOGGER.info("init transactionDataSource and idempotentDataSource");
            TransactionRepositoryFactory.create().setDataSource(transactionDataSource).createTable();
            IdempotentRepositoryFactory.create().setDataSource(idempotentDataSource).createTable();

            new CompensableTransactionScheduler().init();
        }
    }

}
