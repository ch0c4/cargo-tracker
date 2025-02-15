package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cargo")
@Entity(name = "Cargo")
public class CargoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "tracking_id")
  private String trackingId;

  @ManyToOne
  @JoinColumn(name = "origin_id", updatable = false, nullable = false)
  private LocationEntity origin;

  // RouteSpecification
  @Embedded private RouteSpecificationEntity routeSpecification;

  // Itinerary

  @Embedded private ItineraryEntity itinerary;

  @Embedded private DeliveryEntity delivery;
}
