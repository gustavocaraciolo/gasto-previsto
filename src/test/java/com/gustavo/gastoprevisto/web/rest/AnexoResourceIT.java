package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.GastoPrevistoApp;
import com.gustavo.gastoprevisto.domain.Anexo;
import com.gustavo.gastoprevisto.repository.AnexoRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnexoResource} REST controller.
 */
@SpringBootTest(classes = GastoPrevistoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnexoResourceIT {

    private static final byte[] DEFAULT_NOME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NOME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NOME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NOME_CONTENT_TYPE = "image/png";

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoMockMvc;

    private Anexo anexo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anexo createEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .nome(DEFAULT_NOME)
            .nomeContentType(DEFAULT_NOME_CONTENT_TYPE);
        return anexo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anexo createUpdatedEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .nome(UPDATED_NOME)
            .nomeContentType(UPDATED_NOME_CONTENT_TYPE);
        return anexo;
    }

    @BeforeEach
    public void initTest() {
        anexo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexo() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();
        // Create the Anexo
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isCreated());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate + 1);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnexo.getNomeContentType()).isEqualTo(DEFAULT_NOME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAnexoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo with an existing ID
        anexo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnexos() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeContentType").value(hasItem(DEFAULT_NOME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOME))));
    }
    
    @Test
    @Transactional
    public void getAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", anexo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexo.getId().intValue()))
            .andExpect(jsonPath("$.nomeContentType").value(DEFAULT_NOME_CONTENT_TYPE))
            .andExpect(jsonPath("$.nome").value(Base64Utils.encodeToString(DEFAULT_NOME)));
    }
    @Test
    @Transactional
    public void getNonExistingAnexo() throws Exception {
        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Update the anexo
        Anexo updatedAnexo = anexoRepository.findById(anexo.getId()).get();
        // Disconnect from session so that the updates on updatedAnexo are not directly saved in db
        em.detach(updatedAnexo);
        updatedAnexo
            .nome(UPDATED_NOME)
            .nomeContentType(UPDATED_NOME_CONTENT_TYPE);

        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnexo)))
            .andExpect(status().isOk());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnexo.getNomeContentType()).isEqualTo(UPDATED_NOME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexo() throws Exception {
        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anexo)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        int databaseSizeBeforeDelete = anexoRepository.findAll().size();

        // Delete the anexo
        restAnexoMockMvc.perform(delete("/api/anexos/{id}", anexo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
