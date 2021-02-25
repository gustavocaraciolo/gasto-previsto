package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.domain.Financeiro;
import com.gustavo.gastoprevisto.repository.FinanceiroRepository;
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
 * REST controller for managing {@link com.gustavo.gastoprevisto.domain.Financeiro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FinanceiroResource {

    private final Logger log = LoggerFactory.getLogger(FinanceiroResource.class);

    private static final String ENTITY_NAME = "financeiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanceiroRepository financeiroRepository;

    public FinanceiroResource(FinanceiroRepository financeiroRepository) {
        this.financeiroRepository = financeiroRepository;
    }

    /**
     * {@code POST  /financeiros} : Create a new financeiro.
     *
     * @param financeiro the financeiro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new financeiro, or with status {@code 400 (Bad Request)} if the financeiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/financeiros")
    public ResponseEntity<Financeiro> createFinanceiro(@RequestBody Financeiro financeiro) throws URISyntaxException {
        log.debug("REST request to save Financeiro : {}", financeiro);
        if (financeiro.getId() != null) {
            throw new BadRequestAlertException("A new financeiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Financeiro result = financeiroRepository.save(financeiro);
        return ResponseEntity.created(new URI("/api/financeiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /financeiros} : Updates an existing financeiro.
     *
     * @param financeiro the financeiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated financeiro,
     * or with status {@code 400 (Bad Request)} if the financeiro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the financeiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/financeiros")
    public ResponseEntity<Financeiro> updateFinanceiro(@RequestBody Financeiro financeiro) throws URISyntaxException {
        log.debug("REST request to update Financeiro : {}", financeiro);
        if (financeiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Financeiro result = financeiroRepository.save(financeiro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, financeiro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /financeiros} : get all the financeiros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of financeiros in body.
     */
    @GetMapping("/financeiros")
    public List<Financeiro> getAllFinanceiros() {
        log.debug("REST request to get all Financeiros");
        return financeiroRepository.findAll();
    }

    /**
     * {@code GET  /financeiros/:id} : get the "id" financeiro.
     *
     * @param id the id of the financeiro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the financeiro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/financeiros/{id}")
    public ResponseEntity<Financeiro> getFinanceiro(@PathVariable Long id) {
        log.debug("REST request to get Financeiro : {}", id);
        Optional<Financeiro> financeiro = financeiroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(financeiro);
    }

    /**
     * {@code DELETE  /financeiros/:id} : delete the "id" financeiro.
     *
     * @param id the id of the financeiro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/financeiros/{id}")
    public ResponseEntity<Void> deleteFinanceiro(@PathVariable Long id) {
        log.debug("REST request to delete Financeiro : {}", id);
        financeiroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
