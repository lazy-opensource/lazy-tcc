package com.lazy.tcc.example.dubbo.shared.services.stock;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDubbo(scanBasePackages = {
        "com.lazy.tcc.example.dubbo.shared.services.stock.service"
})
@EnableJpaRepositories(
        value = {
                "com.lazy.tcc.example.dubbo.shared.services.stock.repository"
        }
)
@EnableAspectJAutoProxy
@SpringBootApplication
public class SharedServicesStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedServicesStockApplication.class, args);
    }
}
