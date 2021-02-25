package com.gustavo.gastoprevisto.repository;

import com.gustavo.gastoprevisto.domain.Financeiro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Financeiro entity.
 */
@Repository
public interface FinanceiroRepository extends JpaRepository<Financeiro, Long>, JpaSpecificationExecutor<Financeiro> {

    @Query("select financeiro from Financeiro financeiro where financeiro.user.login = ?#{principal.username}")
    List<Financeiro> findByUserIsCurrentUser();

    @Query(value = "select distinct financeiro from Financeiro financeiro left join fetch financeiro.anexos",
        countQuery = "select count(distinct financeiro) from Financeiro financeiro")
    Page<Financeiro> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct financeiro from Financeiro financeiro left join fetch financeiro.anexos")
    List<Financeiro> findAllWithEagerRelationships();

    @Query("select financeiro from Financeiro financeiro left join fetch financeiro.anexos where financeiro.id =:id")
    Optional<Financeiro> findOneWithEagerRelationships(@Param("id") Long id);
}
