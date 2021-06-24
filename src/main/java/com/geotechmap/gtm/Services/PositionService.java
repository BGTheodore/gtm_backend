package com.geotechmap.gtm.Services;

import java.util.List;
import java.util.Optional;

import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Entities.Position;
import com.geotechmap.gtm.Exception.ResourceNotFoundException;
import com.geotechmap.gtm.Repositories.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Service
public class PositionService {
    @Autowired
    PositionRepository repository;

    public Position createNewPosition(PositionDto position) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(position.getLatitude(), position.getLongitude());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(3857);
        position.setGeom(point);

        Position savedPosition =  new Position();
        savedPosition.setAdresse(position.getAdresse());
        savedPosition.setAltitude(position.getAltitude());
        savedPosition.setCommune(position.getCommune());
        savedPosition.setSectionCommunale(position.getSectionCommunale());
        savedPosition.setDepartement(position.getDepartement());
        savedPosition.setGeom(position.getGeom());
   


        return repository.save(savedPosition);
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