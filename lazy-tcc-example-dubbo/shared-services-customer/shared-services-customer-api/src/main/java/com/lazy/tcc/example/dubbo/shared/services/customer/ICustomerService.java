package com.lazy.tcc.example.dubbo.shared.services.customer;

import java.math.BigDecimal;

/**
 * <p>
 * ICustomerService
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface ICustomerService {

    /**
     * TCC - Try Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    void deductCapital(String customerNo, BigDecimal capital);


    /**
     * TCC - Confirm Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    void confirmDeductCapital(String customerNo, BigDecimal capital);


    /**
     * TCC - Cancel Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    void cancelDeductCapital(String customerNo, BigDecimal capital);

}
