package com.lazy.tcc.core.autoconfig;

import com.lazy.tcc.core.BeanFactory;
import com.lazy.tcc.core.interceptor.CompensableTransactionInterceptor;
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
    public CompensableTransactionInterceptor compensableTransactionInterceptor() {
        return new CompensableTransactionInterceptor();
    }

}