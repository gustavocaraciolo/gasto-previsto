package com.gustavo.gastoprevisto.service.impl;

import com.gustavo.gastoprevisto.service.PropriedadesService;
import com.gustavo.gastoprevisto.domain.Propriedades;
import com.gustavo.gastoprevisto.repository.PropriedadesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Propriedades}.
 */
@Service
@Transactional
public class PropriedadesServiceImpl implements PropriedadesService {

    private final Logger log = LoggerFactory.getLogger(PropriedadesServiceImpl.class);

    private final PropriedadesRepository propriedadesRepository;

    public PropriedadesServiceImpl(PropriedadesRepository propriedadesRepository) {
        this.propriedadesRepository = propriedadesRepository;
    }

    @Override
    public Propriedades save(Propriedades propriedades) {
        log.debug("Request to save Propriedades : {}", propriedades);
        return propriedadesRepository.save(propriedades);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Propriedades> findAll() {
        log.debug("Request to get all Propriedades");
        return propriedadesRepository.findAllWithEagerRelationships();
    }


    public Page<Propriedades> findAllWithEagerRelationships(Pageable pageable) {
        return propriedadesRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Propriedades> findOne(Long id) {
        log.debug("Request to get Propriedades : {}", id);
        return propriedadesRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Propriedades : {}", id);
        propriedadesRepository.deleteById(id);
    }
}
