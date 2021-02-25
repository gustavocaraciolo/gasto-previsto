package com.gustavo.gastoprevisto.web.rest;

import com.gustavo.gastoprevisto.GastoPrevistoApp;
import com.gustavo.gastoprevisto.domain.Financeiro;
import com.gustavo.gastoprevisto.domain.User;
import com.gustavo.gastoprevisto.domain.Anexo;
import com.gustavo.gastoprevisto.domain.TipoFinanceiro;
import com.gustavo.gastoprevisto.repository.FinanceiroRepository;
import com.gustavo.gastoprevisto.service.FinanceiroService;
import com.gustavo.gastoprevisto.service.dto.FinanceiroCriteria;
import com.gustavo.gastoprevisto.service.FinanceiroQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gustavo.gastoprevisto.domain.enumeration.Moeda;
import com.gustavo.gastoprevisto.domain.enumeration.CategoriaFinanceiro;
/**
 * Integration tests for the {@link FinanceiroResource} REST controller.
 */
@SpringBootTest(classes = GastoPrevistoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FinanceiroResourceIT {

    private static final LocalDate DEFAULT_DATA_PAGAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PAGAMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_PAGAMENTO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_VENCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_VALOR_PLANEJADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PLANEJADO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_PLANEJADO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_PAGO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PAGO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_PAGO = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PERIODICIDADE = 1;
    private static final Integer UPDATED_PERIODICIDADE = 2;
    private static final Integer SMALLER_PERIODICIDADE = 1 - 1;

    private static final Integer DEFAULT_QUANTIDADE_PARCELAS = 1;
    private static final Integer UPDATED_QUANTIDADE_PARCELAS = 2;
    private static final Integer SMALLER_QUANTIDADE_PARCELAS = 1 - 1;

    private static final Moeda DEFAULT_MOEDA = Moeda.REAL;
    private static final Moeda UPDATED_MOEDA = Moeda.DOLAR;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final CategoriaFinanceiro DEFAULT_CATEGORIA_FINANCEIRO = CategoriaFinanceiro.DESPESA;
    private static final CategoriaFinanceiro UPDATED_CATEGORIA_FINANCEIRO = CategoriaFinanceiro.RECEITA;

    @Autowired
    private FinanceiroRepository financeiroRepository;

    @Mock
    private FinanceiroRepository financeiroRepositoryMock;

    @Mock
    private FinanceiroService financeiroServiceMock;

    @Autowired
    private FinanceiroService financeiroService;

    @Autowired
    private FinanceiroQueryService financeiroQueryService;

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
            .categoriaFinanceiro(DEFAULT_CATEGORIA_FINANCEIRO);
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
            .categoriaFinanceiro(UPDATED_CATEGORIA_FINANCEIRO);
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
        assertThat(testFinanceiro.getCategoriaFinanceiro()).isEqualTo(DEFAULT_CATEGORIA_FINANCEIRO);
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
            .andExpect(jsonPath("$.[*].categoriaFinanceiro").value(hasItem(DEFAULT_CATEGORIA_FINANCEIRO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFinanceirosWithEagerRelationshipsIsEnabled() throws Exception {
        when(financeiroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFinanceiroMockMvc.perform(get("/api/financeiros?eagerload=true"))
            .andExpect(status().isOk());

        verify(financeiroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFinanceirosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(financeiroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFinanceiroMockMvc.perform(get("/api/financeiros?eagerload=true"))
            .andExpect(status().isOk());

        verify(financeiroServiceMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.categoriaFinanceiro").value(DEFAULT_CATEGORIA_FINANCEIRO.toString()));
    }


    @Test
    @Transactional
    public void getFinanceirosByIdFiltering() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        Long id = financeiro.getId();

        defaultFinanceiroShouldBeFound("id.equals=" + id);
        defaultFinanceiroShouldNotBeFound("id.notEquals=" + id);

        defaultFinanceiroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFinanceiroShouldNotBeFound("id.greaterThan=" + id);

        defaultFinanceiroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFinanceiroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento equals to DEFAULT_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.equals=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento equals to UPDATED_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.equals=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento not equals to DEFAULT_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.notEquals=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento not equals to UPDATED_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.notEquals=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento in DEFAULT_DATA_PAGAMENTO or UPDATED_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.in=" + DEFAULT_DATA_PAGAMENTO + "," + UPDATED_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento equals to UPDATED_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.in=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento is not null
        defaultFinanceiroShouldBeFound("dataPagamento.specified=true");

        // Get all the financeiroList where dataPagamento is null
        defaultFinanceiroShouldNotBeFound("dataPagamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento is greater than or equal to DEFAULT_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.greaterThanOrEqual=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento is greater than or equal to UPDATED_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.greaterThanOrEqual=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento is less than or equal to DEFAULT_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.lessThanOrEqual=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento is less than or equal to SMALLER_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.lessThanOrEqual=" + SMALLER_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento is less than DEFAULT_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.lessThan=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento is less than UPDATED_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.lessThan=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataPagamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataPagamento is greater than DEFAULT_DATA_PAGAMENTO
        defaultFinanceiroShouldNotBeFound("dataPagamento.greaterThan=" + DEFAULT_DATA_PAGAMENTO);

        // Get all the financeiroList where dataPagamento is greater than SMALLER_DATA_PAGAMENTO
        defaultFinanceiroShouldBeFound("dataPagamento.greaterThan=" + SMALLER_DATA_PAGAMENTO);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento equals to DEFAULT_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.equals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.equals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento not equals to DEFAULT_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.notEquals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento not equals to UPDATED_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.notEquals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento in DEFAULT_DATA_VENCIMENTO or UPDATED_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.in=" + DEFAULT_DATA_VENCIMENTO + "," + UPDATED_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.in=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento is not null
        defaultFinanceiroShouldBeFound("dataVencimento.specified=true");

        // Get all the financeiroList where dataVencimento is null
        defaultFinanceiroShouldNotBeFound("dataVencimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento is greater than or equal to DEFAULT_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.greaterThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento is greater than or equal to UPDATED_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.greaterThanOrEqual=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento is less than or equal to DEFAULT_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.lessThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento is less than or equal to SMALLER_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.lessThanOrEqual=" + SMALLER_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento is less than DEFAULT_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.lessThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento is less than UPDATED_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.lessThan=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDataVencimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where dataVencimento is greater than DEFAULT_DATA_VENCIMENTO
        defaultFinanceiroShouldNotBeFound("dataVencimento.greaterThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the financeiroList where dataVencimento is greater than SMALLER_DATA_VENCIMENTO
        defaultFinanceiroShouldBeFound("dataVencimento.greaterThan=" + SMALLER_DATA_VENCIMENTO);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado equals to DEFAULT_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.equals=" + DEFAULT_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado equals to UPDATED_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.equals=" + UPDATED_VALOR_PLANEJADO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado not equals to DEFAULT_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.notEquals=" + DEFAULT_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado not equals to UPDATED_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.notEquals=" + UPDATED_VALOR_PLANEJADO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado in DEFAULT_VALOR_PLANEJADO or UPDATED_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.in=" + DEFAULT_VALOR_PLANEJADO + "," + UPDATED_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado equals to UPDATED_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.in=" + UPDATED_VALOR_PLANEJADO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado is not null
        defaultFinanceiroShouldBeFound("valorPlanejado.specified=true");

        // Get all the financeiroList where valorPlanejado is null
        defaultFinanceiroShouldNotBeFound("valorPlanejado.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado is greater than or equal to DEFAULT_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.greaterThanOrEqual=" + DEFAULT_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado is greater than or equal to UPDATED_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.greaterThanOrEqual=" + UPDATED_VALOR_PLANEJADO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado is less than or equal to DEFAULT_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.lessThanOrEqual=" + DEFAULT_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado is less than or equal to SMALLER_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.lessThanOrEqual=" + SMALLER_VALOR_PLANEJADO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsLessThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado is less than DEFAULT_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.lessThan=" + DEFAULT_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado is less than UPDATED_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.lessThan=" + UPDATED_VALOR_PLANEJADO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPlanejadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPlanejado is greater than DEFAULT_VALOR_PLANEJADO
        defaultFinanceiroShouldNotBeFound("valorPlanejado.greaterThan=" + DEFAULT_VALOR_PLANEJADO);

        // Get all the financeiroList where valorPlanejado is greater than SMALLER_VALOR_PLANEJADO
        defaultFinanceiroShouldBeFound("valorPlanejado.greaterThan=" + SMALLER_VALOR_PLANEJADO);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago equals to DEFAULT_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.equals=" + DEFAULT_VALOR_PAGO);

        // Get all the financeiroList where valorPago equals to UPDATED_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.equals=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago not equals to DEFAULT_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.notEquals=" + DEFAULT_VALOR_PAGO);

        // Get all the financeiroList where valorPago not equals to UPDATED_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.notEquals=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago in DEFAULT_VALOR_PAGO or UPDATED_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.in=" + DEFAULT_VALOR_PAGO + "," + UPDATED_VALOR_PAGO);

        // Get all the financeiroList where valorPago equals to UPDATED_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.in=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago is not null
        defaultFinanceiroShouldBeFound("valorPago.specified=true");

        // Get all the financeiroList where valorPago is null
        defaultFinanceiroShouldNotBeFound("valorPago.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago is greater than or equal to DEFAULT_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.greaterThanOrEqual=" + DEFAULT_VALOR_PAGO);

        // Get all the financeiroList where valorPago is greater than or equal to UPDATED_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.greaterThanOrEqual=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago is less than or equal to DEFAULT_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.lessThanOrEqual=" + DEFAULT_VALOR_PAGO);

        // Get all the financeiroList where valorPago is less than or equal to SMALLER_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.lessThanOrEqual=" + SMALLER_VALOR_PAGO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsLessThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago is less than DEFAULT_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.lessThan=" + DEFAULT_VALOR_PAGO);

        // Get all the financeiroList where valorPago is less than UPDATED_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.lessThan=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByValorPagoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where valorPago is greater than DEFAULT_VALOR_PAGO
        defaultFinanceiroShouldNotBeFound("valorPago.greaterThan=" + DEFAULT_VALOR_PAGO);

        // Get all the financeiroList where valorPago is greater than SMALLER_VALOR_PAGO
        defaultFinanceiroShouldBeFound("valorPago.greaterThan=" + SMALLER_VALOR_PAGO);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade equals to DEFAULT_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.equals=" + DEFAULT_PERIODICIDADE);

        // Get all the financeiroList where periodicidade equals to UPDATED_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.equals=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade not equals to DEFAULT_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.notEquals=" + DEFAULT_PERIODICIDADE);

        // Get all the financeiroList where periodicidade not equals to UPDATED_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.notEquals=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade in DEFAULT_PERIODICIDADE or UPDATED_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.in=" + DEFAULT_PERIODICIDADE + "," + UPDATED_PERIODICIDADE);

        // Get all the financeiroList where periodicidade equals to UPDATED_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.in=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade is not null
        defaultFinanceiroShouldBeFound("periodicidade.specified=true");

        // Get all the financeiroList where periodicidade is null
        defaultFinanceiroShouldNotBeFound("periodicidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade is greater than or equal to DEFAULT_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.greaterThanOrEqual=" + DEFAULT_PERIODICIDADE);

        // Get all the financeiroList where periodicidade is greater than or equal to UPDATED_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.greaterThanOrEqual=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade is less than or equal to DEFAULT_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.lessThanOrEqual=" + DEFAULT_PERIODICIDADE);

        // Get all the financeiroList where periodicidade is less than or equal to SMALLER_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.lessThanOrEqual=" + SMALLER_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade is less than DEFAULT_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.lessThan=" + DEFAULT_PERIODICIDADE);

        // Get all the financeiroList where periodicidade is less than UPDATED_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.lessThan=" + UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByPeriodicidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where periodicidade is greater than DEFAULT_PERIODICIDADE
        defaultFinanceiroShouldNotBeFound("periodicidade.greaterThan=" + DEFAULT_PERIODICIDADE);

        // Get all the financeiroList where periodicidade is greater than SMALLER_PERIODICIDADE
        defaultFinanceiroShouldBeFound("periodicidade.greaterThan=" + SMALLER_PERIODICIDADE);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas equals to DEFAULT_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.equals=" + DEFAULT_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas equals to UPDATED_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.equals=" + UPDATED_QUANTIDADE_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas not equals to DEFAULT_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.notEquals=" + DEFAULT_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas not equals to UPDATED_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.notEquals=" + UPDATED_QUANTIDADE_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas in DEFAULT_QUANTIDADE_PARCELAS or UPDATED_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.in=" + DEFAULT_QUANTIDADE_PARCELAS + "," + UPDATED_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas equals to UPDATED_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.in=" + UPDATED_QUANTIDADE_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas is not null
        defaultFinanceiroShouldBeFound("quantidadeParcelas.specified=true");

        // Get all the financeiroList where quantidadeParcelas is null
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas is greater than or equal to DEFAULT_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas is greater than or equal to UPDATED_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.greaterThanOrEqual=" + UPDATED_QUANTIDADE_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas is less than or equal to DEFAULT_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.lessThanOrEqual=" + DEFAULT_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas is less than or equal to SMALLER_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.lessThanOrEqual=" + SMALLER_QUANTIDADE_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsLessThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas is less than DEFAULT_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.lessThan=" + DEFAULT_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas is less than UPDATED_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.lessThan=" + UPDATED_QUANTIDADE_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByQuantidadeParcelasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where quantidadeParcelas is greater than DEFAULT_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldNotBeFound("quantidadeParcelas.greaterThan=" + DEFAULT_QUANTIDADE_PARCELAS);

        // Get all the financeiroList where quantidadeParcelas is greater than SMALLER_QUANTIDADE_PARCELAS
        defaultFinanceiroShouldBeFound("quantidadeParcelas.greaterThan=" + SMALLER_QUANTIDADE_PARCELAS);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByMoedaIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where moeda equals to DEFAULT_MOEDA
        defaultFinanceiroShouldBeFound("moeda.equals=" + DEFAULT_MOEDA);

        // Get all the financeiroList where moeda equals to UPDATED_MOEDA
        defaultFinanceiroShouldNotBeFound("moeda.equals=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByMoedaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where moeda not equals to DEFAULT_MOEDA
        defaultFinanceiroShouldNotBeFound("moeda.notEquals=" + DEFAULT_MOEDA);

        // Get all the financeiroList where moeda not equals to UPDATED_MOEDA
        defaultFinanceiroShouldBeFound("moeda.notEquals=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByMoedaIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where moeda in DEFAULT_MOEDA or UPDATED_MOEDA
        defaultFinanceiroShouldBeFound("moeda.in=" + DEFAULT_MOEDA + "," + UPDATED_MOEDA);

        // Get all the financeiroList where moeda equals to UPDATED_MOEDA
        defaultFinanceiroShouldNotBeFound("moeda.in=" + UPDATED_MOEDA);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByMoedaIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where moeda is not null
        defaultFinanceiroShouldBeFound("moeda.specified=true");

        // Get all the financeiroList where moeda is null
        defaultFinanceiroShouldNotBeFound("moeda.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where descricao equals to DEFAULT_DESCRICAO
        defaultFinanceiroShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the financeiroList where descricao equals to UPDATED_DESCRICAO
        defaultFinanceiroShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where descricao not equals to DEFAULT_DESCRICAO
        defaultFinanceiroShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the financeiroList where descricao not equals to UPDATED_DESCRICAO
        defaultFinanceiroShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultFinanceiroShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the financeiroList where descricao equals to UPDATED_DESCRICAO
        defaultFinanceiroShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where descricao is not null
        defaultFinanceiroShouldBeFound("descricao.specified=true");

        // Get all the financeiroList where descricao is null
        defaultFinanceiroShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllFinanceirosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where descricao contains DEFAULT_DESCRICAO
        defaultFinanceiroShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the financeiroList where descricao contains UPDATED_DESCRICAO
        defaultFinanceiroShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where descricao does not contain DEFAULT_DESCRICAO
        defaultFinanceiroShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the financeiroList where descricao does not contain UPDATED_DESCRICAO
        defaultFinanceiroShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllFinanceirosByCategoriaFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where categoriaFinanceiro equals to DEFAULT_CATEGORIA_FINANCEIRO
        defaultFinanceiroShouldBeFound("categoriaFinanceiro.equals=" + DEFAULT_CATEGORIA_FINANCEIRO);

        // Get all the financeiroList where categoriaFinanceiro equals to UPDATED_CATEGORIA_FINANCEIRO
        defaultFinanceiroShouldNotBeFound("categoriaFinanceiro.equals=" + UPDATED_CATEGORIA_FINANCEIRO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByCategoriaFinanceiroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where categoriaFinanceiro not equals to DEFAULT_CATEGORIA_FINANCEIRO
        defaultFinanceiroShouldNotBeFound("categoriaFinanceiro.notEquals=" + DEFAULT_CATEGORIA_FINANCEIRO);

        // Get all the financeiroList where categoriaFinanceiro not equals to UPDATED_CATEGORIA_FINANCEIRO
        defaultFinanceiroShouldBeFound("categoriaFinanceiro.notEquals=" + UPDATED_CATEGORIA_FINANCEIRO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByCategoriaFinanceiroIsInShouldWork() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where categoriaFinanceiro in DEFAULT_CATEGORIA_FINANCEIRO or UPDATED_CATEGORIA_FINANCEIRO
        defaultFinanceiroShouldBeFound("categoriaFinanceiro.in=" + DEFAULT_CATEGORIA_FINANCEIRO + "," + UPDATED_CATEGORIA_FINANCEIRO);

        // Get all the financeiroList where categoriaFinanceiro equals to UPDATED_CATEGORIA_FINANCEIRO
        defaultFinanceiroShouldNotBeFound("categoriaFinanceiro.in=" + UPDATED_CATEGORIA_FINANCEIRO);
    }

    @Test
    @Transactional
    public void getAllFinanceirosByCategoriaFinanceiroIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);

        // Get all the financeiroList where categoriaFinanceiro is not null
        defaultFinanceiroShouldBeFound("categoriaFinanceiro.specified=true");

        // Get all the financeiroList where categoriaFinanceiro is null
        defaultFinanceiroShouldNotBeFound("categoriaFinanceiro.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceirosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        financeiro.setUser(user);
        financeiroRepository.saveAndFlush(financeiro);
        Long userId = user.getId();

        // Get all the financeiroList where user equals to userId
        defaultFinanceiroShouldBeFound("userId.equals=" + userId);

        // Get all the financeiroList where user equals to userId + 1
        defaultFinanceiroShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllFinanceirosByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);
        Anexo anexo = AnexoResourceIT.createEntity(em);
        em.persist(anexo);
        em.flush();
        financeiro.addAnexo(anexo);
        financeiroRepository.saveAndFlush(financeiro);
        Long anexoId = anexo.getId();

        // Get all the financeiroList where anexo equals to anexoId
        defaultFinanceiroShouldBeFound("anexoId.equals=" + anexoId);

        // Get all the financeiroList where anexo equals to anexoId + 1
        defaultFinanceiroShouldNotBeFound("anexoId.equals=" + (anexoId + 1));
    }


    @Test
    @Transactional
    public void getAllFinanceirosByTipoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        financeiroRepository.saveAndFlush(financeiro);
        TipoFinanceiro tipoFinanceiro = TipoFinanceiroResourceIT.createEntity(em);
        em.persist(tipoFinanceiro);
        em.flush();
        financeiro.setTipoFinanceiro(tipoFinanceiro);
        financeiroRepository.saveAndFlush(financeiro);
        Long tipoFinanceiroId = tipoFinanceiro.getId();

        // Get all the financeiroList where tipoFinanceiro equals to tipoFinanceiroId
        defaultFinanceiroShouldBeFound("tipoFinanceiroId.equals=" + tipoFinanceiroId);

        // Get all the financeiroList where tipoFinanceiro equals to tipoFinanceiroId + 1
        defaultFinanceiroShouldNotBeFound("tipoFinanceiroId.equals=" + (tipoFinanceiroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFinanceiroShouldBeFound(String filter) throws Exception {
        restFinanceiroMockMvc.perform(get("/api/financeiros?sort=id,desc&" + filter))
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
            .andExpect(jsonPath("$.[*].categoriaFinanceiro").value(hasItem(DEFAULT_CATEGORIA_FINANCEIRO.toString())));

        // Check, that the count call also returns 1
        restFinanceiroMockMvc.perform(get("/api/financeiros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFinanceiroShouldNotBeFound(String filter) throws Exception {
        restFinanceiroMockMvc.perform(get("/api/financeiros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFinanceiroMockMvc.perform(get("/api/financeiros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        financeiroService.save(financeiro);

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
            .categoriaFinanceiro(UPDATED_CATEGORIA_FINANCEIRO);

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
        assertThat(testFinanceiro.getCategoriaFinanceiro()).isEqualTo(UPDATED_CATEGORIA_FINANCEIRO);
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
        financeiroService.save(financeiro);

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
