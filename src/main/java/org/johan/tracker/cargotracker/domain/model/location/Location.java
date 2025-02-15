package org.johan.tracker.cargotracker.domain.model.location;

public record Location(UnLocode unLocode, String name) {

  public static Location UNKNOWN = new Location(new UnLocode("XXXXX"), "Unknown location");
}
