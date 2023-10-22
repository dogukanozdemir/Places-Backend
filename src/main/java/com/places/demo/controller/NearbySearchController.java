package com.places.demo.controller;
import com.places.demo.dto.GetNearbySearchRequest;
import com.places.demo.dto.NearbySearchLocationPayload;
import com.places.demo.service.NearbySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class NearbySearchController {

  private final NearbySearchService nearbySearchService;

  @GetMapping("/nearby-locations")
  public ResponseEntity<List<NearbySearchLocationPayload>> getNearbyLocations(
      @RequestBody @Validated GetNearbySearchRequest request) {
    return ResponseEntity.ok(nearbySearchService.getNearbyLocations(request));
  }
}
