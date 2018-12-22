package com.lazy.tcc.core.listener;

import com.lazy.tcc.common.utils.StringUtils;
import com.lazy.tcc.core.exception.SystemException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.IdempotentRepositoryFactory;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.scheduler.CompensableTransactionScheduler;
import com.lazy.tcc.core.spi.SpiConfiguration;
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

            checkedConfigure();

            LOGGER.info("init transactionDataSource and idempotentDataSource");
            TransactionRepositoryFactory.create().setDataSource(transactionDataSource).createTable();
            IdempotentRepositoryFactory.create().setDataSource(idempotentDataSource).createTable();

            if (SpiConfiguration.getInstance().isEnableCompensableScheduler()) {
                new CompensableTransactionScheduler().init();
            }
        }
    }

    private void checkedConfigure() {
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getTxDatabaseName())) {
            throw new SystemException("please config lazy tcc getTxDatabaseName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getApplicationDatabaseName())) {
            throw new SystemException("please config lazy tcc getApplicationDatabaseName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getTxTableName())) {
            throw new SystemException("please config lazy tcc getTxTableName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getParticipantTableName())) {
            throw new SystemException("please config lazy tcc getParticipantTableName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getAppKeyTableName())) {
            throw new SystemException("please config lazy tcc getAppKeyTableName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getAppKey())) {
            throw new SystemException("please config lazy tcc getAppKey");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getIdempotentTableName())) {
            throw new SystemException("please config lazy tcc getIdempotentTableName");
        }
    }

}
