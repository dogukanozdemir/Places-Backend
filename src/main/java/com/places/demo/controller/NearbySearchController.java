package com.places.demo.controller;

import com.places.demo.dto.GetNearbySearchRequest;
import com.places.demo.dto.NearbySearchLocationPayload;
import com.places.demo.service.NearbySearchService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class NearbySearchController {

  private final NearbySearchService nearbySearchService;

  @GetMapping("/nearby-locations")
  public ResponseEntity<List<NearbySearchLocationPayload>> getNearbyLocations(
      @Validated @RequestParam(name = "latitude") @NotNull Double latitude,
      @Validated @RequestParam(name = "longitude") @NotNull Double longitude,
      @Validated @RequestParam(name = "radius") @NotNull Integer radius) {
    GetNearbySearchRequest request = new GetNearbySearchRequest(latitude, longitude, radius);
    return ResponseEntity.ok(nearbySearchService.getNearbyLocations(request));
  }
}
