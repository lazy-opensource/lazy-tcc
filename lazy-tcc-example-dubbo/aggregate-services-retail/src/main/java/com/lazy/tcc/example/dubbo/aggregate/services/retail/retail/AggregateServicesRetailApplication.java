package com.lazy.tcc.example.dubbo.aggregate.services.retail.retail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class AggregateServicesRetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregateServicesRetailApplication.class, args);
    }
}
