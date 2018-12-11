package com.lazy.tcc.example.dubbo.aggregate.services.retail.retail.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.retail.exceptions.DataInconsistencyException;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.retail.service.IShopCartService;
import com.lazy.tcc.example.dubbo.shared.services.customer.ICustomerService;
import com.lazy.tcc.example.dubbo.shared.services.order.api.IOrderItemService;
import com.lazy.tcc.example.dubbo.shared.services.order.api.IOrderService;
import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderEntity;
import com.lazy.tcc.example.dubbo.shared.services.order.api.entity.TOrderItemEntity;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.IStockService;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.StockEditorDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * <p>
 * DefaultShopCartServiceImpl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@Service
public class DefaultShopCartServiceImpl implements IShopCartService {


    /**
     * Order Item Dubbo Service
     */
    @Reference(version = "1.0.0",
            application = "shared-services-order",
            url = "dubbo://localhost:9001")
    private IOrderItemService iOrderItemService;

    /**
     * Order Dubbo Service
     */
    @Reference(version = "1.0.0",
            application = "shared-services-order",
            url = "dubbo://localhost:9001")
    private IOrderService iOrderService;

    /**
     * Stock Dubbo Service
     */
    @Reference(version = "1.0.0",
            application = "shared-services-stock",
            url = "dubbo://localhost:9000")
    private IStockService iStockService;

    /**
     * Customer Dubbo Service
     */
    @Reference(version = "1.0.0",
            application = "shared-services-customer",
            url = "dubbo://localhost:9002")
    private ICustomerService iCustomerService;

    /**
     * markerException
     */
    private String markerException = null;


    @Override
    public void submitOrder() {
        //order
        TOrderEntity orderEntity = new TOrderEntity()
                .setCustomerNo("customer-no-1")
                .setOrderNo("5647344565462454232")
                .setItemEntityList(new ArrayList<>())
                .setTotalAmount(new BigDecimal("5000"));
        //order item
        TOrderItemEntity orderItemEntity1 = new TOrderItemEntity()
                .setTotalAmount(new BigDecimal("3000"))
                .setOrderNo(orderEntity.getOrderNo())
                .setProductNum(1)
                .setProductSku("product-sku-1");
        TOrderItemEntity orderItemEntity2 = new TOrderItemEntity()
                .setTotalAmount(new BigDecimal("2000"))
                .setOrderNo(orderEntity.getOrderNo())
                .setProductNum(1)
                .setProductSku("product-sku-2");
        //add item to order
        orderEntity.getItemEntityList().add(orderItemEntity1);
        orderEntity.getItemEntityList().add(orderItemEntity2);


        //deduct customer capital
        iCustomerService.deductCapital(orderEntity.getCustomerNo(), orderEntity.getTotalAmount());

        //If an exception occurs here, the data will be inconsistent
        //NullPointerException
//        markerException.toString();

        //deduct product stock
        iStockService.deductStock(new StockEditorDto()
                .setProductSku(orderItemEntity1.getProductSku())
                .setStockNum(orderItemEntity1.getProductNum()));
        iStockService.deductStock(new StockEditorDto()
                .setProductSku(orderItemEntity2.getProductSku())
                .setStockNum(orderItemEntity2.getProductNum()));

        //If an exception occurs here, the data will be inconsistent
        //NullPointerException
//        markerException.toString();

        //saved order
        iOrderItemService.save(orderItemEntity1);
        iOrderItemService.save(orderItemEntity2);
        iOrderService.save(orderEntity);

    }
}