package com.lazy.tcc.example.dubbo.shared.services.stock.api;

import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseDto;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.StockEditorDto;

/**
 * <p>
 * Definition A Stock Service Interface
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
public interface IStockService {

    /**
     * TCC - Try Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    SimpleResponseDto<String> deductStock(StockEditorDto dto);


    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    SimpleResponseDto<String> confirmDeductStock(StockEditorDto dto);


    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    SimpleResponseDto<String> cancelDeductStock(StockEditorDto dto);

}
