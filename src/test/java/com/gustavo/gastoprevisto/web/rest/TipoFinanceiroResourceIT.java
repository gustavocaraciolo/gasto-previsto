package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.GastoPrevistoApp;
import com.gustavo.gastoprevisto.domain.TipoFinanceiro;
import com.gustavo.gastoprevisto.repository.TipoFinanceiroRepository;
import com.gustavo.gastoprevisto.service.TipoFinanceiroService;

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
 * Integration tests for the {@link TipoFinanceiroResource} REST controller.
 */
@SpringBootTest(classes = GastoPrevistoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoFinanceiroResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoFinanceiroRepository tipoFinanceiroRepository;

    @Autowired
    private TipoFinanceiroService tipoFinanceiroService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoFinanceiroMockMvc;

    private TipoFinanceiro tipoFinanceiro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoFinanceiro createEntity(EntityManager em) {
        TipoFinanceiro tipoFinanceiro = new TipoFinanceiro()
            .nome(DEFAULT_NOME);
        return tipoFinanceiro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoFinanceiro createUpdatedEntity(EntityManager em) {
        TipoFinanceiro tipoFinanceiro = new TipoFinanceiro()
            .nome(UPDATED_NOME);
        return tipoFinanceiro;
    }

    @BeforeEach
    public void initTest() {
        tipoFinanceiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoFinanceiro() throws Exception {
        int databaseSizeBeforeCreate = tipoFinanceiroRepository.findAll().size();
        // Create the TipoFinanceiro
        restTipoFinanceiroMockMvc.perform(post("/api/tipo-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFinanceiro)))
            .andExpect(status().isCreated());

        // Validate the TipoFinanceiro in the database
        List<TipoFinanceiro> tipoFinanceiroList = tipoFinanceiroRepository.findAll();
        assertThat(tipoFinanceiroList).hasSize(databaseSizeBeforeCreate + 1);
        TipoFinanceiro testTipoFinanceiro = tipoFinanceiroList.get(tipoFinanceiroList.size() - 1);
        assertThat(testTipoFinanceiro.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTipoFinanceiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoFinanceiroRepository.findAll().size();

        // Create the TipoFinanceiro with an existing ID
        tipoFinanceiro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoFinanceiroMockMvc.perform(post("/api/tipo-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFinanceiro)))
            .andExpect(status().isBadRequest());

        // Validate the TipoFinanceiro in the database
        List<TipoFinanceiro> tipoFinanceiroList = tipoFinanceiroRepository.findAll();
        assertThat(tipoFinanceiroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoFinanceiros() throws Exception {
        // Initialize the database
        tipoFinanceiroRepository.saveAndFlush(tipoFinanceiro);

        // Get all the tipoFinanceiroList
        restTipoFinanceiroMockMvc.perform(get("/api/tipo-financeiros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoFinanceiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTipoFinanceiro() throws Exception {
        // Initialize the database
        tipoFinanceiroRepository.saveAndFlush(tipoFinanceiro);

        // Get the tipoFinanceiro
        restTipoFinanceiroMockMvc.perform(get("/api/tipo-financeiros/{id}", tipoFinanceiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoFinanceiro.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingTipoFinanceiro() throws Exception {
        // Get the tipoFinanceiro
        restTipoFinanceiroMockMvc.perform(get("/api/tipo-financeiros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoFinanceiro() throws Exception {
        // Initialize the database
        tipoFinanceiroService.save(tipoFinanceiro);

        int databaseSizeBeforeUpdate = tipoFinanceiroRepository.findAll().size();

        // Update the tipoFinanceiro
        TipoFinanceiro updatedTipoFinanceiro = tipoFinanceiroRepository.findById(tipoFinanceiro.getId()).get();
        // Disconnect from session so that the updates on updatedTipoFinanceiro are not directly saved in db
        em.detach(updatedTipoFinanceiro);
        updatedTipoFinanceiro
            .nome(UPDATED_NOME);

        restTipoFinanceiroMockMvc.perform(put("/api/tipo-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoFinanceiro)))
            .andExpect(status().isOk());

        // Validate the TipoFinanceiro in the database
        List<TipoFinanceiro> tipoFinanceiroList = tipoFinanceiroRepository.findAll();
        assertThat(tipoFinanceiroList).hasSize(databaseSizeBeforeUpdate);
        TipoFinanceiro testTipoFinanceiro = tipoFinanceiroList.get(tipoFinanceiroList.size() - 1);
        assertThat(testTipoFinanceiro.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoFinanceiro() throws Exception {
        int databaseSizeBeforeUpdate = tipoFinanceiroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoFinanceiroMockMvc.perform(put("/api/tipo-financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFinanceiro)))
            .andExpect(status().isBadRequest());

        // Validate the TipoFinanceiro in the database
        List<TipoFinanceiro> tipoFinanceiroList = tipoFinanceiroRepository.findAll();
        assertThat(tipoFinanceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoFinanceiro() throws Exception {
        // Initialize the database
        tipoFinanceiroService.save(tipoFinanceiro);

        int databaseSizeBeforeDelete = tipoFinanceiroRepository.findAll().size();

        // Delete the tipoFinanceiro
        restTipoFinanceiroMockMvc.perform(delete("/api/tipo-financeiros/{id}", tipoFinanceiro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoFinanceiro> tipoFinanceiroList = tipoFinanceiroRepository.findAll();
        assertThat(tipoFinanceiroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
