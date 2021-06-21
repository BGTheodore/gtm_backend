package com.example.gtm.Services;

import java.io.File;
import java.io.IOException;

import com.example.gtm.Entities.Essai;
import com.example.gtm.Entities.Fichier;
import com.example.gtm.Exception.ResourceNotFoundException;
import com.example.gtm.Repositories.FichierRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Dto.Essai.EssaiDto;
import Dto.Fichier.FichierDto;

import java.util.Optional;

import javax.validation.Valid;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class FichierService {
    @Autowired
    FichierRepository repository;

    final ModelMapper modelMapper = new ModelMapper();
  
    private FichierDto convertToDto(Fichier fichier) {
        FichierDto fichierDto = modelMapper.map(fichier, FichierDto.class);
        return fichierDto;
    }

    private Fichier convertToEntity(FichierDto fichierDto) throws ParseException {
        Fichier fichier = modelMapper.map(fichierDto, Fichier.class);
        return fichier;
    }


    //____________________

    public FichierDto createNewFichier(Fichier fichier) throws ParseException{
        Fichier fichierCreated = repository.save(fichier);
        return convertToDto(fichierCreated);
    }

   public void telechargementFichier(MultipartFile fichier) throws IllegalStateException, IOException{
       fichier.transferTo(new File("/home/kevin/Documents/kevin/EN3/PROJET_FINAL_URGEO/Spring/gtm/geotechmap/backend/gtm/src/main/java/com/example/gtm/Services"+fichier.getOriginalFilename()));//Path de destination des fichiers telecharges
   }

   public Fichier updateFichier(Long id, Fichier fichier){
        Optional<Fichier> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Fichier not found with id :" + id );
        } else {
            fichier.setId(id);
            return repository.save(fichier);
        }
    }


    //============================

    public Fichier genererStuctureFichier(@Valid EssaiDto essaiDto) {
        Date date = new Date();
        String nomInitial = essaiDto.getFichier().getNom();
        String nomUniqueDuFichier = nomInitial.substring(0, nomInitial.length() - 3)+ new Timestamp(date.getTime()) + ".pdf";
        nomUniqueDuFichier = nomUniqueDuFichier.replace(' ','-');

        //__ fin enregistrement du fichier dans server de fichier  
        if(essaiDto.getId() != null){
            System.out.println(essaiDto.getId());
            System.out.println("MODIFICATION");
        }else{
            System.out.println(essaiDto.getId());
            System.out.println("CREATION");
        }
            File file = new File(nomUniqueDuFichier);
            System.out.println(nomUniqueDuFichier);
            try ( FileOutputStream fos = new FileOutputStream(file); ) {
            String b64 = essaiDto.getPdf();
            byte[] decoder = Base64.getDecoder().decode(b64);
            fos.write(decoder); //sonje free memory
            System.out.println("PDF File Saved");
            } catch (Exception e) {
            e.printStackTrace();
            }   
        //__ fin enregistrement du fichier dans server de fichier  
            //__ recupération du nom de domaine
            InetAddress ip;
            String localIp ="";
            try {
      
              ip = InetAddress.getLocalHost();
              System.out.println("Current IP address : " + ip.getHostAddress());
              localIp = ip.getHostAddress();
      
            } catch (UnknownHostException e) {
      
              e.printStackTrace();
      
            }
            // __ fin récuperation du nom de domaine
        Fichier fichier = new Fichier();
        fichier.setNom(essaiDto.getFichier().getNom());
        fichier.setCapacite(essaiDto.getFichier().getCapacite());
        fichier.setFormat(essaiDto.getFichier().getFormat());
        fichier.setLien(localIp +"/"+nomUniqueDuFichier);
        fichier.setHash("igutUIYGUFVEHJFWDSGYFEBIUSDGYUKGf");
       
        return fichier;
        
    }

}
