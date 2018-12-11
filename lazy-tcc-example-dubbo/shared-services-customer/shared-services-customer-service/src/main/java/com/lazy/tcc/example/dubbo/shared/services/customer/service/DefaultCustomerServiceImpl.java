package com.lazy.tcc.example.dubbo.shared.services.customer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.example.dubbo.shared.services.customer.ICustomerService;
import com.lazy.tcc.example.dubbo.shared.services.customer.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * DefaultCustomerServiceImpl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@org.springframework.stereotype.Service
@Service(
        version = "${customer.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultCustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository repository;



}
