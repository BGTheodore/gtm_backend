package com.geotechmap.gtm.Repositories;
import java.util.List;

import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDto;
import com.geotechmap.gtm.Entities.EssaiDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EssaiDetailsRepository extends JpaRepository<EssaiDetails, Long> {
   
    @Query(value = "SELECT * FROM v_essais_details", nativeQuery = true)
    List<EssaiDetails> findAllFromView();
}

