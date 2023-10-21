package com.places.demo.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Value("${google.maps.api.key}")
  private String googleMapsApiKey;

  @Bean
  public GeoApiContext geoApiContext() {
    return new GeoApiContext.Builder().apiKey(googleMapsApiKey).build();
  }
}