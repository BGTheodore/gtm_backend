package com.example.gtm.Services;

import java.util.List;

import com.example.gtm.Entities.testPositiongeographiqueEntity;
import com.example.gtm.Exception.ResourceNotFoundException;

public interface NairobiHealthFacilityService {
    List<testPositiongeographiqueEntity> findAll();
    testPositiongeographiqueEntity findById(int id) throws ResourceNotFoundException;
    List<testPositiongeographiqueEntity> findAllHospitalsWithinSubCounty(int subCountyId);
    List<testPositiongeographiqueEntity> findAllHospitalsByDistanceFromUser(Double userLongitude, Double userLatitude);
    void deleteById(int id);
}