package com.gustavo.gastoprevisto.service.impl;

import com.gustavo.gastoprevisto.service.FinanceiroService;
import com.gustavo.gastoprevisto.domain.Financeiro;
import com.gustavo.gastoprevisto.repository.FinanceiroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Financeiro}.
 */
@Service
@Transactional
public class FinanceiroServiceImpl implements FinanceiroService {

    private final Logger log = LoggerFactory.getLogger(FinanceiroServiceImpl.class);

    private final FinanceiroRepository financeiroRepository;

    public FinanceiroServiceImpl(FinanceiroRepository financeiroRepository) {
        this.financeiroRepository = financeiroRepository;
    }

    @Override
    public Financeiro save(Financeiro financeiro) {
        log.debug("Request to save Financeiro : {}", financeiro);
        return financeiroRepository.save(financeiro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Financeiro> findAll() {
        log.debug("Request to get all Financeiros");
        return financeiroRepository.findAllWithEagerRelationships();
    }


    public Page<Financeiro> findAllWithEagerRelationships(Pageable pageable) {
        return financeiroRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Financeiro> findOne(Long id) {
        log.debug("Request to get Financeiro : {}", id);
        return financeiroRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Financeiro : {}", id);
        financeiroRepository.deleteById(id);
    }
}
