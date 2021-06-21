package com.example.gtm.Controllers;

import java.util.List;

import com.example.gtm.Entities.testPositiongeographiqueEntity;
import com.example.gtm.Services.NairobiHealthFacilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.gtm.Exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/nairobihealthfacilities")
public class testPositiongeographiqueController {

    @Autowired
    private NairobiHealthFacilityService nairobiHealthFacilityService;

    @GetMapping
    public List<testPositiongeographiqueEntity> findAll() {
        return nairobiHealthFacilityService.findAll();
    }

    @GetMapping(path = "/")
    public testPositiongeographiqueEntity findById(@RequestParam("id") int id) {
        try {
            return nairobiHealthFacilityService.findById(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @GetMapping(path = "/hospitalswithinsubcounty")
    public List<testPositiongeographiqueEntity> findAllHospitalsWithinSubCounty(@RequestParam("id") int id) {
        return nairobiHealthFacilityService.findAllHospitalsWithinSubCounty(id);
    }

    @GetMapping(path = "/nearbyhealthfacilities") 
    public List<testPositiongeographiqueEntity> findAllHospitalsByDistanceFromUser(@RequestParam("userlocation") List<Double> userLocation) {
        //this extraction can also be implemented in return method
        double userLongitude = userLocation.get(0);
        double userLatitude = userLocation.get(1);

        return nairobiHealthFacilityService.findAllHospitalsByDistanceFromUser(userLongitude, userLatitude);
    }

    @DeleteMapping(path = "/")
    public void deleteById(@RequestParam("id") int id) {
        nairobiHealthFacilityService.deleteById(id);
    }
}