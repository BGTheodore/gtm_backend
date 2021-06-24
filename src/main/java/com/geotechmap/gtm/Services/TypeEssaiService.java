package com.geotechmap.gtm.Services;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Entities.TypeEssai;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.TypeEssaiRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TypeEssaiDto createNewTypeEssai(TypeEssaiDto typeEssaiDto) throws ParseException {
        TypeEssai typeEssai = convertToEntity(typeEssaiDto);
        TypeEssai intitutionCreated = repository.save(typeEssai);
        return convertToDto(intitutionCreated);
        }

    public List<TypeEssaiDto> listAllTypeEssais() {
        List<TypeEssaiDto> typeEssaiDto;
        List<TypeEssai> typeEssais = repository.findAll();
        Type listType = new TypeToken<List<TypeEssaiDto>>() {}.getType();
        typeEssaiDto = modelMapper.map(typeEssais, listType);
        return typeEssaiDto;
        }
    
    public TypeEssaiDto updateTypeEssai(Long id, TypeEssaiDto typeEssaiDto) throws ParseException {
        Optional<TypeEssai> optional = repository.findById(id);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Type Essai not found with id :" + id);
        } else {
            TypeEssai typeEssai = convertToEntity(typeEssaiDto);
            typeEssai.setId(id);// je dois mettre l'id dans le body et enlever ca en parametre
            return convertToDto(repository.save(typeEssai));
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

    public TypeEssaiDto getTypeEssai(Long id) {
        Optional<TypeEssai> optional = repository.findById(id);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Type Essai not found with id :" + id);
        } else {
            return convertToDto(optional.get());
        }
    }
}
