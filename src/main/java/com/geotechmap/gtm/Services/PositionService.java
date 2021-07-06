package com.geotechmap.gtm.Services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Entities.Position;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.PositionRepository;
import com.geotechmap.gtm.Util.CurrentUserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;

@Service
public class PositionService {
    @Autowired
    PositionRepository repository;

    final ModelMapper modelMapper = new ModelMapper();
  
    private PositionDto convertToDto(Position position) {
        PositionDto positionDto = modelMapper.map(position, PositionDto.class);
        return positionDto;
    }

    private Position convertToEntity(PositionDto positionDto) throws ParseException {
        Position position = modelMapper.map(positionDto, Position.class);
        return position;
    }
    //_________________________
    
    public PositionDto createNewPosition(PositionDto positionDto) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(positionDto.getLatitude(), positionDto.getLongitude());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(3857);
        positionDto.setGeom(point);

        Position position = convertToEntity(positionDto);
        Position savedPosition = repository.save(position);
        return convertToDto(savedPosition);
    }

    public List<Position> listAllPositions() {
        return repository.findAll();
        }

    public Position updatePosition(Long id, Position position){
        Optional<Position> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Position not found with id :" + id );
        } else {
            position.setId(id);
            return repository.save(position);
        }
    }

    public void deletePosition(Long id) {
        Optional<Position> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Position not found with id :" + id );
        } else {
            repository.deleteById(id);
        }
    }

    public Optional<Position> getPosition(Long id) {
        Optional<Position> optional = repository.findById(id);
        if (!optional.isPresent()){
            throw new ResourceNotFoundException("Position not found with id :" + id );
        } else {
            return optional;
        }
    }

    public List<Position> rechercheAuxEnvirons(String latitude, String longitude) {
        //On crée un point à partir des coordonnées en paramètre.
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(Float.parseFloat(latitude), Float.parseFloat(longitude));
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(3857);

        return repository.rechercheAuxEnvirons(point);
    }
}