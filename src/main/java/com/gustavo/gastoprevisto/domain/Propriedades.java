package com.gustavo.gastoprevisto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;

import com.gustavo.gastoprevisto.domain.enumeration.Moeda;

import com.gustavo.gastoprevisto.domain.enumeration.TipoPagamento;

/**
 * A Propriedades.
 */
@Entity
@Table(name = "propriedades")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Propriedades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "moeda")
    private Moeda moeda;

    @Column(name = "dt_ass_contrato")
    private String dtAssContrato;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @ManyToOne
    @JsonIgnoreProperties(value = "propriedades", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Propriedades nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public Propriedades moeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getDtAssContrato() {
        return dtAssContrato;
    }

    public Propriedades dtAssContrato(String dtAssContrato) {
        this.dtAssContrato = dtAssContrato;
        return this;
    }

    public void setDtAssContrato(String dtAssContrato) {
        this.dtAssContrato = dtAssContrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public Propriedades descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public Propriedades tipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        return this;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public User getUser() {
        return user;
    }

    public Propriedades user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Propriedades)) {
            return false;
        }
        return id != null && id.equals(((Propriedades) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Propriedades{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", moeda='" + getMoeda() + "'" +
            ", dtAssContrato='" + getDtAssContrato() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipoPagamento='" + getTipoPagamento() + "'" +
            "}";
    }
}
