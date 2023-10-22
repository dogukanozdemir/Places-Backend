package com.places.demo.repository;

import com.places.demo.entity.NearbySearchLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NearbySearchLocationRepository extends JpaRepository<NearbySearchLocation, Long> {}
