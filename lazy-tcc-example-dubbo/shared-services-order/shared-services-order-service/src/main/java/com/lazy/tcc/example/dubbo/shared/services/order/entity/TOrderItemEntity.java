package com.lazy.tcc.example.dubbo.shared.services.order.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * TOrderItemEntity
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
@Entity
@Table(name = "t_order_item")
@EqualsAndHashCode
public class TOrderItemEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -97888941982L;


    @Id
    private Long id;
    private String productSku;
    private Integer productNum;
    private String orderNo;
    private BigDecimal tatolAmount;

    public Long getId() {
        return id;
    }

    public TOrderItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "product_num")
    public Integer getProductNum() {
        return productNum;
    }

    public TOrderItemEntity setProductNum(Integer productNum) {
        this.productNum = productNum;
        return this;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public TOrderItemEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    @Basic
    @Column(name = "tatol_amount")
    public BigDecimal getTatolAmount() {
        return tatolAmount;
    }

    public TOrderItemEntity setTatolAmount(BigDecimal tatolAmount) {
        this.tatolAmount = tatolAmount;
        return this;
    }


}
