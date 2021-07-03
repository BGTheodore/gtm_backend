package com.geotechmap.gtm.Controllers;
import java.text.ParseException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Institution.InstitutionDto;
import com.geotechmap.gtm.Repositories.InstitutionRepository;
import com.geotechmap.gtm.Services.InstitutionService;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {
    @Autowired
    InstitutionService service;
    InstitutionRepository repository;

    
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public InstitutionDto createNewInstitution(@Valid @RequestBody InstitutionDto institutionDto) throws ParseException {
        return service.createNewInstitution(institutionDto);
    }


    @GetMapping
    // @RolesAllowed({"SUPER_ADMIN"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InstitutionDto>> getAllInstitutions(){
        return ResponseEntity.ok().body(service.listAllInstitutions());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public InstitutionDto getInstitution(@PathVariable Long id){
        return service.getInstitution(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InstitutionDto> updateInstitution(@RequestBody InstitutionDto institutionDto, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok().body(service.updateInstitution(id, institutionDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstitution(@PathVariable Long id){
        service.deleteInstitution(id);
    }
}

