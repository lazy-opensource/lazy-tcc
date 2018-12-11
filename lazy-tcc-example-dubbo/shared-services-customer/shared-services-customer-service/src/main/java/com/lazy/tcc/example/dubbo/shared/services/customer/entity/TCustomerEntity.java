package com.lazy.tcc.example.dubbo.shared.services.customer.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@Table(name = "t_customer")
@Entity
@EqualsAndHashCode
public class TCustomerEntity implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 897857441982L;


    @Id
    private Long id;
    private String customerNo;
    private Integer tatolCapaital;
    private Integer frozenCapaital;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public TCustomerEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "customer_no")
    public String getCustomerNo() {
        return customerNo;
    }

    public TCustomerEntity setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    @Basic
    @Column(name = "tatol_capaital")
    public Integer getTatolCapaital() {
        return tatolCapaital;
    }

    public TCustomerEntity setTatolCapaital(Integer tatolCapaital) {
        this.tatolCapaital = tatolCapaital;
        return this;
    }

    @Basic
    @Column(name = "frozen_capaital")
    public Integer getFrozenCapaital() {
        return frozenCapaital;
    }

    public TCustomerEntity setFrozenCapaital(Integer frozenCapaital) {
        this.frozenCapaital = frozenCapaital;
        return this;
    }

    @Basic
    @Column(name = "version")
    public long getVersion() {
        return version;
    }

    public TCustomerEntity setVersion(long version) {
        this.version = version;
        return this;
    }
}
