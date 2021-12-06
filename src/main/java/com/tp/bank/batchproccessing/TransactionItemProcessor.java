package com.tp.bank.batchproccessing;

import com.tp.bank.dto.TransactionDto;
import com.tp.bank.mapper.TransactionMapperImpl;
import com.tp.bank.model.Compte;
import com.tp.bank.model.Transaction;
import com.tp.bank.repository.CompteRepository;
import com.tp.bank.service.CompteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionItemProcessor implements ItemProcessor<TransactionDto, Transaction> {

    private static final Logger log = LoggerFactory.getLogger(TransactionItemProcessor.class);

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteService compteService;


    public TransactionItemProcessor() {
    }

    @Override
    public Transaction process(TransactionDto atransaction) throws Exception {

        log.info("start proccessing" + atransaction.getIdTransaction());
        Transaction transaction = TransactionMapperImpl.getInstance().dtoToentity(atransaction);
        Compte compte = compteService.debit(atransaction.getIdCompte(), transaction.getMontant());
        transaction.setDateDebit(LocalDateTime.now());
        transaction.setCompte(compte);
        log.info("Processing transaction id = " + transaction.getIdTransaction());
        return transaction;
    }
}
