package com.geotechmap.gtm.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDto;
import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDtoResponse;
import com.geotechmap.gtm.Entities.Utilisateur;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.UtilisateurRepository;
import com.geotechmap.gtm.Util.CurrentUserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository repository;

    final ModelMapper modelMapper = new ModelMapper();
  
    private UtilisateurDto convertToDto(Utilisateur utilisateur) {
        UtilisateurDto utilisateurDto = modelMapper.map(utilisateur, UtilisateurDto.class);
        return utilisateurDto;
    }

    private Utilisateur convertToEntity(UtilisateurDto UtilisateurDto) throws ParseException {
        Utilisateur utilisateur = modelMapper.map(UtilisateurDto, Utilisateur.class);
        return utilisateur;
    }
    //_________________________

    public UtilisateurDtoResponse createNewUtilisateur(UtilisateurDto utilisateurDto) throws ParseException{
        UtilisateurDtoResponse utilisateurDtoResponse = new UtilisateurDtoResponse();
        utilisateurDtoResponse.setUtilisateurDto(null);
        try{
             //___ Audit
            utilisateurDto.setCreatedBy(CurrentUserUtil.getUsername());
            utilisateurDto.setLastModifiedBy(CurrentUserUtil.getUsername());
            utilisateurDto.setCreatedDate(new Date());
            utilisateurDto.setLastModifiedDate(new Date());
        //___ Fin Audit
        Utilisateur utilisateur = convertToEntity(utilisateurDto);
        Utilisateur utilisateurCreated = repository.save(utilisateur);
        utilisateurDtoResponse.setUtilisateurDto(convertToDto(utilisateurCreated));
        utilisateurDtoResponse.setMessage("Succès !");
    }catch(IllegalArgumentException e){
        //
    }
    return utilisateurDtoResponse;
    }

    public List<UtilisateurDto> listAllUtilisateurs(){
        //   System.out.println("*******************************"+ CurrentUserUtil.getUsername()); 
            List<UtilisateurDto> utilisateurDto;
            List<Utilisateur> utilisateurs = repository.findAll();
            Type listType = new TypeToken<List<UtilisateurDto>>() {}.getType();
            utilisateurDto = modelMapper.map(utilisateurs, listType);
            return utilisateurDto;
        }

    
        public UtilisateurDtoResponse updateUtilisateur(Long id, UtilisateurDto utilisateurDto) throws ParseException{
            Optional<Utilisateur> optional = repository.findById(id);
            if (!optional.isPresent()){
                throw new ResourceNotFoundException("Utilisateur not found with id :" + id );
            } else {
                UtilisateurDtoResponse utilisateurDtoResponse = new UtilisateurDtoResponse();
                utilisateurDtoResponse.setUtilisateurDto(null);
               try {
                   //___ Audit
                   utilisateurDto.setLastModifiedBy(CurrentUserUtil.getUsername());
                   utilisateurDto.setLastModifiedDate(new Date());
               //___ Fin Audit
                Utilisateur utilisateur = convertToEntity(utilisateurDto);
                utilisateur.setId(id);// je dois mettre l'id dans le body et enlever ca en parametre
                
                Utilisateur utilisateurCreated =  repository.save(utilisateur);
                utilisateurDtoResponse.setUtilisateurDto(convertToDto(utilisateurCreated));
                utilisateurDtoResponse.setMessage("Succès !");
            } catch (Exception e) {
                //TODO: handle exceptionessaiDtoResponse
            }
                return utilisateurDtoResponse;
            }
        }

        public void deleteUtilisateur(Long id) {
            Optional<Utilisateur> optional = repository.findById(id);
            if (!optional.isPresent()){
                throw new ResourceNotFoundException("Utilisateur not found with id :" + id );
            } else {
                repository.deleteById(id);
            }
        }

        public UtilisateurDtoResponse getUtilisateur(Long id) {
            Optional<Utilisateur> optional = repository.findById(id);
            UtilisateurDtoResponse utilisateurDtoResponse = new UtilisateurDtoResponse();
            utilisateurDtoResponse.setUtilisateurDto(null);
           
            if (!optional.isPresent()){
                throw new ResourceNotFoundException("Utilisateur not found with id :" + id );
            } else {
                utilisateurDtoResponse.setUtilisateurDto(convertToDto(optional.get()));
                utilisateurDtoResponse.setMessage("Succès !");
           
                return utilisateurDtoResponse;
            }
        }
        

}


