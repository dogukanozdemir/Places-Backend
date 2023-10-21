package com.places.demo.dto;

import lombok.Builder;

@Builder
public record NearbySearchLocationPayload(
    Double latitude, Double longitude, String name, Float rating, String vicinity, String type) {}
