package com.gustavo.gastoprevisto.service;

import com.gustavo.gastoprevisto.domain.Financeiro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Financeiro}.
 */
public interface FinanceiroService {

    /**
     * Save a financeiro.
     *
     * @param financeiro the entity to save.
     * @return the persisted entity.
     */
    Financeiro save(Financeiro financeiro);

    /**
     * Get all the financeiros.
     *
     * @return the list of entities.
     */
    List<Financeiro> findAll();

    /**
     * Get all the financeiros with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Financeiro> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" financeiro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Financeiro> findOne(Long id);

    /**
     * Delete the "id" financeiro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
