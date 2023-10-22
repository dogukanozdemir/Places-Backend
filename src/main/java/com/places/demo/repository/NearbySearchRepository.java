package com.places.demo.repository;

import com.places.demo.entity.NearbySearch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NearbySearchRepository extends JpaRepository<NearbySearch, Long> {

  Optional<NearbySearch> findByLatitudeAndLongitudeAndRadius(
      Double latitude, Double longitude, Integer radius);
}
