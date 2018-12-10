package com.lazy.tcc.example.dubbo.provider.service;

import com.lazy.tcc.example.dubbo.api.IStockService;
import com.lazy.tcc.example.dubbo.api.dto.SimpleResponseDto;
import com.lazy.tcc.example.dubbo.api.dto.StockEditorDto;
import com.lazy.tcc.example.dubbo.provider.repository.IStockRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Definition A Default Stock Service Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/12/10.
 */
@org.springframework.stereotype.Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = IStockService.class, group = "lazy-tcc", cluster = "failfast")
public class DefaultStockServiceImpl implements IStockService {

    @Autowired
    private IStockRepository repository;

    /**
     * TCC - Try Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Override
    public SimpleResponseDto<String> tryDeductStock(StockEditorDto dto) {
        repository.tryDeductStock(dto.getProductNo(), dto.getStockNum());

        return responseSuccessfully();
    }

    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Override
    public SimpleResponseDto<String> confirmDeductStock(StockEditorDto dto) {
        repository.confirmDeductStock(dto.getProductNo(), dto.getStockNum());

        return responseSuccessfully();
    }

    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Override
    public SimpleResponseDto<String> cancelDeductStock(StockEditorDto dto) {
        repository.cancelDeductStock(dto.getProductNo(), dto.getStockNum());

        return responseSuccessfully();
    }
}
