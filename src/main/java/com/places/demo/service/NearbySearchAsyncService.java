package com.places.demo.service;

import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.places.demo.dto.GetNearbySearchRequest;
import com.places.demo.entity.NearbySearch;
import com.places.demo.entity.NearbySearchLocation;
import com.places.demo.repository.NearbySearchLocationRepository;
import com.places.demo.repository.NearbySearchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NearbySearchAsyncService {

  private final NearbySearchRepository nearbySearchRepository;
  private final NearbySearchLocationRepository nearbySearchLocationRepository;

  @Async
  @Transactional
  public void saveSearch(GetNearbySearchRequest request, PlacesSearchResponse searchResponse) {
    NearbySearch nearbySearch =
        nearbySearchRepository.save(
            NearbySearch.builder()
                .latitude(request.latitude())
                .longitude(request.longitude())
                .radius(request.radius())
                .build());

    List<NearbySearchLocation> locations =
        Arrays.stream(searchResponse.results)
            .map(placesSearchResult -> createNearbySearchLocation(placesSearchResult, nearbySearch))
            .collect(Collectors.toList());

    nearbySearchLocationRepository.saveAll(locations);
  }

  private NearbySearchLocation createNearbySearchLocation(
      PlacesSearchResult result, NearbySearch nearbySearch) {
    return NearbySearchLocation.builder()
        .nearbySearch(nearbySearch)
        .latitude(result.geometry.location.lat)
        .longitude(result.geometry.location.lng)
        .name(result.name)
        .rating(result.rating)
        .type(result.types[0])
        .vicinity(result.vicinity)
        .build();
  }
}
