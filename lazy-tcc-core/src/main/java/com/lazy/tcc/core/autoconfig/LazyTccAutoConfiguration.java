package com.lazy.tcc.core.autoconfig;

import com.lazy.tcc.core.BeanFactory;
import com.lazy.tcc.core.interceptor.IdempotentInterceptor;
import com.lazy.tcc.core.interceptor.TransactionInterceptor;
import com.lazy.tcc.core.listener.DefaultListener;
import com.lazy.tcc.core.spi.SpiConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * <p>
 * LazyTccAutoConfiguration Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/16.
 */
@Configuration
public class LazyTccAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public BeanFactory lazyTccBeanFactory() {
        return new BeanFactory();
    }

    @Bean
    public TransactionInterceptor lazyTccTransactionInterceptor() {
        return new TransactionInterceptor();
    }

    @Bean
    public IdempotentInterceptor lazyTccIdempotentInterceptor() {
        return new IdempotentInterceptor();
    }

    @Bean
    public DefaultListener defaultListener() {
        DataSource applicationDataSource = applicationContext.getBean(DataSource.class);
        return new DefaultListener(createDataSource(), applicationDataSource);
    }

    private DataSource createDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(SpiConfiguration.getInstance().getDatasourceDriver());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(SpiConfiguration.getInstance().getDatasourceUrl());
        dataSource.setUser(SpiConfiguration.getInstance().getDatasourceUsername());
        dataSource.setPassword(SpiConfiguration.getInstance().getDatasourcePassword());
        dataSource.setAutoCommitOnClose(true);


        return dataSource;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
