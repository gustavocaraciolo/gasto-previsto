package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.domain.Propriedades;
import com.gustavo.gastoprevisto.service.PropriedadesService;
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
 * REST controller for managing {@link com.gustavo.gastoprevisto.domain.Propriedades}.
 */
@RestController
@RequestMapping("/api")
public class PropriedadesResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadesResource.class);

    private static final String ENTITY_NAME = "propriedades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropriedadesService propriedadesService;

    public PropriedadesResource(PropriedadesService propriedadesService) {
        this.propriedadesService = propriedadesService;
    }

    /**
     * {@code POST  /propriedades} : Create a new propriedades.
     *
     * @param propriedades the propriedades to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedades, or with status {@code 400 (Bad Request)} if the propriedades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedades")
    public ResponseEntity<Propriedades> createPropriedades(@RequestBody Propriedades propriedades) throws URISyntaxException {
        log.debug("REST request to save Propriedades : {}", propriedades);
        if (propriedades.getId() != null) {
            throw new BadRequestAlertException("A new propriedades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Propriedades result = propriedadesService.save(propriedades);
        return ResponseEntity.created(new URI("/api/propriedades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedades} : Updates an existing propriedades.
     *
     * @param propriedades the propriedades to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedades,
     * or with status {@code 400 (Bad Request)} if the propriedades is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedades couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedades")
    public ResponseEntity<Propriedades> updatePropriedades(@RequestBody Propriedades propriedades) throws URISyntaxException {
        log.debug("REST request to update Propriedades : {}", propriedades);
        if (propriedades.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Propriedades result = propriedadesService.save(propriedades);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propriedades.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propriedades} : get all the propriedades.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedades in body.
     */
    @GetMapping("/propriedades")
    public List<Propriedades> getAllPropriedades(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Propriedades");
        return propriedadesService.findAll();
    }

    /**
     * {@code GET  /propriedades/:id} : get the "id" propriedades.
     *
     * @param id the id of the propriedades to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedades, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedades/{id}")
    public ResponseEntity<Propriedades> getPropriedades(@PathVariable Long id) {
        log.debug("REST request to get Propriedades : {}", id);
        Optional<Propriedades> propriedades = propriedadesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedades);
    }

    /**
     * {@code DELETE  /propriedades/:id} : delete the "id" propriedades.
     *
     * @param id the id of the propriedades to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedades/{id}")
    public ResponseEntity<Void> deletePropriedades(@PathVariable Long id) {
        log.debug("REST request to delete Propriedades : {}", id);
        propriedadesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
