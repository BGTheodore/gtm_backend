package com.example.gtm.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.example.gtm.Entities.Position;
import com.example.gtm.Repositories.PositionRepository;
import com.example.gtm.Services.PositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.locationtech.jts.geom.Point;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/positions")
public class PositionController {
    @Autowired
    PositionService service;
    PositionRepository repository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Position> createNewPosition(@Valid @RequestBody Position position){
        Position createdPosition = service.createNewPosition(position);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Position>> getAllPositions(){
        return ResponseEntity.ok().body(service.listAllPositions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Position>> getPosition(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getPosition(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Position> updatePosition(@RequestBody Position position, @PathVariable Long id) {
        return ResponseEntity.ok().body(service.updatePosition(id, position));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable Long id){
        service.deletePosition(id);
    }

  
    //__Travail sur point gégraphique__
    @GetMapping(path = "/search_close_to")
    // @GetMapping
    // public List<testPositiongeographiqueEntity> findAllHospitalsWithinSubCounty(@RequestParam("id") int id) {
    //     return nairobiHealthFacilityService.findAllHospitalsWithinSubCounty(id);
    // }
    public  ResponseEntity<List<Position>>  rechercheAuxEnvirons(@RequestParam Map<String,String> requestParams){
        return ResponseEntity.ok().body(service
        .rechercheAuxEnvirons(requestParams.get("latitude"), 
        requestParams.get("longitude")));
    }
}


