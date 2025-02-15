package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.domain.model.cargo.RoutingStatus;
import org.johan.tracker.cargotracker.domain.model.cargo.TransportStatus;
import org.johan.tracker.cargotracker.infrastructure.jpa.handling.HandlingEventEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class DeliveryEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "transport_status", nullable = false)
  private TransportStatus transportStatus;

  @ManyToOne
  @JoinColumn(name = "last_known_location_id")
  private LocationEntity lastKnownLocation;

  @ManyToOne
  @JoinColumn(name = "current_voyage_id")
  private VoyageEntity currentVoyage;

  @Column(name = "misdirected", nullable = false)
  private Boolean misdirected;

  @Column(name = "eta")
  private LocalDateTime eta;

  @Embedded private HandlingActivityEntity nextExpectedActivity;

  @Column(name = "unloaded_at_dest", nullable = false)
  private Boolean isUnloadedAtDestination;

  @Enumerated(EnumType.STRING)
  @Column(name = "routing_status", nullable = false)
  private RoutingStatus routingStatus;

  @Column(name = "calculated_at", nullable = false)
  private LocalDateTime calculatedAt;

  @ManyToOne
  @JoinColumn(name = "last_event_id")
  private HandlingEventEntity lastEvent;
}
