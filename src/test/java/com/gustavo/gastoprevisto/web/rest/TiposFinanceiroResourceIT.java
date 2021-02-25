package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.GastoPrevistoApp;
import com.gustavo.gastoprevisto.domain.TiposFinanceiro;
import com.gustavo.gastoprevisto.repository.TiposFinanceiroRepository;
import com.gustavo.gastoprevisto.service.TiposFinanceiroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TiposFinanceiroResource} REST controller.
 */
@SpringBootTest(classes = GastoPrevistoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TiposFinanceiroResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TiposFinanceiroRepository tiposFinanceiroRepository;

    @Autowired
    private TiposFinanceiroService tiposFinanceiroService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiposFinanceiroMockMvc;

    private TiposFinanceiro tiposFinanceiro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiposFinanceiro createEntity(EntityManager em) {
        TiposFinanceiro tiposFinanceiro = new TiposFinanceiro()
            .nome(DEFAULT_NOME);
        return tiposFinanceiro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiposFinanceiro createUpdatedEntity(EntityManager em) {
        TiposFinanceiro tiposFinanceiro = new TiposFinanceiro()
            .nome(UPDATED_NOME);
        return tiposFinanceiro;
    }

    @BeforeEach
    public void initTest() {
        tiposFinanceiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiposFinanceiro() throws Exception {
        int databaseSizeBeforeCreate = tiposFinanceiroRepository.findAll().size();
        // Create the TiposFinanceiro
        restTiposFinanceiroMockMvc.perform(post("/api/tipos-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposFinanceiro)))
            .andExpect(status().isCreated());

        // Validate the TiposFinanceiro in the database
        List<TiposFinanceiro> tiposFinanceiroList = tiposFinanceiroRepository.findAll();
        assertThat(tiposFinanceiroList).hasSize(databaseSizeBeforeCreate + 1);
        TiposFinanceiro testTiposFinanceiro = tiposFinanceiroList.get(tiposFinanceiroList.size() - 1);
        assertThat(testTiposFinanceiro.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTiposFinanceiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiposFinanceiroRepository.findAll().size();

        // Create the TiposFinanceiro with an existing ID
        tiposFinanceiro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiposFinanceiroMockMvc.perform(post("/api/tipos-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposFinanceiro)))
            .andExpect(status().isBadRequest());

        // Validate the TiposFinanceiro in the database
        List<TiposFinanceiro> tiposFinanceiroList = tiposFinanceiroRepository.findAll();
        assertThat(tiposFinanceiroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTiposFinanceiros() throws Exception {
        // Initialize the database
        tiposFinanceiroRepository.saveAndFlush(tiposFinanceiro);

        // Get all the tiposFinanceiroList
        restTiposFinanceiroMockMvc.perform(get("/api/tipos-financeiros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiposFinanceiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTiposFinanceiro() throws Exception {
        // Initialize the database
        tiposFinanceiroRepository.saveAndFlush(tiposFinanceiro);

        // Get the tiposFinanceiro
        restTiposFinanceiroMockMvc.perform(get("/api/tipos-financeiros/{id}", tiposFinanceiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tiposFinanceiro.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingTiposFinanceiro() throws Exception {
        // Get the tiposFinanceiro
        restTiposFinanceiroMockMvc.perform(get("/api/tipos-financeiros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiposFinanceiro() throws Exception {
        // Initialize the database
        tiposFinanceiroService.save(tiposFinanceiro);

        int databaseSizeBeforeUpdate = tiposFinanceiroRepository.findAll().size();

        // Update the tiposFinanceiro
        TiposFinanceiro updatedTiposFinanceiro = tiposFinanceiroRepository.findById(tiposFinanceiro.getId()).get();
        // Disconnect from session so that the updates on updatedTiposFinanceiro are not directly saved in db
        em.detach(updatedTiposFinanceiro);
        updatedTiposFinanceiro
            .nome(UPDATED_NOME);

        restTiposFinanceiroMockMvc.perform(put("/api/tipos-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTiposFinanceiro)))
            .andExpect(status().isOk());

        // Validate the TiposFinanceiro in the database
        List<TiposFinanceiro> tiposFinanceiroList = tiposFinanceiroRepository.findAll();
        assertThat(tiposFinanceiroList).hasSize(databaseSizeBeforeUpdate);
        TiposFinanceiro testTiposFinanceiro = tiposFinanceiroList.get(tiposFinanceiroList.size() - 1);
        assertThat(testTiposFinanceiro.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTiposFinanceiro() throws Exception {
        int databaseSizeBeforeUpdate = tiposFinanceiroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiposFinanceiroMockMvc.perform(put("/api/tipos-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposFinanceiro)))
            .andExpect(status().isBadRequest());

        // Validate the TiposFinanceiro in the database
        List<TiposFinanceiro> tiposFinanceiroList = tiposFinanceiroRepository.findAll();
        assertThat(tiposFinanceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTiposFinanceiro() throws Exception {
        // Initialize the database
        tiposFinanceiroService.save(tiposFinanceiro);

        int databaseSizeBeforeDelete = tiposFinanceiroRepository.findAll().size();

        // Delete the tiposFinanceiro
        restTiposFinanceiroMockMvc.perform(delete("/api/tipos-financeiros/{id}", tiposFinanceiro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TiposFinanceiro> tiposFinanceiroList = tiposFinanceiroRepository.findAll();
        assertThat(tiposFinanceiroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
