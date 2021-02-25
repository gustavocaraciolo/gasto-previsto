package com.gustavo.gastoprevisto.repository;

import com.gustavo.gastoprevisto.domain.TipoFinanceiro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoFinanceiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoFinanceiroRepository extends JpaRepository<TipoFinanceiro, Long> {
}
