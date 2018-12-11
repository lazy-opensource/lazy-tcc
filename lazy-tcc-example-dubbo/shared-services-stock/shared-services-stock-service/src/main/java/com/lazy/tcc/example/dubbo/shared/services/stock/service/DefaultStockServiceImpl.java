package com.lazy.tcc.example.dubbo.shared.services.stock.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.IStockService;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseDto;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.StockEditorDto;
import com.lazy.tcc.example.dubbo.shared.services.stock.repository.IStockRepository;
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
@Service(
        version = "${stock.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
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
        repository.tryDeductStock(dto.getProductSku(), dto.getStockNum());

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
        repository.confirmDeductStock(dto.getProductSku(), dto.getStockNum());

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
        repository.cancelDeductStock(dto.getProductSku(), dto.getStockNum());

        return responseSuccessfully();
    }
}
