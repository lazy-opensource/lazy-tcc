package com.lazy.tcc.example.dubbo.shared.services.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderItemEntity;
import com.lazy.tcc.example.dubbo.shared.services.order.repository.IOrderItemRepository;
import com.lazy.tcc.example.dubbo.shared.services.order.api.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Definition A Default Order Item Service Impl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@org.springframework.stereotype.Service
@Service(
        version = "${order.item.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class DefaultOrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;


    @Override
    public void save(TOrderItemEntity entity) {
        repository.save(entity);
    }
}
