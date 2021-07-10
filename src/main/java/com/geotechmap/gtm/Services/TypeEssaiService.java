package com.geotechmap.gtm.Services;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDtoResponse;
import com.geotechmap.gtm.Entities.TypeEssai;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.TypeEssaiRepository;
import com.geotechmap.gtm.Util.CurrentUserUtil;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TypeEssaiService {
    @Autowired
    TypeEssaiRepository repository;
    
    final ModelMapper modelMapper = new ModelMapper();
  
    private TypeEssaiDto convertToDto(TypeEssai typeEssai) {
        TypeEssaiDto typeEssaiDto = modelMapper.map(typeEssai, TypeEssaiDto.class);
        return typeEssaiDto;
    }

    private TypeEssai convertToEntity(TypeEssaiDto TypeEssaiDto) throws ParseException {
        TypeEssai TypeEssai = modelMapper.map(TypeEssaiDto, TypeEssai.class);
        return TypeEssai;
    }
    //_________________________

    public TypeEssaiDtoResponse createNewTypeEssai(TypeEssaiDto typeEssaiDto) throws ParseException {
        TypeEssaiDtoResponse typeEssaiDtoResponse = new TypeEssaiDtoResponse();
        typeEssaiDtoResponse.setTypeEssaiDto(null);
        try{
        //___ Audit
            typeEssaiDto.setCreatedBy(CurrentUserUtil.getUsername());
            typeEssaiDto.setLastModifiedBy(CurrentUserUtil.getUsername());
            typeEssaiDto.setCreatedDate(new Date());
            typeEssaiDto.setLastModifiedDate(new Date());
        //___ Fin Audit
        TypeEssai typeEssai = convertToEntity(typeEssaiDto);
        TypeEssai typeEssaiCreated = repository.save(typeEssai);
        typeEssaiDtoResponse.setTypeEssaiDto(convertToDto(typeEssaiCreated));
        typeEssaiDtoResponse.setMessage("success");
        }catch(IllegalArgumentException e){
            typeEssaiDtoResponse.setMessage(e.getMessage());
        }
        catch(DataIntegrityViolationException e){
            typeEssaiDtoResponse.setMessage(e.getMessage());
        }
        catch(ParseException e){
            typeEssaiDtoResponse.setMessage(e.getMessage());
        }finally{
            return typeEssaiDtoResponse;
        }
    }



    public List<TypeEssaiDto> listAllTypeEssais() {
        List<TypeEssaiDto> typeEssaiDto;
        List<TypeEssai> typeEssais = repository.findAll();
        Type listType = new TypeToken<List<TypeEssaiDto>>() {}.getType();
        typeEssaiDto = modelMapper.map(typeEssais, listType);
        return typeEssaiDto;
        }
    
    public TypeEssaiDtoResponse updateTypeEssai(Long id, TypeEssaiDto typeEssaiDto) throws ParseException {
        Optional<TypeEssai> optional = repository.findById(id);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Type Essai not found with id :" + id);
        } else {
            TypeEssaiDtoResponse typeEssaiDtoResponse = new TypeEssaiDtoResponse();
            typeEssaiDtoResponse.setTypeEssaiDto(typeEssaiDto);
            try {
           
            //___ Audit
                typeEssaiDto.setLastModifiedBy(CurrentUserUtil.getUsername());
                typeEssaiDto.setLastModifiedDate(new Date());
            //___ Fin Audit
            TypeEssai typeEssai = convertToEntity(typeEssaiDto);
            typeEssai.setId(id);// je dois mettre l'id dans le body et enlever ca en parametre
            TypeEssai typeEssaiCreated =  repository.save(typeEssai);
            typeEssaiDtoResponse.setTypeEssaiDto(convertToDto(typeEssaiCreated));
            typeEssaiDtoResponse.setMessage("Succès !");
        } catch (Exception e) {
            //TODO: handle exceptionessaiDtoResponse
        }
            return typeEssaiDtoResponse;
        }
    }

    public void deleteTypeEssai(Long id) {
        Optional<TypeEssai> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Type Essai not found with id :" + id);
        } else {
            repository.deleteById(id);
        }
    }

    public TypeEssaiDtoResponse getTypeEssai(Long id) {
        Optional<TypeEssai> optional = repository.findById(id);
        TypeEssaiDtoResponse typeEssaiDtoResponse = new TypeEssaiDtoResponse();
        typeEssaiDtoResponse.setTypeEssaiDto(null);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Type Essai not found with id :" + id);
        } else {
            typeEssaiDtoResponse.setTypeEssaiDto(convertToDto(optional.get()));
            typeEssaiDtoResponse.setMessage("Succès !");
            return typeEssaiDtoResponse;
        }
    }
}
