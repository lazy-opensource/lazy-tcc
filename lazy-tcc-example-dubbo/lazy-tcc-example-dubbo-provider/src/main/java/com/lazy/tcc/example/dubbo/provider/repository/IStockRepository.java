package com.lazy.tcc.example.dubbo.provider.repository;

import com.lazy.tcc.example.dubbo.provider.entity.TStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 * IStockRepository
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
public interface IStockRepository extends JpaRepository<TStockEntity, Long> {

    /**
     * TCC - Try Deduct Stock Api
     *
     * @param productNo ProductNo {@link String}
     * @param stockNum  stockNum {@link Integer}
     */
    @Query(
            "update from TStockEntity set stockNum = stockNum - ?2, frozenNum = frozenNum + ?2 where productNo = ?1"
    )
    void tryDeductStock(String productNo, Integer stockNum);


    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param productNo ProductNo {@link String}
     * @param stockNum  stockNum {@link Integer}
     */
    @Query(
            "update from TStockEntity set frozenNum = frozenNum - ?2 where productNo = ?1"
    )
    void confirmDeductStock(String productNo, Integer stockNum);


    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param productNo ProductNo {@link String}
     * @param stockNum  stockNum {@link Integer}
     */
    @Query(
            "update from TStockEntity set frozenNum = frozenNum - ?2, stockNum = stockNum + ?2 where productNo = ?1"
    )
    void cancelDeductStock(String productNo, Integer stockNum);

}
