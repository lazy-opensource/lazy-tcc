package com.lazy.tcc.example.dubbo.shared.services.customer.repository;

import com.lazy.tcc.example.dubbo.shared.services.customer.entity.TCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * ICustomerRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface ICustomerRepository extends JpaRepository<TCustomerEntity, Long> {
}
