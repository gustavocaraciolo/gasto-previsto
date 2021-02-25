package com.gustavo.gastoprevisto.service;

import com.gustavo.gastoprevisto.domain.Propriedades;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Propriedades}.
 */
public interface PropriedadesService {

    /**
     * Save a propriedades.
     *
     * @param propriedades the entity to save.
     * @return the persisted entity.
     */
    Propriedades save(Propriedades propriedades);

    /**
     * Get all the propriedades.
     *
     * @return the list of entities.
     */
    List<Propriedades> findAll();

    /**
     * Get all the propriedades with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Propriedades> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" propriedades.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Propriedades> findOne(Long id);

    /**
     * Delete the "id" propriedades.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
