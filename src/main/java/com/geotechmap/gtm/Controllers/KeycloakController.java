package com.geotechmap.gtm.Controllers;
import java.text.ParseException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDto;
import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDtoResponse;
import com.geotechmap.gtm.Repositories.UtilisateurRepository;
import com.geotechmap.gtm.Services.UtilisateurService;

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




@RestController
@RequestMapping("/api/keycloakusers")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class KeycloakController {
    @Autowired
    UtilisateurService service;
    UtilisateurRepository repository;
    
    @GetMapping
    // @RolesAllowed({"SUPER_ADMIN"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Object>> getAllInstitutions(){
        return ResponseEntity.ok().body(service.listAllUtilisateursKeycloak());
    }
}
