package com.geotechmap.gtm.Repositories;

import com.geotechmap.gtm.Entities.Institution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query(value = "SELECT COUNT(*) FROM institutions WHERE is_deleted = false", nativeQuery = true)
    Long countInstitutions();

}