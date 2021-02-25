package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.domain.Anexo;
import com.gustavo.gastoprevisto.repository.AnexoRepository;
import com.gustavo.gastoprevisto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gustavo.gastoprevisto.domain.Anexo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnexoResource {

    private final Logger log = LoggerFactory.getLogger(AnexoResource.class);

    private static final String ENTITY_NAME = "anexo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRepository anexoRepository;

    public AnexoResource(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    /**
     * {@code POST  /anexos} : Create a new anexo.
     *
     * @param anexo the anexo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexo, or with status {@code 400 (Bad Request)} if the anexo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anexos")
    public ResponseEntity<Anexo> createAnexo(@RequestBody Anexo anexo) throws URISyntaxException {
        log.debug("REST request to save Anexo : {}", anexo);
        if (anexo.getId() != null) {
            throw new BadRequestAlertException("A new anexo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anexo result = anexoRepository.save(anexo);
        return ResponseEntity.created(new URI("/api/anexos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anexos} : Updates an existing anexo.
     *
     * @param anexo the anexo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexo,
     * or with status {@code 400 (Bad Request)} if the anexo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anexos")
    public ResponseEntity<Anexo> updateAnexo(@RequestBody Anexo anexo) throws URISyntaxException {
        log.debug("REST request to update Anexo : {}", anexo);
        if (anexo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Anexo result = anexoRepository.save(anexo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anexos} : get all the anexos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexos in body.
     */
    @GetMapping("/anexos")
    public List<Anexo> getAllAnexos() {
        log.debug("REST request to get all Anexos");
        return anexoRepository.findAll();
    }

    /**
     * {@code GET  /anexos/:id} : get the "id" anexo.
     *
     * @param id the id of the anexo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anexos/{id}")
    public ResponseEntity<Anexo> getAnexo(@PathVariable Long id) {
        log.debug("REST request to get Anexo : {}", id);
        Optional<Anexo> anexo = anexoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(anexo);
    }

    /**
     * {@code DELETE  /anexos/:id} : delete the "id" anexo.
     *
     * @param id the id of the anexo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anexos/{id}")
    public ResponseEntity<Void> deleteAnexo(@PathVariable Long id) {
        log.debug("REST request to delete Anexo : {}", id);
        anexoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
