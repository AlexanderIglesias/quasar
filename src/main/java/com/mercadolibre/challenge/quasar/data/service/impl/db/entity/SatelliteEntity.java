package com.mercadolibre.challenge.quasar.data.service.impl.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "satellite")
public class SatelliteEntity implements Serializable  {
	
	private static final long serialVersionUID = -7030735600961748880L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String[] message;
	private Float distance;
	private PositionEntity position;

}
