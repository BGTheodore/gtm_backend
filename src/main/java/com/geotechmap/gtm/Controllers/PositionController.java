package com.geotechmap.gtm.Controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Entities.Position;
import com.geotechmap.gtm.Repositories.PositionRepository;
import com.geotechmap.gtm.Services.PositionService;

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


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/api/positions")
public class PositionController {
    @Autowired
    PositionService service;
    PositionRepository repository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PositionDto> createNewPosition(@Valid @RequestBody PositionDto positionDto) throws ParseException{
        PositionDto createdPosition = service.createNewPosition(positionDto);
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


