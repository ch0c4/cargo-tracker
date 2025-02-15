package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "voyage")
@Entity(name = "Voyage")
public class VoyageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "voyage_number", nullable = false)
  private String voyageNumber;

  @Embedded private ScheduleEntity schedule;
}
