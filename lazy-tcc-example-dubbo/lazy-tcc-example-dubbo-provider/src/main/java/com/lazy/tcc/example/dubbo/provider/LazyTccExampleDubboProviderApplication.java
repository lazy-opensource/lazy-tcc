package com.lazy.tcc.example.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <p>
 * Bootstrap
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
@EnableAspectJAutoProxy
@EnableDubbo(scanBasePackages = {"com.lazy.tcc.example.dubbo"})
@SpringBootApplication(scanBasePackages = {"com.lazy.tcc"})
public class LazyTccExampleDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LazyTccExampleDubboProviderApplication.class, args);
    }
}
