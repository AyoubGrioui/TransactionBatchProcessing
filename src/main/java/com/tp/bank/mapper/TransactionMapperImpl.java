package com.tp.bank.mapper;

import com.tp.bank.dto.TransactionDto;
import com.tp.bank.model.Compte;
import com.tp.bank.model.Transaction;

public class TransactionMapperImpl implements TransactionMapper {

    private static TransactionMapper INSTANCE = null;

    public static TransactionMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TransactionMapperImpl();
        }
        return INSTANCE;
    }

    @Override
    public Transaction dtoToentity(TransactionDto aDto) {
        if (aDto == null)
            return null;
        Transaction transaction = new Transaction();
        transaction.setIdTransaction(aDto.getIdTransaction());
        transaction.setDateTransaction(aDto.getDateTransaction());
        transaction.setMontant(aDto.getMontant());
        transaction.setCompte(new Compte() {{
            setIdCompte(aDto.getIdCompte());
        }});

        return transaction;
    }
}
