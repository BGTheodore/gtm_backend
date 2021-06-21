package com.example.gtm.Repositories;

import java.util.List;

import com.example.gtm.Entities.testPositiongeographiqueEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NairobiHealthFacilityRepository extends JpaRepository<testPositiongeographiqueEntity, Integer> {
        @Query(value = "SELECT nhf.id, nhf.name, nhf.geom "
                      + "FROM nairobi_Health_facilities nhf, nairobi_sub_counties nsc "
                      + "WHERE ST_Within(nhf.geom, nsc.geom) AND nsc.id= :subCountyId"
                      , nativeQuery = true)
       List<testPositiongeographiqueEntity> findAllHospitalsWithinSubCounty(@Param("subCountyId")  int subCountyId);

        @Query(value = "SELECT nhf.id, nhf.name, nhf.geom, ST_Distance(nhf.geom,ST_SetSRID(ST_Point(:userLongitude,:userLatitude),4326)) AS distance "
                      + "FROM nairobi_Health_facilities nhf "
                      + "ORDER BY nhf.geom  <-> ST_SetSRID(ST_Point(:userLongitude,:userLatitude),4326) "
                      + "LIMIT 5"
                       , nativeQuery = true)
        List<testPositiongeographiqueEntity> findAllHospitalsByDistanceFromUser(@Param("userLongitude") Double userLongitude,@Param("userLatitude")  Double userLatitude);

}