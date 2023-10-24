package com.places.demo.service;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.places.demo.dto.GetNearbySearchRequest;
import com.places.demo.dto.NearbySearchLocationPayload;
import com.places.demo.entity.NearbySearch;
import com.places.demo.repository.NearbySearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NearbySearchService {

  private final GeoApiContext geoApiContext;

  private final NearbySearchRepository nearbySearchRepository;
  private final NearbySearchAsyncService nearbySearchAsyncService;

  public List<NearbySearchLocationPayload> getNearbyLocations(
      GetNearbySearchRequest searchRequest) {

    log.info(searchRequest.longitude().toString());
    log.info(searchRequest.latitude().toString());
    log.info(searchRequest.radius().toString());
    return nearbySearchRepository
        .findByLatitudeAndLongitudeAndRadius(
            searchRequest.latitude(), searchRequest.longitude(), searchRequest.radius())
        .map(this::fetchLocationsFromDatabase)
        .orElseGet(() -> fetchLocationsFromGoogle(searchRequest));
  }

  private List<NearbySearchLocationPayload> fetchLocationsFromDatabase(NearbySearch nearbySearch) {
    return nearbySearch.getLocations().stream()
        .map(
            location ->
                NearbySearchLocationPayload.builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .name(location.getName())
                    .rating(location.getRating())
                    .type(location.getType())
                    .vicinity(location.getVicinity())
                    .build())
        .toList();
  }

  private List<NearbySearchLocationPayload> fetchLocationsFromGoogle(
      GetNearbySearchRequest searchRequest) {
    try {
      LatLng location = new LatLng(searchRequest.latitude(), searchRequest.longitude());
      NearbySearchRequest nearbySearchRequest =
          PlacesApi.nearbySearchQuery(geoApiContext, location).radius(searchRequest.radius());
      PlacesSearchResponse response = nearbySearchRequest.await();
      nearbySearchAsyncService.saveSearch(searchRequest, response);
      return Arrays.stream(response.results)
          .map(
              result ->
                  NearbySearchLocationPayload.builder()
                      .latitude(result.geometry.location.lat)
                      .longitude(result.geometry.location.lng)
                      .name(result.name)
                      .rating(result.rating)
                      .vicinity(result.vicinity)
                      .type(result.types[0])
                      .build())
          .toList();
    } catch (Exception e) {
      log.error(e.getMessage());
      return Collections.emptyList();
    }
  }
}
