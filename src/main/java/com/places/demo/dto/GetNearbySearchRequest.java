package com.places.demo.dto;

import jakarta.validation.constraints.NotNull;

public record GetNearbySearchRequest(
    @NotNull Double latitude, @NotNull Double longitude, String keyword) {}
