package com.mercadolibre.challenge.quasar.data.service.impl.db.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.challenge.quasar.data.service.impl.db.entity.SatelliteEntity;

@Repository
public interface SatelliteRepository extends JpaRepository<SatelliteEntity, Integer> {
    
	public SatelliteEntity findByName(String name);

}
