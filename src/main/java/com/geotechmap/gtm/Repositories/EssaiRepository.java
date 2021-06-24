package com.geotechmap.gtm.Repositories;

import java.util.List;

import com.geotechmap.gtm.Entities.Essai;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EssaiRepository extends JpaRepository<Essai, Long> {
   
    @Query(value = "SELECT id FROM essais e WHERE e.mots_cles LIKE %:mot_cle%"
    , nativeQuery = true)
    List<Long> rechercheParmotsCles(@Param("mot_cle") String mot_cle);

    @Query(value = "SELECT * FROM essais e GROUP BY e.id_type_essai", nativeQuery = true)
    List<Essai> getAllEssaisRegroupeParCategorie();
}

