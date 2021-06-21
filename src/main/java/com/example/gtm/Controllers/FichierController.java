package com.example.gtm.Controllers;

import java.io.IOException;

import com.example.gtm.Services.FichierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/file")
public class FichierController {
    @Autowired
    FichierService fichierService;

    @PostMapping
    public void fichierController(@RequestParam("fichier") MultipartFile fichier) throws IllegalStateException, IOException{
        fichierService.telechargementFichier(fichier);

    }
}
