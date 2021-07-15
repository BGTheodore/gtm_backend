package com.geotechmap.gtm.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.geotechmap.gtm.Dto.Institution.InstitutionDto;
import com.geotechmap.gtm.Dto.Institution.InstitutionDtoResponse;
import com.geotechmap.gtm.Entities.Essai;
import com.geotechmap.gtm.Entities.Institution;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.EssaiRepository;
import com.geotechmap.gtm.Repositories.InstitutionRepository;
import com.geotechmap.gtm.Util.CurrentUserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class InstitutionService {
    @Autowired
    InstitutionRepository repository;
    @Autowired
    EssaiRepository essaiRepository;

    final ModelMapper modelMapper = new ModelMapper();
  
    private InstitutionDto convertToDto(Institution institution) {
        InstitutionDto institutionDto = modelMapper.map(institution, InstitutionDto.class);
        return institutionDto;
    }

    private Institution convertToEntity(InstitutionDto institutionDto) throws ParseException {
        Institution institution = modelMapper.map(institutionDto, Institution.class);
        return institution;
    }
    //_________________________
    
    public InstitutionDtoResponse createNewInstitution(InstitutionDto institutionDto) {
        InstitutionDtoResponse institutionDtoResponse = new InstitutionDtoResponse();
        institutionDtoResponse.setInstitutionDto(null);
        try{
             //___ Audit
            institutionDto.setCreatedBy(CurrentUserUtil.getUsername());
            institutionDto.setLastModifiedBy(CurrentUserUtil.getUsername());
            institutionDto.setCreatedDate(new Date());
            institutionDto.setLastModifiedDate(new Date());
        //___ Fin Audit
        Institution institution = convertToEntity(institutionDto);
        Institution institutionCreated = repository.save(institution);
        institutionDtoResponse.setInstitutionDto(convertToDto(institutionCreated));
        institutionDtoResponse.setMessage("Succès !");
    }catch(IllegalArgumentException e){
        institutionDtoResponse.setMessage(e.getMessage());
    }
    catch(DataIntegrityViolationException e){
        institutionDtoResponse.setMessage(e.getMessage());
        System.out.println("=================="+institutionDto);
    }
    catch(ParseException e){
        institutionDtoResponse.setMessage(e.getMessage());
        System.out.println("=================="+institutionDto);
    }finally{
         System.out.println("=================="+institutionDto);
    return institutionDtoResponse;
    }
   
    }

    public List<InstitutionDto> listAllInstitutions(){
    //   System.out.println("*******************************"+ CurrentUserUtil.getUsername()); 
        List<InstitutionDto> institutionDto;
        List<Institution> institutions = repository.findAll();
        Type listType = new TypeToken<List<InstitutionDto>>() {}.getType();
        institutionDto = modelMapper.map(institutions, listType);
        return institutionDto;
    }

    public InstitutionDtoResponse updateInstitution(Long id, InstitutionDto institutionDto) throws ParseException{
        Optional<Institution> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Institution not found with id :" + id );
        } else {
            InstitutionDtoResponse institutionDtoResponse = new InstitutionDtoResponse();
            institutionDtoResponse.setInstitutionDto(null);
           try {
               //___ Audit
               institutionDto.setLastModifiedBy(CurrentUserUtil.getUsername());
               institutionDto.setLastModifiedDate(new Date());
           //___ Fin Audit
            Institution institution = convertToEntity(institutionDto);
            institution.setId(id);// je dois mettre l'id dans le body et enlever ca en parametre
            
            Institution institutionCreated =  repository.save(institution);
            institutionDtoResponse.setInstitutionDto(convertToDto(institutionCreated));
            institutionDtoResponse.setMessage("Succès !");
        } catch (Exception e) {
            //TODO: handle exceptionessaiDtoResponse
        }
            return institutionDtoResponse;
        }
    }

    public void deleteInstitution(Long id) {
        Optional<Institution> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Institution not found with id :" + id );
        } else {
            repository.deleteById(id);
            essaiRepository.deleteByIdInstitution(id);
        }
    }

    public InstitutionDtoResponse getInstitution(Long id) {
        Optional<Institution> optional = repository.findById(id);
        InstitutionDtoResponse institutionDtoResponse = new InstitutionDtoResponse();
        institutionDtoResponse.setInstitutionDto(null);
       
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Institution not found with id :" + id );
        } else {
            institutionDtoResponse.setInstitutionDto(convertToDto(optional.get()));
            institutionDtoResponse.setMessage("Succès !");
       
            return institutionDtoResponse;
        }
    }

    public Long countInstitutions() {
        return repository.countInstitutions();
    }
}
