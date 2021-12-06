package com.tp.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransaction;
    private Double montant;
    private LocalDateTime dateTransaction;
    private LocalDateTime dateDebit;

    @ManyToOne
    @JoinColumn(name = "idCompte")
    private Compte compte;
}
