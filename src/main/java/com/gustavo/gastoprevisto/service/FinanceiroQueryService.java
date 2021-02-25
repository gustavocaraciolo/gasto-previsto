package com.gustavo.gastoprevisto.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.gustavo.gastoprevisto.domain.Financeiro;
import com.gustavo.gastoprevisto.domain.*; // for static metamodels
import com.gustavo.gastoprevisto.repository.FinanceiroRepository;
import com.gustavo.gastoprevisto.service.dto.FinanceiroCriteria;

/**
 * Service for executing complex queries for {@link Financeiro} entities in the database.
 * The main input is a {@link FinanceiroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Financeiro} or a {@link Page} of {@link Financeiro} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FinanceiroQueryService extends QueryService<Financeiro> {

    private final Logger log = LoggerFactory.getLogger(FinanceiroQueryService.class);

    private final FinanceiroRepository financeiroRepository;

    public FinanceiroQueryService(FinanceiroRepository financeiroRepository) {
        this.financeiroRepository = financeiroRepository;
    }

    /**
     * Return a {@link List} of {@link Financeiro} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Financeiro> findByCriteria(FinanceiroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Financeiro> specification = createSpecification(criteria);
        return financeiroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Financeiro} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Financeiro> findByCriteria(FinanceiroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Financeiro> specification = createSpecification(criteria);
        return financeiroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FinanceiroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Financeiro> specification = createSpecification(criteria);
        return financeiroRepository.count(specification);
    }

    /**
     * Function to convert {@link FinanceiroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Financeiro> createSpecification(FinanceiroCriteria criteria) {
        Specification<Financeiro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Financeiro_.id));
            }
            if (criteria.getDataPagamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPagamento(), Financeiro_.dataPagamento));
            }
            if (criteria.getDataVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimento(), Financeiro_.dataVencimento));
            }
            if (criteria.getValorPlanejado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorPlanejado(), Financeiro_.valorPlanejado));
            }
            if (criteria.getValorPago() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorPago(), Financeiro_.valorPago));
            }
            if (criteria.getPeriodicidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodicidade(), Financeiro_.periodicidade));
            }
            if (criteria.getQuantidadeParcelas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidadeParcelas(), Financeiro_.quantidadeParcelas));
            }
            if (criteria.getMoeda() != null) {
                specification = specification.and(buildSpecification(criteria.getMoeda(), Financeiro_.moeda));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Financeiro_.descricao));
            }
            if (criteria.getTipoFinanceiro() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoFinanceiro(), Financeiro_.tipoFinanceiro));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Financeiro_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPropriedadesId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropriedadesId(),
                    root -> root.join(Financeiro_.propriedades, JoinType.LEFT).get(Propriedades_.id)));
            }
            if (criteria.getAnexoId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnexoId(),
                    root -> root.join(Financeiro_.anexos, JoinType.LEFT).get(Anexo_.id)));
            }
        }
        return specification;
    }
}
