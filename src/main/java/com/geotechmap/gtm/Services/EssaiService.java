package com.geotechmap.gtm.Services;


import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.Valid;

import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDto;
import com.geotechmap.gtm.Dto.Essai.EssaiDetailsDtoResponse;
import com.geotechmap.gtm.Dto.Essai.EssaiDto;
import com.geotechmap.gtm.Dto.Essai.EssaiDtoResponse;
import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Entities.Essai;
import com.geotechmap.gtm.Entities.EssaiDetails;
import com.geotechmap.gtm.Entities.Position;
import com.geotechmap.gtm.Entities.TypeEssai;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.EssaiDetailsRepository;
import com.geotechmap.gtm.Repositories.EssaiRepository;
import com.geotechmap.gtm.Repositories.TypeEssaiRepository;
import com.geotechmap.gtm.Util.CurrentUserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.util.Date;
import java.util.HashMap;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class EssaiService {
    @Autowired
    EssaiRepository repository;
    @Autowired
    EssaiDetailsRepository essaiDetailsRepository;
    @Autowired
    TypeEssaiService typeEssaiService;
    @Autowired
    TypeEssaiRepository typeEssaiRepository;
    @Autowired
    private Environment env;


    final ModelMapper modelMapper = new ModelMapper();
    
    private EssaiDto convertToDto(Essai essai) {
        EssaiDto essaiDto = modelMapper.map(essai, EssaiDto.class);
        return essaiDto;
    }
    private Essai convertToEntity(EssaiDto essaiDto) throws ParseException {
        Essai essai = modelMapper.map(essaiDto, Essai.class);
        return essai;
    }

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
    
    //_______________________
    
    public EssaiDtoResponse createNewEssai(@Valid EssaiDto essaiDto)  {
        EssaiDtoResponse essaiDtoResponse = new EssaiDtoResponse();
        essaiDtoResponse.setEssaiDto(null);
       try{
        //___ Audit
            essaiDto.setCreatedBy(CurrentUserUtil.getUsername());
            essaiDto.setLastModifiedBy(CurrentUserUtil.getUsername());
            essaiDto.setCreatedDate(new Date());
            essaiDto.setLastModifiedDate(new Date());
        //___ Fin Audit
        //___AJOUTER POINT GEOGRAPHIQUE DANS PODITION 
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coordinate = new Coordinate(essaiDto.getPosition().getLatitude(), essaiDto.getPosition().getLongitude());
            Point point = geometryFactory.createPoint(coordinate);
            point.setSRID(3857);//Nous devons choisir un SRID (old 4326) WGS84
            essaiDto.getPosition().setGeom(point);
        //___COMPLETER INFORMATIONS DU FICHIER 
            Date date = new Date();
            String nomInitial = essaiDto.getFichier().getNom();
            String nomUniqueDuFichier = nomInitial.substring(0, nomInitial.length() - 3)+ new Timestamp(date.getTime()) + ".pdf";
            nomUniqueDuFichier = nomUniqueDuFichier.replace(' ','-');
            String hashDuNomDeFichier = hashString(nomUniqueDuFichier);
            String hashDuNomDeFichierSansCaractereCompromettant =  hashDuNomDeFichier.replace('/','-');
            essaiDto.getFichier().setHashNomFichier(hashDuNomDeFichierSansCaractereCompromettant);
            essaiDto.getFichier().setHashPdf(hashString(essaiDto.getPdf()));

        Essai essai = convertToEntity(essaiDto);
      
            Essai essaiCreated =  repository.save(essai);

            //__save image to file server
            
            //__ fin save image to file server

            essaiDtoResponse.setEssaiDto(convertToDto(essaiCreated));
            essaiDtoResponse.setMessage("Succès !");
        } catch (IllegalArgumentException e) {
            essaiDtoResponse.setMessage(e.getMessage());
        }catch(ParseException e ){

        }catch(NoSuchAlgorithmException e){
            //impossible de calculer le hash

        }catch(InvalidKeySpecException e){
            //
        }
        


        return essaiDtoResponse;
        }

    // public List<EssaiDto> listAllEssais() {
    //     List<EssaiDto> essaiDto;
    //     List<Essai> essais = repository.findAll();
    //     Type listType = new TypeToken<List<EssaiDto>>() {}.getType();
    //     essaiDto = modelMapper.map(essais, listType);
    //     return essaiDto;
    //     }

    public List<EssaiDetailsDto> listAllEssais() {
        List<EssaiDetailsDto> essaiDetailsDto;
        List<EssaiDetails> essaisDetails = essaiDetailsRepository.findAllFromView();
        Type listType = new TypeToken<List<EssaiDetailsDto>>() {}.getType();
        essaiDetailsDto = modelMapper.map(essaisDetails, listType);
        return essaiDetailsDto;
        }

    public List<Essai> getAllEssaisRegroupeParCategorie() {
        return repository.getAllEssaisRegroupeParCategorie();
        }
    


    public EssaiDtoResponse updateEssai(Long id, EssaiDto essaiDto) throws ParseException, NoSuchAlgorithmException, InvalidKeySpecException {
          Optional<Essai> optional = repository.findById(id);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Essai not found with id :" + id);
        } else {
             EssaiDtoResponse essaiDtoResponse = new EssaiDtoResponse();
             essaiDtoResponse.setEssaiDto(null);
            // try {
            
            //___AJOUTER POINT GEOGRAPHIQUE DANS PODITION 
               GeometryFactory geometryFactory = new GeometryFactory();
               Coordinate coordinate = new Coordinate(essaiDto.getPosition().getLatitude(), essaiDto.getPosition().getLongitude());
               Point point = geometryFactory.createPoint(coordinate);
               point.setSRID(3857);//Nous devons choisir un SRID (old 4326) WGS84
               essaiDto.getPosition().setGeom(point);
           //___COMPLETER INFORMATIONS DU FICHIER 
           Date date = new Date();
           String nomInitial = essaiDto.getFichier().getNom();
           String nomUniqueDuFichier = nomInitial.substring(0, nomInitial.length() - 3)+ new Timestamp(date.getTime()) + ".pdf";
           nomUniqueDuFichier = nomUniqueDuFichier.replace(' ','-');
           String hashDuNomDeFichier = hashString(nomUniqueDuFichier);
           String hashDuNomDeFichierSansCaractereCompromettant =  hashDuNomDeFichier.replace('/','-');
           essaiDto.getFichier().setHashNomFichier(hashDuNomDeFichierSansCaractereCompromettant);
           essaiDto.getFichier().setHashPdf(hashString(essaiDto.getPdf()));
           
                //___ Audit
                
                    //___essai
                    essaiDto.setCreatedBy(optional.get().getCreatedBy());
                    essaiDto.setCreatedDate(optional.get().getCreatedDate());
                    essaiDto.setLastModifiedBy(CurrentUserUtil.getUsername());
                    essaiDto.setLastModifiedDate(new Date());
                //___ Fin Audit
                essaiDto.getFichier().setId(optional.get().getFichier().getId());
                essaiDto.getPosition().setId(optional.get().getPosition().getId());
                
               Essai essai = convertToEntity(essaiDto);
           essai.setId(id);
           Essai essaiCreated = repository.save(essai); 
           essaiDtoResponse.setEssaiDto(convertToDto(essaiCreated));
           essaiDtoResponse.setMessage("success");
            // } catch (Exception e) {
            //     //TODO: handle exceptionessaiDtoResponse
            //     System.out.println(e.getMessage());
            // }
            // System.out.println("----------------------" + essaiDtoResponse);
            return essaiDtoResponse;
        }
    }

    public void deleteEssai(Long id) {
        Optional<Essai> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Essai not found with id :" + id);
        } else {
            repository.deleteById(id);
        }
    }

    public EssaiDto getEssai(Long id) {
        Optional<Essai> optional = repository.findById(id);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Essai not found with id :" + id);
        } else {
        return convertToDto(optional.get());
        }
    }

    public List<TypeEssai> rechercheParmotsCles(String mot_cle) {
        List<TypeEssai> originalList = typeEssaiRepository.findAll();
        List<Long> nameFilter = repository.rechercheParmotsCles(mot_cle);
        for ( TypeEssai typeEssai : originalList ) {
            Iterator<Essai> i = typeEssai.getEssais().iterator();
            while (i.hasNext()) {
                Essai s = i.next(); // must be called before you can call i.remove()
                if (nameFilter.contains(s.getId()) == false) 
                {
                    i.remove();
                }     
             }
        }
        return originalList;
    }

    //============================
public Object postFile() throws IOException{
    URL url = new URL("http://localhost:8081/api/file");
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection)con;
    http.setRequestMethod("POST"); 
    http.setDoOutput(true);

    byte[] out = "{\"username\":\"root\",\"password\":\"password\"}" .getBytes(StandardCharsets.UTF_8);
    int length = out.length;

    http.setFixedLengthStreamingMode(length);
    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    http.connect();
    try(OutputStream os = http.getOutputStream()) {
        os.write(out);
}


	return http;
}
    // public PositionDto genererStucturePosition(@Valid EssaiDto essaiDto) {
public Long countEssais() {
    return repository.countEssais();
}
        
//__ pagination
  public EssaiDetailsDtoResponse fetchWithPagination(int pageSize, int pageNumber){
      EssaiDetailsDtoResponse essaiDetailsDtoResponse = new EssaiDetailsDtoResponse();
      Pageable pageRequest =PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC,"created_date_essai"));
        Page<EssaiDetails> results = essaiDetailsRepository.fetchWithPagination( pageRequest);
        if(results.getContent() == null){
            essaiDetailsDtoResponse.setMessage("Aucun résultat trouvé");
            essaiDetailsDtoResponse.setPageNumber(0);
            essaiDetailsDtoResponse.setPageSize(0);
        }else{
            essaiDetailsDtoResponse.setMessage("sucess");
            essaiDetailsDtoResponse.setPageNumber(pageNumber);
            essaiDetailsDtoResponse.setPageSize(pageSize);

            List<EssaiDetailsDto> listEssaiDetailsDto;
            Type listType = new TypeToken<List<EssaiDetailsDto>>() {}.getType();
            listEssaiDetailsDto = modelMapper.map(results.getContent(), listType);

            essaiDetailsDtoResponse.setEssaiDetailsDto(listEssaiDetailsDto);
        }
        
        return essaiDetailsDtoResponse;
   
  }

}
