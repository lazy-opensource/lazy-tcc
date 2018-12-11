package com.lazy.tcc.example.dubbo.shared.services.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDubbo(scanBasePackages = {
        "com.lazy.tcc.example.dubbo.shared.services.order.service"
})
@EnableJpaRepositories(
        value = {
                "com.lazy.tcc.example.dubbo.shared.services.order.repository"
        }
)
@EnableAspectJAutoProxy
@SpringBootApplication
public class SharedServicesOrderItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedServicesOrderItemApplication.class, args);
    }
}
