package com.tp.bank.service.Impl;

import com.tp.bank.model.Compte;
import com.tp.bank.repository.CompteRepository;
import com.tp.bank.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;

    public CompteServiceImpl() {

    }

    @Override
    public Compte debit(Long idCompte, Double montant) {
        Compte compte = compteRepository.findById(idCompte).orElse(Compte.builder().solde(10000.0).build());

        compte.setSolde(compte.getSolde() - montant);
        return compteRepository.save(compte);

    }

}
