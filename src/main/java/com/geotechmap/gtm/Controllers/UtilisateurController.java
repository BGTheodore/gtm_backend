package com.geotechmap.gtm.Controllers;
import java.text.ParseException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDto;
import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDtoResponse;
import com.geotechmap.gtm.Entities.Utilisateur;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class UtilisateurController {
    @Autowired
    UtilisateurService service;
    UtilisateurRepository repository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UtilisateurDtoResponse createNewUtilisateur( @RequestBody UtilisateurDto utilisateurDto) throws ParseException {
        System.out.println("==================="+utilisateurDto);
        return service.createNewUtilisateur(utilisateurDto);
    }

    @GetMapping
    // @RolesAllowed({"SUPER_ADMIN"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs(){
        return ResponseEntity.ok().body(service.listAllUtilisateurs());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UtilisateurDtoResponse getUtilisateur(@PathVariable Long id){
        return service.getUtilisateur(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UtilisateurDtoResponse> updateUtilisateur(@RequestBody UtilisateurDto utilisateurDto, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok().body(service.updateUtilisateur(id, utilisateurDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtilisateur(@PathVariable Long id){
        service.deleteUtilisateur(id);
    }

    @GetMapping(path = "/search")
    public  ResponseEntity<Utilisateur>  rechercheParUsername(@RequestParam String username) throws ParseException {
        //trim mot_cle
        return ResponseEntity.ok().body(service.rechercheParUsername(username));
    }

}
