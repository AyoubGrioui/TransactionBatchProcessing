package com.tp.bank.batchproccessing;

import com.tp.bank.model.Transaction;
import com.tp.bank.repository.TransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionItemWriter implements ItemWriter<Transaction> {
    @Autowired
    TransactionRepository transactionRepository;


    public TransactionItemWriter() {

    }

    @Override
    public void write(List<? extends Transaction> list) throws Exception {
        transactionRepository.saveAll(list);
    }
}
