package com.geotechmap.gtm.Services;

import java.io.File;
import java.io.IOException;

import org.springframework.core.env.Environment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Essai.EssaiDto;
import com.geotechmap.gtm.Dto.Fichier.FichierDto;
import com.geotechmap.gtm.Entities.Essai;
import com.geotechmap.gtm.Entities.Fichier;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.FichierRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class FichierService {
    @Autowired
    FichierRepository repository;

    final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private Environment env;
  
    private FichierDto convertToDto(Fichier fichier) {
        FichierDto fichierDto = modelMapper.map(fichier, FichierDto.class);
        return fichierDto;
    }

    private Fichier convertToEntity(FichierDto fichierDto) throws ParseException {
        Fichier fichier = modelMapper.map(fichierDto, Fichier.class);
        return fichier;
    }


    //____________________

    public FichierDto createNewFichier(FichierDto fichier) throws ParseException{

        Fichier fichierCreated = repository.save(convertToEntity(fichier));
        return convertToDto(fichierCreated);
    }

//    public void telechargementFichier(MultipartFile fichier) throws IllegalStateException, IOException{
//        fichier.transferTo(new File("/home/kevin/Documents/kevin/EN3/PROJET_FINAL_URGEO/Spring/gtm/geotechmap/backend/gtm/src/main/java/com/example/gtm/Services"+fichier.getOriginalFilename()));//Path de destination des fichiers telecharges
//    }

   public String hashString(String stringToHash) throws NoSuchAlgorithmException, InvalidKeySpecException
   {
       //__generer hash du nom du fichier
       String secretSalt = env.getProperty("secret_salt");
       byte[] secretSaltInBytes = secretSalt.getBytes(Charset.forName("UTF-8"));
       KeySpec spec = new PBEKeySpec(stringToHash.toCharArray(), secretSaltInBytes, 65536, 128);
       SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
       byte[] hash = factory.generateSecret(spec).getEncoded();
       Base64.Encoder enc = Base64.getEncoder();
       String hashToString = enc.encodeToString(hash);
       //__fin generer hash du nom du fichier
       return hashToString;
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

    public FichierDto getFichier(Long id) {
        Optional<Fichier> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Fichier not found with id :" + id );
        } else {
            return convertToDto(optional.get());
        }
    }


    //============================

    public FichierDto genererStuctureFichier(@Valid EssaiDto essaiDto) throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException {
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

            // File file = new File(nomUniqueDuFichier);
            // System.out.println(nomUniqueDuFichier);
            // try ( FileOutputStream fos = new FileOutputStream(file); ) {
            // String b64 = essaiDto.getPdf();
            // byte[] b64Decoder = Base64.getDecoder().decode(b64);
            // fos.write(b64Decoder); //sonje free memory
            // System.out.println("PDF File Saved");
            // } catch (Exception e) {
            // e.printStackTrace();
            // }   
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
        FichierDto fichier = new FichierDto();
        fichier.setNom(essaiDto.getFichier().getNom());
        fichier.setCapacite(essaiDto.getFichier().getCapacite());
        fichier.setFormat(essaiDto.getFichier().getFormat());
        fichier.setLien(localIp +"/"+nomUniqueDuFichier);
        String setHashNomFichier = hashString(nomUniqueDuFichier);
        fichier.setHashNomFichier(setHashNomFichier);
        // String s = new String(Base64.getDecoder().decode(essaiDto.getPdf()), StandardCharsets.UTF_8);
        String hashPdf = hashString(essaiDto.getPdf());
        fichier.setHashPdf(hashPdf);
        //__send the file to the file server
        // postToFileServer();
        //__send the file to the file server
       
        return fichier;
        
    }

}
