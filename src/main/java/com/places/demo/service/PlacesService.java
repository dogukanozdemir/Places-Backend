package com.places.demo.service;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.places.demo.dto.GetNearbySearchRequest;
import com.places.demo.dto.NearbySearchLocationPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PlacesService {

  private final GeoApiContext geoApiContext;

  public List<NearbySearchLocationPayload> getNearbyLocations(GetNearbySearchRequest searchRequest)
      throws IOException, InterruptedException, ApiException {
    LatLng location = new LatLng(searchRequest.latitude(), searchRequest.longitude());
    NearbySearchRequest nearbySearchRequest =
        PlacesApi.nearbySearchQuery(geoApiContext, location).radius(searchRequest.radius());

    Optional.ofNullable(searchRequest.keyword()).ifPresent(nearbySearchRequest::keyword);

    PlacesSearchResponse response = nearbySearchRequest.await();
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
  }
}
