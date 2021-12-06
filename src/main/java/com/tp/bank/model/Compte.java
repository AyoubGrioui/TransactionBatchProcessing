package com.tp.bank.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCompte;
    private Double solde;
}
