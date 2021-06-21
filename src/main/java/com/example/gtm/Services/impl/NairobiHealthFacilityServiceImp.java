package com.example.gtm.Services.impl;

import java.util.List;

import com.example.gtm.Entities.testPositiongeographiqueEntity;
import com.example.gtm.Repositories.NairobiHealthFacilityRepository;
import com.example.gtm.Services.NairobiHealthFacilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gtm.Exception.ResourceNotFoundException;

@Service
public class NairobiHealthFacilityServiceImp implements NairobiHealthFacilityService {

    @Autowired
    private NairobiHealthFacilityRepository nairobiHealthFacilityRepository;

    public List<testPositiongeographiqueEntity> findAll() {
        return  nairobiHealthFacilityRepository.findAll();
    }

    public testPositiongeographiqueEntity findById(int id) throws ResourceNotFoundException {
        return nairobiHealthFacilityRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Health Facility with ID: " + id + " not found.")
        );
    }

    @Override
    public List<testPositiongeographiqueEntity> findAllHospitalsWithinSubCounty(int subCountyId) {
        return nairobiHealthFacilityRepository.findAllHospitalsWithinSubCounty(subCountyId);
    }

    @Override
    public List<testPositiongeographiqueEntity> findAllHospitalsByDistanceFromUser(Double userLongitude, Double userLatitude) {
        return nairobiHealthFacilityRepository.findAllHospitalsByDistanceFromUser(userLongitude,userLatitude);
    }
    public void deleteById(int id) {
        nairobiHealthFacilityRepository.deleteById(id);
    }
}