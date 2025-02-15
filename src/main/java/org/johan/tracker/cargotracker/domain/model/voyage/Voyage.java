package org.johan.tracker.cargotracker.domain.model.voyage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.johan.tracker.cargotracker.domain.model.location.Location;

public record Voyage(VoyageNumber voyageNumber, Schedule schedule) {

  public static class Builder {
    private final List<CarrierMovement> carrierMovements = new ArrayList<>();
    private final VoyageNumber voyageNumber;
    private Location departureLocation;

    public Builder(VoyageNumber voyageNumber, Location departureLocation) {
      this.voyageNumber = voyageNumber;
      this.departureLocation = departureLocation;
    }

    public Builder addMovement(
        Location arrivalLocation, LocalDateTime departureTime, LocalDateTime arrivalTime) {
      carrierMovements.add(
          new CarrierMovement(departureLocation, arrivalLocation, departureTime, arrivalTime));
      this.departureLocation = arrivalLocation;
      return this;
    }

    public Voyage build() {
      return new Voyage(voyageNumber, new Schedule(carrierMovements));
    }
  }
}
