package com.example.gtm.Services;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.gtm.Entities.Essai;
import com.example.gtm.Exception.ResourceNotFoundException;
import com.example.gtm.Repositories.EssaiRepository;
import com.example.gtm.Repositories.TypeEssaiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dto.Essai.EssaiDto;
import Dto.TypeEssai.TypeEssaiDto;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.example.gtm.Entities.Position;
import com.example.gtm.Entities.TypeEssai;



@Service
public class EssaiService {
    @Autowired
    EssaiRepository repository;
    @Autowired
    TypeEssaiService typeEssaiService;
    @Autowired
    TypeEssaiRepository typeEssaiRepository;


    final ModelMapper modelMapper = new ModelMapper();
    
    private EssaiDto convertToDto(Essai essai) {
        EssaiDto essaiDto = modelMapper.map(essai, EssaiDto.class);
        return essaiDto;
    }
    private Essai convertToEntity(EssaiDto essaiDto) throws ParseException {
        Essai essai = modelMapper.map(essaiDto, Essai.class);
        return essai;
    }
    
    //_______________________
    
    public EssaiDto createNewEssai(EssaiDto essaiDto) throws ParseException {
        Essai essai = convertToEntity(essaiDto);
        Essai essaiCreated =  repository.save(essai);
        return convertToDto(essaiCreated);
        }

    public List<EssaiDto> listAllEssais() {
        List<EssaiDto> essaiDto;
        List<Essai> essais = repository.findAll();
        Type listType = new TypeToken<List<EssaiDto>>() {}.getType();
        essaiDto = modelMapper.map(essais, listType);
        return essaiDto;
        }

    public List<Essai> getAllEssaisRegroupeParCategorie() {
        return repository.getAllEssaisRegroupeParCategorie();
        }
    


    public EssaiDto updateEssai(Long id, EssaiDto essaiDto) throws ParseException {
        Optional<Essai> optional = repository.findById(id);
        if (!optional.isPresent()) {
        throw new ResourceNotFoundException("Essai not found with id :" + id);
        } else {
            Essai essai = convertToEntity(essaiDto);
            essai.setId(id);
            return convertToDto(repository.save(essai));
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
        
        // List<TypeEssai> filteredList;
        List<TypeEssai> originalList = typeEssaiRepository.findAll();
        List<Long> nameFilter = repository.rechercheParmotsCles(mot_cle);
      
        // filteredList = originalList.stream()
        //   .filter(typeEssai -> nameFilter.contains(typeEssai.getEssais()))
        //   .collect(Collectors.toList());

        for (TypeEssai typeEssai : originalList) {
            Iterator<Essai> i = typeEssai.getEssais().iterator();
            while (i.hasNext()) {
                Essai s = i.next(); // must be called before you can call i.remove()
                // Do something
                if (nameFilter.contains(s.getId()) == false) 
                {
                    i.remove();
                }
                
             }
            // for (Essai essai : typeEssai.getEssais()) {
            //         if (nameFilter.contains(essai.getId()) ) {
            //             System.out.println(("----------------------"));
            //             typeEssai.getEssais().remove(essai);

            //         }              
            // }
        }

        // List<TypeEssaiDto> typeEssaiDto;
        //  Type listType = new TypeToken<List<TypeEssaiDto>>() {}.getType();
        //  typeEssaiDto = modelMapper.map(filteredList, listType);
        // return typeEssaiDto;
        return originalList;
    }

    //============================

    public Position genererStucturePosition(@Valid EssaiDto essaiDto) {
        
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(essaiDto.getPosition().getLatitude(), essaiDto.getPosition().getLongitude());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(3857);//Nous devons choisir un SRID (old 4326) WGS84
        Position position = essaiDto.getPosition();
        position.setGeom(point);
        position.setLatitude(essaiDto.getPosition().getLatitude());
        position.setLongitude(essaiDto.getPosition().getLongitude());
        position.setAltitude(essaiDto.getPosition().getAltitude());
        position.setDepartement(essaiDto.getPosition().getDepartement());
        position.setCommune(essaiDto.getPosition().getCommune());
        position.setSectionCommunale(essaiDto.getPosition().getSectionCommunale());
        return position;
        
    }

}
