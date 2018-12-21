package com.lazy.tcc.example.dubbo.aggregate.services.retail.service;

import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.entity.TOrderEntity;

/**
 * <p>
 * IOrderService
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface IOrderService {

    @Compensable
    void save(TOrderEntity entity);

}
