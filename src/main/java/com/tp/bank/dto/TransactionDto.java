package com.tp.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {
    private Long idTransaction;
    private Double montant;
    private LocalDateTime dateTransaction;
    private Long idCompte;
}
