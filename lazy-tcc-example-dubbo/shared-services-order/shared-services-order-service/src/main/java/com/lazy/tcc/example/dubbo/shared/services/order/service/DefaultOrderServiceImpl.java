package com.lazy.tcc.example.dubbo.shared.services.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.example.dubbo.shared.services.order.api.IOrderService;
import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderEntity;
import com.lazy.tcc.example.dubbo.shared.services.order.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Definition A Default Order Service Impl
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
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class DefaultOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository repository;


    @Override
    public void save(TOrderEntity entity) {
        repository.save(entity);
    }
}
