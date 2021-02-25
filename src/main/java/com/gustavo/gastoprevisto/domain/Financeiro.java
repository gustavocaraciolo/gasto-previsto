package com.gustavo.gastoprevisto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.gustavo.gastoprevisto.domain.enumeration.Moeda;

import com.gustavo.gastoprevisto.domain.enumeration.TipoFinanceiro;

/**
 * A Financeiro.
 */
@Entity
@Table(name = "financeiro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Financeiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "valor_planejado", precision = 21, scale = 2)
    private BigDecimal valorPlanejado;

    @Column(name = "valor_pago", precision = 21, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "periodicidade")
    private Integer periodicidade;

    @Column(name = "quantidade_parcelas")
    private Integer quantidadeParcelas;

    @Enumerated(EnumType.STRING)
    @Column(name = "moeda")
    private Moeda moeda;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_financeiro")
    private TipoFinanceiro tipoFinanceiro;

    @ManyToOne
    @JsonIgnoreProperties(value = "financeiros", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "financeiros", allowSetters = true)
    private Propriedades propriedades;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "financeiro_anexo",
               joinColumns = @JoinColumn(name = "financeiro_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "anexo_id", referencedColumnName = "id"))
    private Set<Anexo> anexos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public Financeiro dataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public Financeiro dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorPlanejado() {
        return valorPlanejado;
    }

    public Financeiro valorPlanejado(BigDecimal valorPlanejado) {
        this.valorPlanejado = valorPlanejado;
        return this;
    }

    public void setValorPlanejado(BigDecimal valorPlanejado) {
        this.valorPlanejado = valorPlanejado;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public Financeiro valorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
        return this;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Integer getPeriodicidade() {
        return periodicidade;
    }

    public Financeiro periodicidade(Integer periodicidade) {
        this.periodicidade = periodicidade;
        return this;
    }

    public void setPeriodicidade(Integer periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public Financeiro quantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
        return this;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public Financeiro moeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getDescricao() {
        return descricao;
    }

    public Financeiro descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoFinanceiro getTipoFinanceiro() {
        return tipoFinanceiro;
    }

    public Financeiro tipoFinanceiro(TipoFinanceiro tipoFinanceiro) {
        this.tipoFinanceiro = tipoFinanceiro;
        return this;
    }

    public void setTipoFinanceiro(TipoFinanceiro tipoFinanceiro) {
        this.tipoFinanceiro = tipoFinanceiro;
    }

    public User getUser() {
        return user;
    }

    public Financeiro user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Propriedades getPropriedades() {
        return propriedades;
    }

    public Financeiro propriedades(Propriedades propriedades) {
        this.propriedades = propriedades;
        return this;
    }

    public void setPropriedades(Propriedades propriedades) {
        this.propriedades = propriedades;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public Financeiro anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public Financeiro addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.getFinanceiros().add(this);
        return this;
    }

    public Financeiro removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.getFinanceiros().remove(this);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Financeiro)) {
            return false;
        }
        return id != null && id.equals(((Financeiro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Financeiro{" +
            "id=" + getId() +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", valorPlanejado=" + getValorPlanejado() +
            ", valorPago=" + getValorPago() +
            ", periodicidade=" + getPeriodicidade() +
            ", quantidadeParcelas=" + getQuantidadeParcelas() +
            ", moeda='" + getMoeda() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipoFinanceiro='" + getTipoFinanceiro() + "'" +
            "}";
    }
}
