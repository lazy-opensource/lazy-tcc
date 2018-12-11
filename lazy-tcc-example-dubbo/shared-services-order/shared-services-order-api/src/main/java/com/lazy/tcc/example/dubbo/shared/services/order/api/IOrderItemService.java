package com.lazy.tcc.example.dubbo.shared.services.order.api;

import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderItemEntity;

/**
 * <p>
 * IOrderItemService
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface IOrderItemService {

    void save(TOrderItemEntity entity);

}
