package com.places.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
    name = "nearby_searches",
    indexes = {@Index(name = "idx_latitude_longitude_radius", columnList = "latitude,longitude,radius")})
@NamedEntityGraph(
    name = "graph.nearbySearch.attribute.options",
    attributeNodes = {@NamedAttributeNode(value = "locations")})
public class NearbySearch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double latitude;

  private Double longitude;

  private Integer radius;

  private String keyword;

  @OneToMany(mappedBy = "nearbySearch")
  private Set<NearbySearchLocation> locations;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;
}
