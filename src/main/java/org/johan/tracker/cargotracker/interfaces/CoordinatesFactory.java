package org.johan.tracker.cargotracker.interfaces;

import static org.johan.tracker.cargotracker.interfaces.SampleLocation.*;

import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;

@Singleton
public class CoordinatesFactory {

  private static final Map<String, Coordinates> COORDINATES_MAP;

  static {
    Map<String, Coordinates> map = new HashMap<>();

    map.put(HONGKONG.unLocode().code(), new Coordinates(22, 114));
    map.put(MELBOURNE.unLocode().code(), new Coordinates(-38, 145));
    map.put(STOCKHOLM.unLocode().code(), new Coordinates(59, 18));
    map.put(HELSINKI.unLocode().code(), new Coordinates(60, 25));
    map.put(CHICAGO.unLocode().code(), new Coordinates(42, -88));
    map.put(TOKYO.unLocode().code(), new Coordinates(36, 140));
    map.put(HAMBURG.unLocode().code(), new Coordinates(54, 10));
    map.put(SHANGHAI.unLocode().code(), new Coordinates(31, 121));
    map.put(ROTTERDAM.unLocode().code(), new Coordinates(52, 5));
    map.put(GOTHENBURG.unLocode().code(), new Coordinates(58, 12));
    map.put(HANGZOU.unLocode().code(), new Coordinates(30, 120));
    map.put(NEWYORK.unLocode().code(), new Coordinates(41, -74));
    map.put(DALLAS.unLocode().code(), new Coordinates(33, -97));
    map.put(UNKNOWN.unLocode().code(), new Coordinates(-90, 0)); // The South Pole.

    COORDINATES_MAP = Collections.unmodifiableMap(map);
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
