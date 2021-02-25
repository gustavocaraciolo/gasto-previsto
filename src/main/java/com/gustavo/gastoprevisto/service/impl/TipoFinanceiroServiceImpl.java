package com.gustavo.gastoprevisto.service.impl;

import com.gustavo.gastoprevisto.service.TipoFinanceiroService;
import com.gustavo.gastoprevisto.domain.TipoFinanceiro;
import com.gustavo.gastoprevisto.repository.TipoFinanceiroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoFinanceiro}.
 */
@Service
@Transactional
public class TipoFinanceiroServiceImpl implements TipoFinanceiroService {

    private final Logger log = LoggerFactory.getLogger(TipoFinanceiroServiceImpl.class);

    private final TipoFinanceiroRepository tipoFinanceiroRepository;

    public TipoFinanceiroServiceImpl(TipoFinanceiroRepository tipoFinanceiroRepository) {
        this.tipoFinanceiroRepository = tipoFinanceiroRepository;
    }

    @Override
    public TipoFinanceiro save(TipoFinanceiro tipoFinanceiro) {
        log.debug("Request to save TipoFinanceiro : {}", tipoFinanceiro);
        return tipoFinanceiroRepository.save(tipoFinanceiro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoFinanceiro> findAll() {
        log.debug("Request to get all TipoFinanceiros");
        return tipoFinanceiroRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFinanceiro> findOne(Long id) {
        log.debug("Request to get TipoFinanceiro : {}", id);
        return tipoFinanceiroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoFinanceiro : {}", id);
        tipoFinanceiroRepository.deleteById(id);
    }
}
