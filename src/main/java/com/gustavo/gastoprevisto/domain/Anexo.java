package com.gustavo.gastoprevisto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Anexo.
 */
@Entity
@Table(name = "anexo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Anexo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "nome")
    private byte[] nome;

    @Column(name = "nome_content_type")
    private String nomeContentType;

    @ManyToMany(mappedBy = "anexos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Propriedades> proriedades = new HashSet<>();

    @ManyToMany(mappedBy = "anexos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Financeiro> financeiros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getNome() {
        return nome;
    }

    public Anexo nome(byte[] nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(byte[] nome) {
        this.nome = nome;
    }

    public String getNomeContentType() {
        return nomeContentType;
    }

    public Anexo nomeContentType(String nomeContentType) {
        this.nomeContentType = nomeContentType;
        return this;
    }

    public void setNomeContentType(String nomeContentType) {
        this.nomeContentType = nomeContentType;
    }

    public Set<Propriedades> getProriedades() {
        return proriedades;
    }

    public Anexo proriedades(Set<Propriedades> propriedades) {
        this.proriedades = propriedades;
        return this;
    }

    public Anexo addProriedade(Propriedades propriedades) {
        this.proriedades.add(propriedades);
        propriedades.getAnexos().add(this);
        return this;
    }

    public Anexo removeProriedade(Propriedades propriedades) {
        this.proriedades.remove(propriedades);
        propriedades.getAnexos().remove(this);
        return this;
    }

    public void setProriedades(Set<Propriedades> propriedades) {
        this.proriedades = propriedades;
    }

    public Set<Financeiro> getFinanceiros() {
        return financeiros;
    }

    public Anexo financeiros(Set<Financeiro> financeiros) {
        this.financeiros = financeiros;
        return this;
    }

    public Anexo addFinanceiro(Financeiro financeiro) {
        this.financeiros.add(financeiro);
        financeiro.getAnexos().add(this);
        return this;
    }

    public Anexo removeFinanceiro(Financeiro financeiro) {
        this.financeiros.remove(financeiro);
        financeiro.getAnexos().remove(this);
        return this;
    }

    public void setFinanceiros(Set<Financeiro> financeiros) {
        this.financeiros = financeiros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anexo)) {
            return false;
        }
        return id != null && id.equals(((Anexo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Anexo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeContentType='" + getNomeContentType() + "'" +
            "}";
    }
}
