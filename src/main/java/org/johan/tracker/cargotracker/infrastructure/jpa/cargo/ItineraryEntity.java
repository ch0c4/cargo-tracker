package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.persistence.*;
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
public class ItineraryEntity {

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "cargo_id")
  @OrderColumn(name = "leg_order")
  private List<LegEntity> legs;
}
