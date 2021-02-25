package com.gustavo.gastoprevisto.repository;

import com.gustavo.gastoprevisto.domain.Propriedades;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Propriedades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropriedadesRepository extends JpaRepository<Propriedades, Long> {

    @Query("select propriedades from Propriedades propriedades where propriedades.user.login = ?#{principal.username}")
    List<Propriedades> findByUserIsCurrentUser();
}
