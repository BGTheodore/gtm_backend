package com.example.gtm.Controllers;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.gtm.Entities.TypeEssai;
import com.example.gtm.Repositories.TypeEssaiRepository;
import com.example.gtm.Services.TypeEssaiService;

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

import Dto.TypeEssai.TypeEssaiDto;


@RestController
@RequestMapping("/api/type_essais")
@CrossOrigin(origins = "http://localhost:3000")
public class TypeEssaiController {
    @Autowired
    TypeEssaiService service;
    TypeEssaiRepository repository;

    //Create a TypeEssai
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TypeEssaiDto createNewTypeEssai(@Valid @RequestBody TypeEssaiDto typeEssaiDto) throws ParseException {         
        return service.createNewTypeEssai(typeEssaiDto);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<List<TypeEssaiDto>>  getAllTypeEssais(){
        return ResponseEntity.ok().body(service.listAllTypeEssais());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public TypeEssaiDto getTypeEssai(@PathVariable Long id){
        return service.getTypeEssai(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TypeEssaiDto> updateTypeEssai(@RequestBody TypeEssaiDto typeEssaiDto, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok().body(service.updateTypeEssai(id, typeEssaiDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteTypeEssai(@PathVariable Long id) {
            service.deleteTypeEssai(id);
    }
    
}
