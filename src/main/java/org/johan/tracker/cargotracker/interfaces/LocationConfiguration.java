package org.johan.tracker.cargotracker.interfaces;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EachProperty("locations")
public class LocationConfiguration {
  private String name;
  private String unLocode;
  private String city;

  public LocationConfiguration(@Parameter String name) {
    this.name = name;
  }
}
