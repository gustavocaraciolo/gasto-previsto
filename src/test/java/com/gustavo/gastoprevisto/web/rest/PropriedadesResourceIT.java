package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.GastoPrevistoApp;
import com.gustavo.gastoprevisto.domain.Propriedades;
import com.gustavo.gastoprevisto.repository.PropriedadesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gustavo.gastoprevisto.domain.enumeration.Moeda;
import com.gustavo.gastoprevisto.domain.enumeration.TipoPagamento;
/**
 * Integration tests for the {@link PropriedadesResource} REST controller.
 */
@SpringBootTest(classes = GastoPrevistoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PropriedadesResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Moeda DEFAULT_MOEDA = Moeda.REAL;
    private static final Moeda UPDATED_MOEDA = Moeda.DOLAR;

    private static final BigDecimal DEFAULT_DT_ASS_CONTRATO = new BigDecimal(1);
    private static final BigDecimal UPDATED_DT_ASS_CONTRATO = new BigDecimal(2);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoPagamento DEFAULT_TIPO_PAGAMENTO = TipoPagamento.ENTRADA;
    private static final TipoPagamento UPDATED_TIPO_PAGAMENTO = TipoPagamento.INTERCALADA;

    @Autowired
    private PropriedadesRepository propriedadesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropriedadesMockMvc;

    private Propriedades propriedades;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedades createEntity(EntityManager em) {
        Propriedades propriedades = new Propriedades()
            .nome(DEFAULT_NOME)
            .moeda(DEFAULT_MOEDA)
            .dtAssContrato(DEFAULT_DT_ASS_CONTRATO)
            .descricao(DEFAULT_DESCRICAO)
            .tipoPagamento(DEFAULT_TIPO_PAGAMENTO);
        return propriedades;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedades createUpdatedEntity(EntityManager em) {
        Propriedades propriedades = new Propriedades()
            .nome(UPDATED_NOME)
            .moeda(UPDATED_MOEDA)
            .dtAssContrato(UPDATED_DT_ASS_CONTRATO)
            .descricao(UPDATED_DESCRICAO)
            .tipoPagamento(UPDATED_TIPO_PAGAMENTO);
        return propriedades;
    }

    @BeforeEach
    public void initTest() {
        propriedades = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropriedades() throws Exception {
        int databaseSizeBeforeCreate = propriedadesRepository.findAll().size();
        // Create the Propriedades
        restPropriedadesMockMvc.perform(post("/api/propriedades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propriedades)))
            .andExpect(status().isCreated());

        // Validate the Propriedades in the database
        List<Propriedades> propriedadesList = propriedadesRepository.findAll();
        assertThat(propriedadesList).hasSize(databaseSizeBeforeCreate + 1);
        Propriedades testPropriedades = propriedadesList.get(propriedadesList.size() - 1);
        assertThat(testPropriedades.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPropriedades.getMoeda()).isEqualTo(DEFAULT_MOEDA);
        assertThat(testPropriedades.getDtAssContrato()).isEqualTo(DEFAULT_DT_ASS_CONTRATO);
        assertThat(testPropriedades.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPropriedades.getTipoPagamento()).isEqualTo(DEFAULT_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void createPropriedadesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propriedadesRepository.findAll().size();

        // Create the Propriedades with an existing ID
        propriedades.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedadesMockMvc.perform(post("/api/propriedades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propriedades)))
            .andExpect(status().isBadRequest());

        // Validate the Propriedades in the database
        List<Propriedades> propriedadesList = propriedadesRepository.findAll();
        assertThat(propriedadesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPropriedades() throws Exception {
        // Initialize the database
        propriedadesRepository.saveAndFlush(propriedades);

        // Get all the propriedadesList
        restPropriedadesMockMvc.perform(get("/api/propriedades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedades.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].moeda").value(hasItem(DEFAULT_MOEDA.toString())))
            .andExpect(jsonPath("$.[*].dtAssContrato").value(hasItem(DEFAULT_DT_ASS_CONTRATO.intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].tipoPagamento").value(hasItem(DEFAULT_TIPO_PAGAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getPropriedades() throws Exception {
        // Initialize the database
        propriedadesRepository.saveAndFlush(propriedades);

        // Get the propriedades
        restPropriedadesMockMvc.perform(get("/api/propriedades/{id}", propriedades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(propriedades.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.moeda").value(DEFAULT_MOEDA.toString()))
            .andExpect(jsonPath("$.dtAssContrato").value(DEFAULT_DT_ASS_CONTRATO.intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.tipoPagamento").value(DEFAULT_TIPO_PAGAMENTO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPropriedades() throws Exception {
        // Get the propriedades
        restPropriedadesMockMvc.perform(get("/api/propriedades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropriedades() throws Exception {
        // Initialize the database
        propriedadesRepository.saveAndFlush(propriedades);

        int databaseSizeBeforeUpdate = propriedadesRepository.findAll().size();

        // Update the propriedades
        Propriedades updatedPropriedades = propriedadesRepository.findById(propriedades.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedades are not directly saved in db
        em.detach(updatedPropriedades);
        updatedPropriedades
            .nome(UPDATED_NOME)
            .moeda(UPDATED_MOEDA)
            .dtAssContrato(UPDATED_DT_ASS_CONTRATO)
            .descricao(UPDATED_DESCRICAO)
            .tipoPagamento(UPDATED_TIPO_PAGAMENTO);

        restPropriedadesMockMvc.perform(put("/api/propriedades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPropriedades)))
            .andExpect(status().isOk());

        // Validate the Propriedades in the database
        List<Propriedades> propriedadesList = propriedadesRepository.findAll();
        assertThat(propriedadesList).hasSize(databaseSizeBeforeUpdate);
        Propriedades testPropriedades = propriedadesList.get(propriedadesList.size() - 1);
        assertThat(testPropriedades.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPropriedades.getMoeda()).isEqualTo(UPDATED_MOEDA);
        assertThat(testPropriedades.getDtAssContrato()).isEqualTo(UPDATED_DT_ASS_CONTRATO);
        assertThat(testPropriedades.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPropriedades.getTipoPagamento()).isEqualTo(UPDATED_TIPO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPropriedades() throws Exception {
        int databaseSizeBeforeUpdate = propriedadesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadesMockMvc.perform(put("/api/propriedades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(propriedades)))
            .andExpect(status().isBadRequest());

        // Validate the Propriedades in the database
        List<Propriedades> propriedadesList = propriedadesRepository.findAll();
        assertThat(propriedadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropriedades() throws Exception {
        // Initialize the database
        propriedadesRepository.saveAndFlush(propriedades);

        int databaseSizeBeforeDelete = propriedadesRepository.findAll().size();

        // Delete the propriedades
        restPropriedadesMockMvc.perform(delete("/api/propriedades/{id}", propriedades.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Propriedades> propriedadesList = propriedadesRepository.findAll();
        assertThat(propriedadesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
