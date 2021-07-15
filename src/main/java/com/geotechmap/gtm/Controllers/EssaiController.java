package com.geotechmap.gtm.Controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDto;
import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDtoResponse;
import com.geotechmap.gtm.Dto.Essai.EssaiDto;
import com.geotechmap.gtm.Dto.Essai.EssaiDtoResponse;
import com.geotechmap.gtm.Dto.Fichier.FichierDto;
import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Entities.Essai;
import com.geotechmap.gtm.Entities.EssaiDetails;
import com.geotechmap.gtm.Entities.Fichier;
import com.geotechmap.gtm.Entities.Position;
import com.geotechmap.gtm.Entities.TypeEssai;
import com.geotechmap.gtm.Repositories.EssaiRepository;
import com.geotechmap.gtm.Repositories.FichierRepository;
import com.geotechmap.gtm.Repositories.PositionRepository;
import com.geotechmap.gtm.Services.EssaiService;
import com.geotechmap.gtm.Services.FichierService;
import com.geotechmap.gtm.Services.PositionService;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Date;

@Data
class GetFileRequest {
    String idFichier;
}

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/api/essais")
public class EssaiController {
    @Autowired
    EssaiService service;

    EssaiRepository repository;
    @Autowired
    FichierService fichierService;
    @Autowired
    FichierRepository fichierRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    PositionService positionService;

    //Create a test
    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // @ResponseBody
    // public EssaiDto createNewEssai(@RequestBody @Valid EssaiDto essaiDto
    //     ) throws ParseException, NoSuchAlgorithmException, InvalidKeySpecException{    
    //         //__creation du fichier dans la BD apres l'avire enregistré sur le file server
    //             FichierDto fichier = fichierService.genererStuctureFichier(essaiDto);
    //             fichierService.createNewFichier(fichier);
    //         //__fin creation du fichier dans la BD
    //         //__création de positiion géographique:
    //             // Position position = essaiDto.getPosition(); 
    //             PositionDto position = service.genererStucturePosition(essaiDto);
    //             positionService.createNewPosition(position);
    //         //__fin création de positiion géographique:

    //         essaiDto.setPosition(position);
    //         essaiDto.setFichier(fichier);
    //         EssaiDto createdEssai = service.createNewEssai(essaiDto);
    //         return createdEssai;
    // }

    @PostMapping
    @RolesAllowed({"OPERATEUR"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public EssaiDtoResponse createNewEssai(@RequestBody @Valid EssaiDto essaiDto
        ) throws ParseException, NoSuchAlgorithmException, InvalidKeySpecException{
            EssaiDtoResponse createdEssai = service.createNewEssai(essaiDto);
            return createdEssai;
    }

    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<List<EssaiDetailsDto>>  getAllEssais(){
        return ResponseEntity.ok().body(service.listAllEssais());
    }
    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public  ResponseEntity<List<EssaiDto>>  getAllEssais(){
    //     return ResponseEntity.ok().body(service.listAllEssais());
    // }

    @PutMapping("/{id}")
    @RolesAllowed({"ADMIN_INSTITUTION", "OPERATEUR"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EssaiDtoResponse> updateEssai(@RequestBody EssaiDto essaiDto, @PathVariable Long id) throws ParseException, NoSuchAlgorithmException, InvalidKeySpecException {  
        return ResponseEntity.ok().body(service.updateEssai(id, essaiDto));
    }

    //cette route est pour retourner les essai dans le webmap tout en les regroupant par categorie pour générer les ovelay dynamiquent
    // @GetMapping(path = "/webmap")
    // @ResponseStatus(HttpStatus.OK)
    // public  ResponseEntity<List<EssaiDto>>  getAllEssaisRegroupeParCategorie(){
    //     return ResponseEntity.ok().body(service.listAllEssais());
    // }


    // @PutMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<Essai> updateEssai(@RequestBody Essai essai, @PathVariable Long id) {
    //     //__ update position de l'aissai
    //         Position position = essai.getPosition(); service.genererStucturePosition(essai);
    //         positionService.updatePosition(essai.getPosition().getId(), position);
    //     //__ fin update position de l'aissai

    //     //__creation du fichier dans la BD
    //         Fichier fichier = fichierService.genererStuctureFichier(essai);
    //         fichierService.updateFichier(essai.getFichier().getId(), fichier);
    //     //__fin creation du fichier dans la BD
    //     essai.setPosition(position);
    //     essai.setFichier(fichier);
    //     return ResponseEntity.ok().body(service.updateEssai(id, essai));
    // }

    @GetMapping("/{id}")
    @ResponseBody
    public EssaiDto getEssai(@PathVariable Long id){
        return service.getEssai(id);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN_INSTITUTION", "OPERATEUR"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteEssai(@PathVariable Long id) {
            service.deleteEssai(id);
    }

    //__pour display les essais dans le webmap
    @GetMapping(path = "/search")
    public  ResponseEntity<List<TypeEssai>>  rechercheParmotsCles(@RequestParam String mot_cle) throws ParseException {
        //trim mot_cle
        return ResponseEntity.ok().body(service.rechercheParmotsCles(mot_cle));
    }
    //__fin

    @GetMapping(path = "/count")
    public  ResponseEntity<Long>  rechercheParmotsCles() throws ParseException {
        return ResponseEntity.ok().body(service.countEssais());
    }

    //__pagination
    @GetMapping(path = "/fetch_with_pagination")
    public  EssaiDetailsDtoResponse  fetchWithPagination(@RequestParam(value="pageNumber") int pageNumber, 
         @RequestParam(value="pageSize") int pageSize ) throws ParseException {
        return service.fetchWithPagination(pageSize, pageNumber);
    }

    // //__pour display les essais filtré dans la liste
    // @GetMapping(path = "/filter_list")
    // public  EssaiDetailsDtoResponse  filterList(@RequestParam String mot_cle) throws ParseException {
    //     //trim mot_cle
    //     return ResponseEntity.ok().body(service.filterList(mot_cle));
    //     return service.filterList(pageSize, pageNumber);
    // }
    // //__fin



}
