package com.gustavo.gastoprevisto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.gustavo.gastoprevisto.domain.enumeration.Moeda;
import com.gustavo.gastoprevisto.domain.enumeration.CategoriaFinanceiro;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.gustavo.gastoprevisto.domain.Financeiro} entity. This class is used
 * in {@link com.gustavo.gastoprevisto.web.rest.FinanceiroResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /financeiros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FinanceiroCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Moeda
     */
    public static class MoedaFilter extends Filter<Moeda> {

        public MoedaFilter() {
        }

        public MoedaFilter(MoedaFilter filter) {
            super(filter);
        }

        @Override
        public MoedaFilter copy() {
            return new MoedaFilter(this);
        }

    }
    /**
     * Class for filtering CategoriaFinanceiro
     */
    public static class CategoriaFinanceiroFilter extends Filter<CategoriaFinanceiro> {

        public CategoriaFinanceiroFilter() {
        }

        public CategoriaFinanceiroFilter(CategoriaFinanceiroFilter filter) {
            super(filter);
        }

        @Override
        public CategoriaFinanceiroFilter copy() {
            return new CategoriaFinanceiroFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dataPagamento;

    private LocalDateFilter dataVencimento;

    private BigDecimalFilter valorPlanejado;

    private BigDecimalFilter valorPago;

    private IntegerFilter periodicidade;

    private IntegerFilter quantidadeParcelas;

    private MoedaFilter moeda;

    private StringFilter descricao;

    private CategoriaFinanceiroFilter categoriaFinanceiro;

    private LongFilter userId;

    private LongFilter anexoId;

    private LongFilter tipoFinanceiroId;

    public FinanceiroCriteria() {
    }

    public FinanceiroCriteria(FinanceiroCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dataPagamento = other.dataPagamento == null ? null : other.dataPagamento.copy();
        this.dataVencimento = other.dataVencimento == null ? null : other.dataVencimento.copy();
        this.valorPlanejado = other.valorPlanejado == null ? null : other.valorPlanejado.copy();
        this.valorPago = other.valorPago == null ? null : other.valorPago.copy();
        this.periodicidade = other.periodicidade == null ? null : other.periodicidade.copy();
        this.quantidadeParcelas = other.quantidadeParcelas == null ? null : other.quantidadeParcelas.copy();
        this.moeda = other.moeda == null ? null : other.moeda.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.categoriaFinanceiro = other.categoriaFinanceiro == null ? null : other.categoriaFinanceiro.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.anexoId = other.anexoId == null ? null : other.anexoId.copy();
        this.tipoFinanceiroId = other.tipoFinanceiroId == null ? null : other.tipoFinanceiroId.copy();
    }

    @Override
    public FinanceiroCriteria copy() {
        return new FinanceiroCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateFilter dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDateFilter getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateFilter dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimalFilter getValorPlanejado() {
        return valorPlanejado;
    }

    public void setValorPlanejado(BigDecimalFilter valorPlanejado) {
        this.valorPlanejado = valorPlanejado;
    }

    public BigDecimalFilter getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimalFilter valorPago) {
        this.valorPago = valorPago;
    }

    public IntegerFilter getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(IntegerFilter periodicidade) {
        this.periodicidade = periodicidade;
    }

    public IntegerFilter getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(IntegerFilter quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public MoedaFilter getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaFilter moeda) {
        this.moeda = moeda;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public CategoriaFinanceiroFilter getCategoriaFinanceiro() {
        return categoriaFinanceiro;
    }

    public void setCategoriaFinanceiro(CategoriaFinanceiroFilter categoriaFinanceiro) {
        this.categoriaFinanceiro = categoriaFinanceiro;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getAnexoId() {
        return anexoId;
    }

    public void setAnexoId(LongFilter anexoId) {
        this.anexoId = anexoId;
    }

    public LongFilter getTipoFinanceiroId() {
        return tipoFinanceiroId;
    }

    public void setTipoFinanceiroId(LongFilter tipoFinanceiroId) {
        this.tipoFinanceiroId = tipoFinanceiroId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FinanceiroCriteria that = (FinanceiroCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dataPagamento, that.dataPagamento) &&
            Objects.equals(dataVencimento, that.dataVencimento) &&
            Objects.equals(valorPlanejado, that.valorPlanejado) &&
            Objects.equals(valorPago, that.valorPago) &&
            Objects.equals(periodicidade, that.periodicidade) &&
            Objects.equals(quantidadeParcelas, that.quantidadeParcelas) &&
            Objects.equals(moeda, that.moeda) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(categoriaFinanceiro, that.categoriaFinanceiro) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(anexoId, that.anexoId) &&
            Objects.equals(tipoFinanceiroId, that.tipoFinanceiroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dataPagamento,
        dataVencimento,
        valorPlanejado,
        valorPago,
        periodicidade,
        quantidadeParcelas,
        moeda,
        descricao,
        categoriaFinanceiro,
        userId,
        anexoId,
        tipoFinanceiroId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinanceiroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dataPagamento != null ? "dataPagamento=" + dataPagamento + ", " : "") +
                (dataVencimento != null ? "dataVencimento=" + dataVencimento + ", " : "") +
                (valorPlanejado != null ? "valorPlanejado=" + valorPlanejado + ", " : "") +
                (valorPago != null ? "valorPago=" + valorPago + ", " : "") +
                (periodicidade != null ? "periodicidade=" + periodicidade + ", " : "") +
                (quantidadeParcelas != null ? "quantidadeParcelas=" + quantidadeParcelas + ", " : "") +
                (moeda != null ? "moeda=" + moeda + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (categoriaFinanceiro != null ? "categoriaFinanceiro=" + categoriaFinanceiro + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (anexoId != null ? "anexoId=" + anexoId + ", " : "") +
                (tipoFinanceiroId != null ? "tipoFinanceiroId=" + tipoFinanceiroId + ", " : "") +
            "}";
    }

}
