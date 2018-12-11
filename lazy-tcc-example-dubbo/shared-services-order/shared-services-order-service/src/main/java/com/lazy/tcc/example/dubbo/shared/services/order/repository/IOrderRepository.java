package com.lazy.tcc.example.dubbo.shared.services.order.repository;


import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * IOrderItemRepository
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
public interface IOrderRepository extends JpaRepository<TOrderEntity, Long> {


}
