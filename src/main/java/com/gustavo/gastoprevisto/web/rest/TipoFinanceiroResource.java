package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.domain.TipoFinanceiro;
import com.gustavo.gastoprevisto.service.TipoFinanceiroService;
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
 * REST controller for managing {@link com.gustavo.gastoprevisto.domain.TipoFinanceiro}.
 */
@RestController
@RequestMapping("/api")
public class TipoFinanceiroResource {

    private final Logger log = LoggerFactory.getLogger(TipoFinanceiroResource.class);

    private static final String ENTITY_NAME = "tipoFinanceiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoFinanceiroService tipoFinanceiroService;

    public TipoFinanceiroResource(TipoFinanceiroService tipoFinanceiroService) {
        this.tipoFinanceiroService = tipoFinanceiroService;
    }

    /**
     * {@code POST  /tipo-financeiros} : Create a new tipoFinanceiro.
     *
     * @param tipoFinanceiro the tipoFinanceiro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoFinanceiro, or with status {@code 400 (Bad Request)} if the tipoFinanceiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-financeiros")
    public ResponseEntity<TipoFinanceiro> createTipoFinanceiro(@RequestBody TipoFinanceiro tipoFinanceiro) throws URISyntaxException {
        log.debug("REST request to save TipoFinanceiro : {}", tipoFinanceiro);
        if (tipoFinanceiro.getId() != null) {
            throw new BadRequestAlertException("A new tipoFinanceiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoFinanceiro result = tipoFinanceiroService.save(tipoFinanceiro);
        return ResponseEntity.created(new URI("/api/tipo-financeiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-financeiros} : Updates an existing tipoFinanceiro.
     *
     * @param tipoFinanceiro the tipoFinanceiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoFinanceiro,
     * or with status {@code 400 (Bad Request)} if the tipoFinanceiro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoFinanceiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-financeiros")
    public ResponseEntity<TipoFinanceiro> updateTipoFinanceiro(@RequestBody TipoFinanceiro tipoFinanceiro) throws URISyntaxException {
        log.debug("REST request to update TipoFinanceiro : {}", tipoFinanceiro);
        if (tipoFinanceiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoFinanceiro result = tipoFinanceiroService.save(tipoFinanceiro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoFinanceiro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-financeiros} : get all the tipoFinanceiros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoFinanceiros in body.
     */
    @GetMapping("/tipo-financeiros")
    public List<TipoFinanceiro> getAllTipoFinanceiros() {
        log.debug("REST request to get all TipoFinanceiros");
        return tipoFinanceiroService.findAll();
    }

    /**
     * {@code GET  /tipo-financeiros/:id} : get the "id" tipoFinanceiro.
     *
     * @param id the id of the tipoFinanceiro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoFinanceiro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-financeiros/{id}")
    public ResponseEntity<TipoFinanceiro> getTipoFinanceiro(@PathVariable Long id) {
        log.debug("REST request to get TipoFinanceiro : {}", id);
        Optional<TipoFinanceiro> tipoFinanceiro = tipoFinanceiroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoFinanceiro);
    }

    /**
     * {@code DELETE  /tipo-financeiros/:id} : delete the "id" tipoFinanceiro.
     *
     * @param id the id of the tipoFinanceiro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-financeiros/{id}")
    public ResponseEntity<Void> deleteTipoFinanceiro(@PathVariable Long id) {
        log.debug("REST request to delete TipoFinanceiro : {}", id);
        tipoFinanceiroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
