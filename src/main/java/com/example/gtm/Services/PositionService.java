package com.example.gtm.Services;

import java.util.List;
import java.util.Optional;
import com.example.gtm.Exception.ResourceNotFoundException;
import com.example.gtm.Entities.Position;
import com.example.gtm.Repositories.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Service
public class PositionService {
    @Autowired
    PositionRepository repository;

    public Position createNewPosition(Position position) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(position.getLatitude(), position.getLongitude());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(3857);
        position.setGeom(point);

        return repository.save(position);
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