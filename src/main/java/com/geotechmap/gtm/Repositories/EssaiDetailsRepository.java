package com.geotechmap.gtm.Repositories;
import java.util.List;

import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDto;
import com.geotechmap.gtm.Entities.EssaiDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EssaiDetailsRepository extends 
        PagingAndSortingRepository <EssaiDetails, Long>, 
        JpaSpecificationExecutor<EssaiDetails>,
        JpaRepository <EssaiDetails, Long> {
   
    @Query(value = "SELECT * FROM v_essais_details WHERE is_deleted_essai = FALSE", nativeQuery = true)
    List<EssaiDetails> findAllFromView();

    //__ handle pagination 
    @Query(value = "SELECT * FROM v_essais_details WHERE is_deleted_essai = FALSE", 
        nativeQuery = true)
    Page<EssaiDetails> fetchWithPagination( Pageable pageRequest);
}

