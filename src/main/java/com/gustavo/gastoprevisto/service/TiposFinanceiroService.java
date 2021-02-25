package com.gustavo.gastoprevisto.service;

import com.gustavo.gastoprevisto.domain.TiposFinanceiro;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TiposFinanceiro}.
 */
public interface TiposFinanceiroService {

    /**
     * Save a tiposFinanceiro.
     *
     * @param tiposFinanceiro the entity to save.
     * @return the persisted entity.
     */
    TiposFinanceiro save(TiposFinanceiro tiposFinanceiro);

    /**
     * Get all the tiposFinanceiros.
     *
     * @return the list of entities.
     */
    List<TiposFinanceiro> findAll();


    /**
     * Get the "id" tiposFinanceiro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TiposFinanceiro> findOne(Long id);

    /**
     * Delete the "id" tiposFinanceiro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
