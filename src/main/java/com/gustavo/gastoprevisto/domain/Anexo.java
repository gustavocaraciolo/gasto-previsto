package com.gustavo.gastoprevisto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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
