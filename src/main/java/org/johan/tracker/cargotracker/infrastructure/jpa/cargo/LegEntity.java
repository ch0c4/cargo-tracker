package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "legs")
@Entity(name = "Leg")
public class LegEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "voyage_id", nullable = false)
  private VoyageEntity voyage;

  @ManyToOne
  @JoinColumn(name = "load_location_id", nullable = false)
  private LocationEntity loadLocation;

  @ManyToOne
  @JoinColumn(name = "unload_location_id", nullable = false)
  private LocationEntity unLoadLocation;

  @Column(name = "load_time", nullable = false)
  private LocalDateTime loadTime;

  @Column(name = "unload_time", nullable = false)
  private LocalDateTime unloadTime;
}
