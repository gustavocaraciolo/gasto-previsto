package com.gustavo.gastoprevisto.service.impl;

import com.gustavo.gastoprevisto.service.TiposFinanceiroService;
import com.gustavo.gastoprevisto.domain.TiposFinanceiro;
import com.gustavo.gastoprevisto.repository.TiposFinanceiroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TiposFinanceiro}.
 */
@Service
@Transactional
public class TiposFinanceiroServiceImpl implements TiposFinanceiroService {

    private final Logger log = LoggerFactory.getLogger(TiposFinanceiroServiceImpl.class);

    private final TiposFinanceiroRepository tiposFinanceiroRepository;

    public TiposFinanceiroServiceImpl(TiposFinanceiroRepository tiposFinanceiroRepository) {
        this.tiposFinanceiroRepository = tiposFinanceiroRepository;
    }

    @Override
    public TiposFinanceiro save(TiposFinanceiro tiposFinanceiro) {
        log.debug("Request to save TiposFinanceiro : {}", tiposFinanceiro);
        return tiposFinanceiroRepository.save(tiposFinanceiro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiposFinanceiro> findAll() {
        log.debug("Request to get all TiposFinanceiros");
        return tiposFinanceiroRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TiposFinanceiro> findOne(Long id) {
        log.debug("Request to get TiposFinanceiro : {}", id);
        return tiposFinanceiroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TiposFinanceiro : {}", id);
        tiposFinanceiroRepository.deleteById(id);
    }
}
