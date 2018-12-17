package com.lazy.tcc.core.mapper;

import com.lazy.tcc.core.Idempotent;
import com.lazy.tcc.core.entity.IdempotentEntity;

/**
 * <p>
 * TransactionMapper Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
@Mapper
public interface IdempotentMapper {

    IdempotentMapper INSTANCE = Mappers.getMapper(IdempotentMapper.class);

    @Mapper
    Idempotent from(IdempotentEntity to);

    @Mapper
    IdempotentEntity to(Idempotent from);

}
