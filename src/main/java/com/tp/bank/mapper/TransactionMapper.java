package com.tp.bank.mapper;

import com.tp.bank.dto.TransactionDto;
import com.tp.bank.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction dtoToentity(TransactionDto aDto);
}
