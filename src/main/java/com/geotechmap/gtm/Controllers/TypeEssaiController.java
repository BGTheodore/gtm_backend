package com.geotechmap.gtm.Controllers;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDtoResponse;
import com.geotechmap.gtm.Entities.TypeEssai;
import com.geotechmap.gtm.Repositories.TypeEssaiRepository;
import com.geotechmap.gtm.Services.TypeEssaiService;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@RestController
@RequestMapping("/api/type_essais")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class TypeEssaiController {
    @Autowired
    TypeEssaiService service;
    TypeEssaiRepository repository;

    //Create a TypeEssai
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TypeEssaiDtoResponse createNewTypeEssai(@Valid @RequestBody TypeEssaiDto typeEssaiDto) throws ParseException {         
        return service.createNewTypeEssai(typeEssaiDto);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<List<TypeEssaiDto>>  getAllTypeEssais(){
        return ResponseEntity.ok().body(service.listAllTypeEssais());
    }

    // @GetMapping
    // @ResponseBody
    // @ResponseStatus(HttpStatus.OK)
    // public  ResponseEntity<List<TypeEssaiDto>>  getAllTypeEssais(
    //   @RequestParam(required = false) String nom,
    //   @RequestParam(defaultValue = "0") int page,
    //   @RequestParam(defaultValue = "3") int size
    // ){
    //     Page<TypeEssaiDto> pageTuts;
    //     if (nom == null)
    //       pageTuts = tutorialRepository.findAll(pagingSort);
    //     else
    //       pageTuts = tutorialRepository.findByTitleContaining(title, pagingSort);
  
    //     tutorials = pageTuts.getContent();

    //     return ResponseEntity.ok().body(service.listAllTypeEssais());
    // }

    @GetMapping("/{id}")
    @ResponseBody
    public TypeEssaiDtoResponse getTypeEssai(@PathVariable Long id){
        return service.getTypeEssai(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TypeEssaiDtoResponse> updateTypeEssai(@RequestBody TypeEssaiDto typeEssaiDto, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok().body(service.updateTypeEssai(id, typeEssaiDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteTypeEssai(@PathVariable Long id) {
            service.deleteTypeEssai(id);
    }
    
}
