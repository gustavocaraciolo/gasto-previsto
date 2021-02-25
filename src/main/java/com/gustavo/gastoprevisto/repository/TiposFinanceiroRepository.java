package com.gustavo.gastoprevisto.repository;

import com.gustavo.gastoprevisto.domain.TiposFinanceiro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TiposFinanceiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiposFinanceiroRepository extends JpaRepository<TiposFinanceiro, Long> {
}
