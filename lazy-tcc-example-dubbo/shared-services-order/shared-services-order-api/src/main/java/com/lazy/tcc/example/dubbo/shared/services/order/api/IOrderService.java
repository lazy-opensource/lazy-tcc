package com.lazy.tcc.example.dubbo.shared.services.order.api;

import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderEntity;

/**
 * <p>
 * IOrderService
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface IOrderService {

    void save(TOrderEntity entity);

}
