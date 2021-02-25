package com.gustavo.gastoprevisto.repository;

import com.gustavo.gastoprevisto.domain.Propriedades;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Propriedades entity.
 */
@Repository
public interface PropriedadesRepository extends JpaRepository<Propriedades, Long> {

    @Query("select propriedades from Propriedades propriedades where propriedades.user.login = ?#{principal.username}")
    List<Propriedades> findByUserIsCurrentUser();

    @Query(value = "select distinct propriedades from Propriedades propriedades left join fetch propriedades.anexos",
        countQuery = "select count(distinct propriedades) from Propriedades propriedades")
    Page<Propriedades> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct propriedades from Propriedades propriedades left join fetch propriedades.anexos")
    List<Propriedades> findAllWithEagerRelationships();

    @Query("select propriedades from Propriedades propriedades left join fetch propriedades.anexos where propriedades.id =:id")
    Optional<Propriedades> findOneWithEagerRelationships(@Param("id") Long id);
}
