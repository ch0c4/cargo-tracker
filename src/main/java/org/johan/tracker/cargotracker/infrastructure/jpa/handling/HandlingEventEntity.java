package org.johan.tracker.cargotracker.infrastructure.jpa.handling;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.infrastructure.jpa.cargo.CargoEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "handling_event")
@Entity(name = "HandlingEvent")
public class HandlingEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private HandlingEvent.Type type;

  @ManyToOne
  @JoinColumn(name = "voyage_id")
  private VoyageEntity voyage;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private LocationEntity location;

  @Column(nullable = false, name = "completion_time")
  private LocalDateTime completionTime;

  @Column(nullable = false, name = "registration")
  private LocalDateTime registrationTime;

  @ManyToOne
  @JoinColumn(name = "cargo_id", nullable = false)
  private CargoEntity cargo;
}
