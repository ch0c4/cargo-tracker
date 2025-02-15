package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class HandlingActivityEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "next_expected_handling_event_type", nullable = false)
  private HandlingEvent.Type nextExpectedEventType;

  @ManyToOne
  @JoinColumn(name = "next_expected_location_id", nullable = false)
  private LocationEntity nextExpectedLocation;

  @ManyToOne
  @JoinColumn(name = "next_expected_voyage_id")
  private VoyageEntity nextExpectedVoyage;
}
