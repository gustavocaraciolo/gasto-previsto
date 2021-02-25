package com.gustavo.gastoprevisto.service.impl;

import com.gustavo.gastoprevisto.service.AnexoService;
import com.gustavo.gastoprevisto.domain.Anexo;
import com.gustavo.gastoprevisto.repository.AnexoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Anexo}.
 */
@Service
@Transactional
public class AnexoServiceImpl implements AnexoService {

    private final Logger log = LoggerFactory.getLogger(AnexoServiceImpl.class);

    private final AnexoRepository anexoRepository;

    public AnexoServiceImpl(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    @Override
    public Anexo save(Anexo anexo) {
        log.debug("Request to save Anexo : {}", anexo);
        return anexoRepository.save(anexo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Anexo> findAll() {
        log.debug("Request to get all Anexos");
        return anexoRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Anexo> findOne(Long id) {
        log.debug("Request to get Anexo : {}", id);
        return anexoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anexo : {}", id);
        anexoRepository.deleteById(id);
    }
}
