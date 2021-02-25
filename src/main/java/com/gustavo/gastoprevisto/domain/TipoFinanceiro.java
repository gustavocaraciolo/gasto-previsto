package com.gustavo.gastoprevisto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "tipoFinanceiro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Financeiro> tipoFinanceiros = new HashSet<>();

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

    public Set<Financeiro> getTipoFinanceiros() {
        return tipoFinanceiros;
    }

    public TipoFinanceiro tipoFinanceiros(Set<Financeiro> financeiros) {
        this.tipoFinanceiros = financeiros;
        return this;
    }

    public TipoFinanceiro addTipoFinanceiro(Financeiro financeiro) {
        this.tipoFinanceiros.add(financeiro);
        financeiro.setTipoFinanceiro(this);
        return this;
    }

    public TipoFinanceiro removeTipoFinanceiro(Financeiro financeiro) {
        this.tipoFinanceiros.remove(financeiro);
        financeiro.setTipoFinanceiro(null);
        return this;
    }

    public void setTipoFinanceiros(Set<Financeiro> financeiros) {
        this.tipoFinanceiros = financeiros;
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
