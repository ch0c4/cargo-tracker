package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "carrier_movement")
@Entity(name = "CarrierMovement")
public class CarrierMovementEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "departure_location_id", nullable = false)
  private LocationEntity departureLocation;

  @ManyToOne
  @JoinColumn(name = "arrival_location_id", nullable = false)
  private LocationEntity arrivalLocation;

  @Column(name = "departure_time", nullable = false)
  private LocalDateTime departureTime;

  @Column(name = "arrival_time", nullable = false)
  private LocalDateTime arrivalTime;
}
