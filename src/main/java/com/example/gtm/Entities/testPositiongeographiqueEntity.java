

package com.example.gtm.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;

import org.locationtech.jts.geom.Point;

//imports ommited for brevity
@Getter
@Setter
@Entity
@Table(name = "nairobi_health_facilities")
public class testPositiongeographiqueEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "geom")
    private Point geom;
}