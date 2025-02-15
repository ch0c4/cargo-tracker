package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class RouteSpecificationEntity {

  @ManyToOne
  @JoinColumn(name = "spec_origin_id", updatable = false)
  private LocationEntity origin;

  @ManyToOne
  @JoinColumn(name = "spec_destination_id")
  private LocationEntity destination;

  @Column(nullable = false, name = "spec_arrival_deadline")
  private LocalDate arrivalDeadline;
}
