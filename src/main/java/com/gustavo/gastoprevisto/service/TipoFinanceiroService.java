package com.gustavo.gastoprevisto.service;

import com.gustavo.gastoprevisto.domain.TipoFinanceiro;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoFinanceiro}.
 */
public interface TipoFinanceiroService {

    /**
     * Save a tipoFinanceiro.
     *
     * @param tipoFinanceiro the entity to save.
     * @return the persisted entity.
     */
    TipoFinanceiro save(TipoFinanceiro tipoFinanceiro);

    /**
     * Get all the tipoFinanceiros.
     *
     * @return the list of entities.
     */
    List<TipoFinanceiro> findAll();


    /**
     * Get the "id" tipoFinanceiro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoFinanceiro> findOne(Long id);

    /**
     * Delete the "id" tipoFinanceiro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
