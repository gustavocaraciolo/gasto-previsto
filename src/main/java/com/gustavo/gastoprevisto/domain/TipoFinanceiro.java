package com.gustavo.gastoprevisto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TipoFinanceiro.
 */
@Entity
@Table(name = "tipo_financeiro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoFinanceiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties(value = "tipoFinanceiros", allowSetters = true)
    private Financeiro tipoFinanceiro;

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

    public TipoFinanceiro nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Financeiro getTipoFinanceiro() {
        return tipoFinanceiro;
    }

    public TipoFinanceiro tipoFinanceiro(Financeiro financeiro) {
        this.tipoFinanceiro = financeiro;
        return this;
    }

    public void setTipoFinanceiro(Financeiro financeiro) {
        this.tipoFinanceiro = financeiro;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoFinanceiro)) {
            return false;
        }
        return id != null && id.equals(((TipoFinanceiro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoFinanceiro{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
