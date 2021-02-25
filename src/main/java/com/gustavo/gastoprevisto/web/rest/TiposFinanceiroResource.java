package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.domain.TiposFinanceiro;
import com.gustavo.gastoprevisto.service.TiposFinanceiroService;
import com.gustavo.gastoprevisto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gustavo.gastoprevisto.domain.TiposFinanceiro}.
 */
@RestController
@RequestMapping("/api")
public class TiposFinanceiroResource {

    private final Logger log = LoggerFactory.getLogger(TiposFinanceiroResource.class);

    private static final String ENTITY_NAME = "tiposFinanceiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiposFinanceiroService tiposFinanceiroService;

    public TiposFinanceiroResource(TiposFinanceiroService tiposFinanceiroService) {
        this.tiposFinanceiroService = tiposFinanceiroService;
    }

    /**
     * {@code POST  /tipos-financeiros} : Create a new tiposFinanceiro.
     *
     * @param tiposFinanceiro the tiposFinanceiro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tiposFinanceiro, or with status {@code 400 (Bad Request)} if the tiposFinanceiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipos-financeiros")
    public ResponseEntity<TiposFinanceiro> createTiposFinanceiro(@RequestBody TiposFinanceiro tiposFinanceiro) throws URISyntaxException {
        log.debug("REST request to save TiposFinanceiro : {}", tiposFinanceiro);
        if (tiposFinanceiro.getId() != null) {
            throw new BadRequestAlertException("A new tiposFinanceiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiposFinanceiro result = tiposFinanceiroService.save(tiposFinanceiro);
        return ResponseEntity.created(new URI("/api/tipos-financeiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipos-financeiros} : Updates an existing tiposFinanceiro.
     *
     * @param tiposFinanceiro the tiposFinanceiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiposFinanceiro,
     * or with status {@code 400 (Bad Request)} if the tiposFinanceiro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiposFinanceiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipos-financeiros")
    public ResponseEntity<TiposFinanceiro> updateTiposFinanceiro(@RequestBody TiposFinanceiro tiposFinanceiro) throws URISyntaxException {
        log.debug("REST request to update TiposFinanceiro : {}", tiposFinanceiro);
        if (tiposFinanceiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TiposFinanceiro result = tiposFinanceiroService.save(tiposFinanceiro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tiposFinanceiro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipos-financeiros} : get all the tiposFinanceiros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiposFinanceiros in body.
     */
    @GetMapping("/tipos-financeiros")
    public List<TiposFinanceiro> getAllTiposFinanceiros() {
        log.debug("REST request to get all TiposFinanceiros");
        return tiposFinanceiroService.findAll();
    }

    /**
     * {@code GET  /tipos-financeiros/:id} : get the "id" tiposFinanceiro.
     *
     * @param id the id of the tiposFinanceiro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tiposFinanceiro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipos-financeiros/{id}")
    public ResponseEntity<TiposFinanceiro> getTiposFinanceiro(@PathVariable Long id) {
        log.debug("REST request to get TiposFinanceiro : {}", id);
        Optional<TiposFinanceiro> tiposFinanceiro = tiposFinanceiroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tiposFinanceiro);
    }

    /**
     * {@code DELETE  /tipos-financeiros/:id} : delete the "id" tiposFinanceiro.
     *
     * @param id the id of the tiposFinanceiro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipos-financeiros/{id}")
    public ResponseEntity<Void> deleteTiposFinanceiro(@PathVariable Long id) {
        log.debug("REST request to delete TiposFinanceiro : {}", id);
        tiposFinanceiroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
