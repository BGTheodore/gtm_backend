package com.geotechmap.gtm.Controllers;

import java.io.IOException;
import java.text.ParseException;

import com.geotechmap.gtm.Dto.Fichier.FichierDto;
import com.geotechmap.gtm.Services.FichierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/api/file")
public class FichierController {
    @Autowired
    FichierService fichierService;

    // @GetMapping("/{id}")
    // @ResponseBody
    // public FichierDto getFichier(@PathVariable Long id){
    //     return fichierService.getFichier(id);
    // }
    
    @GetMapping("/info")
    public ResponseEntity<FichierDto> getEssai(@RequestParam Long id){
        return ResponseEntity.ok().body(fichierService.getFichier(id));
    }
    // @PostMapping
    // public void fichierController(@RequestParam("fichier") MultipartFile fichier) throws IllegalStateException, IOException{
    //     fichierService.telechargementFichier(fichier);

    // }
}
