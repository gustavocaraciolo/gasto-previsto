package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.GastoPrevistoApp;
import com.gustavo.gastoprevisto.domain.Financeiro;
import com.gustavo.gastoprevisto.repository.FinanceiroRepository;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gustavo.gastoprevisto.domain.enumeration.Moeda;
import com.gustavo.gastoprevisto.domain.enumeration.TipoFinanceiro;
/**
 * Integration tests for the {@link FinanceiroResource} REST controller.
 */
@SpringBootTest(classes = GastoPrevistoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FinanceiroResourceIT {

    private static final LocalDate DEFAULT_DATA_PAGAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PAGAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR_PLANEJADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PLANEJADO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_PAGO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PAGO = new BigDecimal(2);

    private static final Integer DEFAULT_PERIODICIDADE = 1;
    private static final Integer UPDATED_PERIODICIDADE = 2;

    private static final Integer DEFAULT_QUANTIDADE_PARCELAS = 1;
    private static final Integer UPDATED_QUANTIDADE_PARCELAS = 2;

    private static final Moeda DEFAULT_MOEDA = Moeda.REAL;
    private static final Moeda UPDATED_MOEDA = Moeda.DOLAR;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoFinanceiro DEFAULT_TIPO_FINANCEIRO = TipoFinanceiro.DESPESA;
    private static final TipoFinanceiro UPDATED_TIPO_FINANCEIRO = TipoFinanceiro.RECEITA;

    @Autowired
    private FinanceiroRepository financeiroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinanceiroMockMvc;

    private Financeiro financeiro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Financeiro createEntity(EntityManager em) {
        Financeiro financeiro = new Financeiro()
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .valorPlanejado(DEFAULT_VALOR_PLANEJADO)
            .valorPago(DEFAULT_VALOR_PAGO)
            .periodicidade(DEFAULT_PERIODICIDADE)
            .quantidadeParcelas(DEFAULT_QUANTIDADE_PARCELAS)
            .moeda(DEFAULT_MOEDA)
            .descricao(DEFAULT_DESCRICAO)
            .tipoFinanceiro(DEFAULT_TIPO_FINANCEIRO);
        return financeiro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Financeiro createUpdatedEntity(EntityManager em) {
        Financeiro financeiro = new Financeiro()
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .valorPlanejado(UPDATED_VALOR_PLANEJADO)
            .valorPago(UPDATED_VALOR_PAGO)
            .periodicidade(UPDATED_PERIODICIDADE)
            .quantidadeParcelas(UPDATED_QUANTIDADE_PARCELAS)
            .moeda(UPDATED_MOEDA)
            .descricao(UPDATED_DESCRICAO)
            .tipoFinanceiro(UPDATED_TIPO_FINANCEIRO);
        return financeiro;
    }

    @BeforeEach
    public void initTest() {
        financeiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinanceiro() throws Exception {
        int databaseSizeBeforeCreate = financeiroRepository.findAll().size();
        // Create the Financeiro
        restFinanceiroMockMvc.perform(post("/api/financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(financeiro)))
            .andExpect(status().isCreated());

        // Validate the Financeiro in the database
        List<Financeiro> financeiroList = financeiroRepository.findAll();
        assertThat(financeiroList).hasSize(databaseSizeBeforeCreate + 1);
        Financeiro testFinanceiro = financeiroList.get(financeiroList.size() - 1);
        assertThat(testFinanceiro.getDataPagamento()).isEqualTo(DEFAULT_DATA_PAGAMENTO);
        assertThat(testFinanceiro.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testFinanceiro.getValorPlanejado()).isEqualTo(DEFAULT_VALOR_PLANEJADO);
        assertThat(testFinanceiro.getValorPago()).isEqualTo(DEFAULT_VALOR_PAGO);
        assertThat(testFinanceiro.getPeriodicidade()).isEqualTo(DEFAULT_PERIODICIDADE);
        assertThat(testFinanceiro.getQuantidadeParcelas()).isEqualTo(DEFAULT_QUANTIDADE_PARCELAS);
        assertThat(testFinanceiro.getMoeda()).isEqualTo(DEFAULT_MOEDA);
        assertThat(testFinanceiro.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFinanceiro.getTipoFinanceiro()).isEqualTo(DEFAULT_TIPO_FINANCEIRO);
    }

    @Test
    @Transactional
    public void createFinanceiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financeiroRepository.findAll().size();

        // Create the Financeiro with an existing ID
        financeiro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceiroMockMvc.perform(post("/api/financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(financeiro)))
            .andExpect(status().isBadRequest());

        // Validate the Financeiro in the database
        List<Financeiro> financeiroList = financeiroRepository.findAll();
        assertThat(financeiroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinanceiros() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList
        restFinanceiroMockMvc.perform(get("/api/financeiros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorPlanejado").value(hasItem(DEFAULT_VALOR_PLANEJADO.intValue())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.intValue())))
            .andExpect(jsonPath("$.[*].periodicidade").value(hasItem(DEFAULT_PERIODICIDADE)))
            .andExpect(jsonPath("$.[*].quantidadeParcelas").value(hasItem(DEFAULT_QUANTIDADE_PARCELAS)))
            .andExpect(jsonPath("$.[*].moeda").value(hasItem(DEFAULT_MOEDA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipoFinanceiro").value(hasItem(DEFAULT_TIPO_FINANCEIRO.toString())));
    }
    
    @Test
    @Transactional
    public void getFinanceiro() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get the financeiro
        restFinanceiroMockMvc.perform(get("/api/financeiros/{id}", financeiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(financeiro.getId().intValue()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.valorPlanejado").value(DEFAULT_VALOR_PLANEJADO.intValue()))
            .andExpect(jsonPath("$.valorPago").value(DEFAULT_VALOR_PAGO.intValue()))
            .andExpect(jsonPath("$.periodicidade").value(DEFAULT_PERIODICIDADE))
            .andExpect(jsonPath("$.quantidadeParcelas").value(DEFAULT_QUANTIDADE_PARCELAS))
            .andExpect(jsonPath("$.moeda").value(DEFAULT_MOEDA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tipoFinanceiro").value(DEFAULT_TIPO_FINANCEIRO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFinanceiro() throws Exception {
        // Get the financeiro
        restFinanceiroMockMvc.perform(get("/api/financeiros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinanceiro() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        int databaseSizeBeforeUpdate = financeiroRepository.findAll().size();

        // Update the financeiro
        Financeiro updatedFinanceiro = financeiroRepository.findById(financeiro.getId()).get();
        // Disconnect from session so that the updates on updatedFinanceiro are not directly saved in db
        em.detach(updatedFinanceiro);
        updatedFinanceiro
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .valorPlanejado(UPDATED_VALOR_PLANEJADO)
            .valorPago(UPDATED_VALOR_PAGO)
            .periodicidade(UPDATED_PERIODICIDADE)
            .quantidadeParcelas(UPDATED_QUANTIDADE_PARCELAS)
            .moeda(UPDATED_MOEDA)
            .descricao(UPDATED_DESCRICAO)
            .tipoFinanceiro(UPDATED_TIPO_FINANCEIRO);

        restFinanceiroMockMvc.perform(put("/api/financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinanceiro)))
            .andExpect(status().isOk());

        // Validate the Financeiro in the database
        List<Financeiro> financeiroList = financeiroRepository.findAll();
        assertThat(financeiroList).hasSize(databaseSizeBeforeUpdate);
        Financeiro testFinanceiro = financeiroList.get(financeiroList.size() - 1);
        assertThat(testFinanceiro.getDataPagamento()).isEqualTo(UPDATED_DATA_PAGAMENTO);
        assertThat(testFinanceiro.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFinanceiro.getValorPlanejado()).isEqualTo(UPDATED_VALOR_PLANEJADO);
        assertThat(testFinanceiro.getValorPago()).isEqualTo(UPDATED_VALOR_PAGO);
        assertThat(testFinanceiro.getPeriodicidade()).isEqualTo(UPDATED_PERIODICIDADE);
        assertThat(testFinanceiro.getQuantidadeParcelas()).isEqualTo(UPDATED_QUANTIDADE_PARCELAS);
        assertThat(testFinanceiro.getMoeda()).isEqualTo(UPDATED_MOEDA);
        assertThat(testFinanceiro.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFinanceiro.getTipoFinanceiro()).isEqualTo(UPDATED_TIPO_FINANCEIRO);
    }

    @Test
    @Transactional
    public void updateNonExistingFinanceiro() throws Exception {
        int databaseSizeBeforeUpdate = financeiroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceiroMockMvc.perform(put("/api/financeiros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(financeiro)))
            .andExpect(status().isBadRequest());

        // Validate the Financeiro in the database
        List<Financeiro> financeiroList = financeiroRepository.findAll();
        assertThat(financeiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinanceiro() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        int databaseSizeBeforeDelete = financeiroRepository.findAll().size();

        // Delete the financeiro
        restFinanceiroMockMvc.perform(delete("/api/financeiros/{id}", financeiro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Financeiro> financeiroList = financeiroRepository.findAll();
        assertThat(financeiroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
