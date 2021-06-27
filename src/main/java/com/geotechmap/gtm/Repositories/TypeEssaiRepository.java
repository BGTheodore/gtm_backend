package com.geotechmap.gtm.Repositories;

import java.util.List;

import com.geotechmap.gtm.Entities.TypeEssai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface TypeEssaiRepository extends JpaRepository<TypeEssai, Long> {
//    Page<TypeEssai> findByPublished(boolean published, Pageable pageable);

    Page<TypeEssai> findByNomContaining(String nom, Pageable pageable);
    
    // List<TypeEssai> findByTitleContaining(String title, Sort sort);
}