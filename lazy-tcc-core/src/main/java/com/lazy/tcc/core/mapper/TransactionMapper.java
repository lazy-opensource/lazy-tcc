package com.lazy.tcc.core.mapper;

import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.entity.TransactionEntity;

/**
 * <p>
 * TransactionMapper Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapper
    Transaction from(TransactionEntity to);

    @Mapper
    TransactionEntity to(Transaction from);

}
