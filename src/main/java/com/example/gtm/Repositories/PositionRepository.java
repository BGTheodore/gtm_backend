package com.example.gtm.Repositories;

import java.util.List;

import com.example.gtm.Entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.locationtech.jts.geom.Point;

public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(value = "SELECT * FROM positions p WHERE ST_DWithin(p.geom, :point, 30)"
    , nativeQuery = true)
    List<Position> rechercheAuxEnvirons(@Param("point") Point point );


}
