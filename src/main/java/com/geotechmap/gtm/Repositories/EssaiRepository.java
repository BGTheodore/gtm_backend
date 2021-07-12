package com.geotechmap.gtm.Repositories;

import java.util.List;

import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDto;
import com.geotechmap.gtm.Entities.Essai;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EssaiRepository extends JpaRepository<Essai, Long> {
   //add where is_deleted=false
    @Query(value = "SELECT id FROM essais e WHERE e.mots_cles LIKE %:mot_cle%"
    , nativeQuery = true)
    List<Long> rechercheParmotsCles(@Param("mot_cle") String mot_cle);

    @Query(value = "SELECT * FROM essais e GROUP BY e.id_type_essai", nativeQuery = true)
    List<Essai> getAllEssaisRegroupeParCategorie();

    // @Query(value = "SELECT * FROM v_essais_details WHERE is_deleted_essai = FALSE", nativeQuery = true)
    // List<EssaiDetailsDto> findAllFromView();

    @Query(value = "UPDATE essais SET is_deleted = TRUE WHERE id_institution = :id", nativeQuery = true)
    void deleteByIdInstitution(@Param("id") Long id);

    @Query(value = "UPDATE essais SET is_deleted = TRUE WHERE id_type_essai = :id", nativeQuery = true)
    void deleteByIdTypeEssai(@Param("id") Long id);
    


}

