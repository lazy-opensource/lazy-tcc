package com.lazy.tcc.core.autoconfig;

import com.lazy.tcc.core.BeanFactory;
import com.lazy.tcc.core.interceptor.IdempotentInterceptor;
import com.lazy.tcc.core.interceptor.TransactionInterceptor;
import com.lazy.tcc.core.scheduler.CompensableTransactionScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * LazyTccAutoConfiguration Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/16.
 */
@Configuration
public class LazyTccAutoConfiguration {

    @Bean
    public BeanFactory beanFactory() {
        return new BeanFactory();
    }

    @Bean
    public TransactionInterceptor transactionInterceptor() {
        return new TransactionInterceptor();
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor() {
        return new IdempotentInterceptor();
    }

    @Bean
    public CompensableTransactionScheduler compensableTransactionScheduler() {
        return new CompensableTransactionScheduler();
    }
}
