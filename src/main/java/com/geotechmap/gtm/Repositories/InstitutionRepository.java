package com.geotechmap.gtm.Repositories;

import com.geotechmap.gtm.Entities.Institution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

}