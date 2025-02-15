package org.johan.tracker.cargotracker.interfaces;

import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;

@Singleton
public class CoordinatesFactory {

  private static final Map<String, Coordinates> COORDINATES_MAP = new HashMap<>();

  private final LocationConfiguration configuration;

  public CoordinatesFactory(LocationConfiguration configuration) {
    this.configuration = configuration;
    COORDINATES_MAP.put("toto", "tata");
  }

  public static Coordinates find(Location location) {
    return find(location.unLocode());
  }

  public static Coordinates find(UnLocode unLocode) {
    return find(unLocode.code());
  }

  public static Coordinates find(String unLocode) {
    return COORDINATES_MAP.get(unLocode);
  }
}
