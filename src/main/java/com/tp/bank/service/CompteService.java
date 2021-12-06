package com.tp.bank.service;

import com.tp.bank.model.Compte;

public interface CompteService {
    Compte debit(Long idCompte, Double montant);
}
