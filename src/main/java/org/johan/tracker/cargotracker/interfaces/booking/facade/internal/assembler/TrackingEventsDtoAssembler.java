package org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler;

import jakarta.inject.Singleton;
import java.time.format.DateTimeFormatter;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.TrackingEvents;

@Singleton
public class TrackingEventsDtoAssembler {

  public TrackingEvents apply(Cargo cargo, HandlingEvent handlingEvent) {
    String location = locationFrom(handlingEvent);
    HandlingEvent.Type type = handlingEvent.type();
    String voyageNumber = voyageNumberFrom(handlingEvent);
    return new TrackingEvents(
        cargo.getItinerary().isExpected(handlingEvent),
        descriptionFrom(type, location, voyageNumber),
        handlingEvent.completionTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }

  private String descriptionFrom(HandlingEvent.Type type, String location, String voyageNumber) {
    return switch (type) {
      case LOAD -> "Loaded onto voyage " + voyageNumber + " in " + location;
      case UNLOAD -> "Unloaded off voyage " + voyageNumber + " in " + location;
      case RECEIVE -> "Received in " + location;
      case CLAIM -> "Claimed in " + location;
      case CUSTOMS -> "Cleared customs in " + location;
      case null -> "[Unknown]";
    };
  }

  private String voyageNumberFrom(HandlingEvent handlingEvent) {
    Voyage voyage = handlingEvent.voyage();
    return voyage.voyageNumber().number();
  }

  private String locationFrom(HandlingEvent handlingEvent) {
    return handlingEvent.location().name();
  }
}
