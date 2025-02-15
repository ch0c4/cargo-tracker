package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ScheduleEntity {

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "voyage_id", nullable = false)
  @OrderColumn(name = "movement_order")
  private List<CarrierMovementEntity> carrierMovements = new ArrayList<>();
}
