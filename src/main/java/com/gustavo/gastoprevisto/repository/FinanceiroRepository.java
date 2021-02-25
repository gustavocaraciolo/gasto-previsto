package com.gustavo.gastoprevisto.repository;

import com.gustavo.gastoprevisto.domain.Financeiro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Financeiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceiroRepository extends JpaRepository<Financeiro, Long> {

    @Query("select financeiro from Financeiro financeiro where financeiro.user.login = ?#{principal.username}")
    List<Financeiro> findByUserIsCurrentUser();
}
