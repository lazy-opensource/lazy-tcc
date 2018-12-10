package com.lazy.tcc.example.dubbo.provider.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * TStockEntity
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
@Entity
@Table(name = "t_stock")
public class TStockEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8941982L;


    private String productNo;
    private Integer stockNum;
    private Integer frozenNum;
    @Version
    private long version;

    @Basic
    @Column(name = "frozen_num")
    public Integer getFrozenNum() {
        return frozenNum;
    }

    public TStockEntity setFrozenNum(Integer frozenNum) {
        this.frozenNum = frozenNum;
        return this;
    }

    @Basic
    @Column(name = "product_no")
    public String getProductNo() {
        return productNo;
    }

    public TStockEntity setProductNo(String productNo) {
        this.productNo = productNo;
        return this;
    }

    @Basic
    @Column(name = "stock_num")
    public Integer getStockNum() {
        return stockNum;
    }

    public TStockEntity setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
        return this;
    }

    @Basic
    @Column(name = "version")
    public long getVersion() {
        return version;
    }

    public TStockEntity setVersion(long version) {
        this.version = version;
        return this;
    }
}
