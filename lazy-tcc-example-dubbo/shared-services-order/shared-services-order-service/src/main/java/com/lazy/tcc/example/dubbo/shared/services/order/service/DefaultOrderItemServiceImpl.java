package com.lazy.tcc.example.dubbo.shared.services.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.example.dubbo.shared.services.order.repository.IOrderItemRepository;
import com.lazy.tcc.example.dubbo.shared.services.order.api.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Definition A Default Stock Service Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
@org.springframework.stereotype.Service
@Service(
        version = "${order.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultOrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;


}
